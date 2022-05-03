package com.github.andriiyan.spring_data_access.impl.dao;

import com.github.andriiyan.spring_data_access.api.model.Identifierable;
import com.github.andriiyan.spring_data_access.impl.storage.hibernate.ISessionFactoryProvider;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.slf4j.Logger;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

abstract class BaseDao<T extends Identifierable, I extends T> implements CrudRepository<T, Long> {

    public static final String ID_COLUMN_NAME = "id";

    private final ISessionFactoryProvider sessionFactoryProvider;

    public BaseDao(ISessionFactoryProvider sessionFactoryProvider) {
        this.sessionFactoryProvider = sessionFactoryProvider;
    }

    protected abstract Logger getLogger();

    @NonNull
    protected abstract Class<I> getEntityClass();

    @NonNull
    protected String getPrimaryKeyColumnName() {
        return ID_COLUMN_NAME;
    }

    protected SessionFactory getSessionFactory() {
        return sessionFactoryProvider.getSessionFactory();
    }

    protected Session createSession() {
        return getSessionFactory().openSession();
    }

    @FunctionalInterface
    private interface FunctionDatabaseOperation<T> {
        T invoke(@NonNull Session session, @NonNull Transaction transaction);
    }

    @FunctionalInterface
    private interface FunctionCriteriaQueryOperation<R> {
        CriteriaQuery<R> query(@NonNull Root<R> root, @NonNull CriteriaQuery<R> criteriaQuery, @NonNull CriteriaBuilder criteriaBuilder);
    }

    @FunctionalInterface
    private interface FunctionCriteriaDeleteOperation<R> {
        CriteriaDelete<R> delete(@NonNull Root<R> root, @NonNull CriteriaDelete<R> criteriaDelete, @NonNull CriteriaBuilder criteriaBuilder);
    }

    @FunctionalInterface
    interface FunctionPredicateFormatter<R> {
        Predicate predicate(@NonNull Root<R> root, @NonNull CriteriaBuilder criteriaBuilder);
    }

    private <R> R withTransactionCommit(@NonNull Session session, @NonNull FunctionDatabaseOperation<R> body) {
        try (session) {
            Transaction transaction = session.beginTransaction();
            try {
                R result = body.invoke(session, transaction);
                transaction.commit();
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                transaction.rollback();
                getLogger().error("withTransactionCommit: Error has happened during database operation", e);
                throw e;
            }
        }
    }

    private <R> R withTransactionCommitAndSession(@NonNull FunctionDatabaseOperation<R> body) {
        return withTransactionCommit(createSession(), body);
    }

    private CriteriaQuery<I> withCriteriaQuery(@NonNull Session session, @NonNull FunctionCriteriaQueryOperation<I> functionCriteriaQueryOperation) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<I> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());

        Root<I> root = criteriaQuery.from(getEntityClass());
        criteriaQuery.select(root);
        return functionCriteriaQueryOperation.query(root, criteriaQuery, criteriaBuilder);
    }

    private MutationQuery withCriteriaDelete(@NonNull Session session, @NonNull FunctionCriteriaDeleteOperation<I> functionCriteriaDeleteOperation) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaDelete<I> criteriaDelete = criteriaBuilder.createCriteriaDelete(getEntityClass());

        Root<I> root = criteriaDelete.from(getEntityClass());
        return session.createMutationQuery(functionCriteriaDeleteOperation.delete(root, criteriaDelete, criteriaBuilder));
    }

    @Override
    @NonNull
    public <S extends T> S save(@NonNull S entity) {
        return withTransactionCommitAndSession((session, transaction) -> {
            if (entity.getId() != 0) {
                session.merge(entity);
                getLogger().debug("save: Merge {}", entity);
            } else {
                session.persist(entity);
                getLogger().debug("save: Saving {}", entity);
            }
            return entity;
        });
    }

    @Override
    @NonNull
    public <S extends T> Iterable<S> saveAll(@NonNull Iterable<S> entities) {
        return withTransactionCommitAndSession(((session, transaction) -> {
            for (S entity : entities) {
                session.persist(entity);
                getLogger().debug("saveAll: Saving {}", entity);
            }
            return entities;
        }));
    }

    @Override
    @NonNull
    public Optional<T> findById(@NonNull Long aLong) {
        return withTransactionCommitAndSession(((session, transaction) -> {
            T entity = session.get(getEntityClass(), aLong);
            getLogger().debug("findById: Searching for the {} with primary key {}, result is {}", getEntityClass(), aLong, entity);
            return Optional.ofNullable(entity);
        }));
    }

    @NonNull
    public Optional<T> findBy(@NonNull FunctionPredicateFormatter<I> predicate) {
        return withTransactionCommitAndSession(((session, transaction) -> {
            CriteriaQuery<I> cQ = withCriteriaQuery(session, (root, criteriaQuery, criteriaBuilder) -> criteriaQuery.select(root).where(predicate.predicate(root, criteriaBuilder)));
            T result = session.createQuery(cQ).getSingleResult();
            getLogger().debug("findBy(predicate): returning result: {}", result);
            return Optional.ofNullable(result);
        }));
    }

    @Override
    public boolean existsById(@NonNull Long aLong) {
        boolean exists = findById(aLong).isPresent();
        getLogger().debug("existsById: Searching for the entity with id {}, result is {}", aLong, exists);
        return exists;
    }

    @Override
    @NonNull
    public Iterable<T> findAll() {
        return withTransactionCommitAndSession(((session, transaction) -> {
            CriteriaQuery<I> cQ = withCriteriaQuery(session, (root, criteriaQuery, criteriaBuilder) -> criteriaQuery.select(root));
            Iterable<I> result = session.createQuery(cQ).getResultList();
            getLogger().debug("findAll: returning result: {}", result);
            return (Iterable<T>) result;
        }));
    }

    @NonNull
    public Iterable<T> findAll(@NonNull FunctionPredicateFormatter<I> predicate) {
        return withTransactionCommitAndSession(((session, transaction) -> {
            CriteriaQuery<I> cQ = withCriteriaQuery(session, (root, criteriaQuery, criteriaBuilder) -> criteriaQuery.select(root).where(predicate.predicate(root, criteriaBuilder)));
            Iterable<I> result = session.createQuery(cQ).getResultList();
            getLogger().debug("findAll(predicate): returning result: {}", result);
            return (Iterable<T>) result;
        }));
    }

    @NonNull
    public Iterable<T> findPaging(int pageNum, int pageSize, FunctionPredicateFormatter<I> predicate) {
        return withTransactionCommitAndSession(((session, transaction) -> {
            CriteriaQuery<I> cQ = withCriteriaQuery(session, (root, criteriaQuery, criteriaBuilder) -> criteriaQuery.select(root).where(predicate.predicate(root, criteriaBuilder)));
            Iterable<I> result = session.createQuery(cQ).setFirstResult(pageNum * pageSize).setMaxResults(pageSize).getResultList();
            getLogger().debug("findPaging: pageNum={}, pageSize={}, returning result: {}", pageNum, pageSize, result);
            return (Iterable<T>) result;
        }));
    }

    @Override
    @NonNull
    public Iterable<T> findAllById(@NonNull Iterable<Long> longs) {
        return withTransactionCommitAndSession(((session, transaction) -> {
            CriteriaQuery<I> cQ = withCriteriaQuery(session, ((root, criteriaQuery, criteriaBuilder) -> {
                CriteriaBuilder.In<Long> inClause = criteriaBuilder.in(root.get(getPrimaryKeyColumnName()));
                for (Long id : longs) {
                    inClause.value(id);
                }
                return criteriaQuery.select(root).where(inClause);
            }));
            Iterable<I> result = session.createQuery(cQ).getResultList();
            getLogger().debug("findAllById: with ids of {}, returning result: {}", longs, result);
            return (Iterable<T>) result;
        }));
    }

    @Override
    public long count() {
        return withTransactionCommitAndSession(((session, transaction) -> {
            CriteriaQuery<? extends T> query = withCriteriaQuery(session, (root, criteriaQuery, criteriaBuilder) -> criteriaQuery.select(root));
            long size = session.createQuery(query).getResultList().size();
            getLogger().debug("count: returned {}", size);
            return size;
        }));
    }

    @Override
    public void deleteById(@NonNull Long aLong) {
        withTransactionCommitAndSession(((session, transaction) -> {
            T entity = session.get(getEntityClass(), aLong);
            if (entity == null) {
                getLogger().debug("deleteById: entity with id {} is not found in the database", aLong);
            } else {
                session.remove(entity);
                getLogger().debug("deleteById: entity with id {} was removed", aLong);
            }
            return null;
        }));
    }

    @Override
    public void delete(@NonNull T entity) {
        withTransactionCommitAndSession(((session, transaction) -> {
            session.remove(entity);
            getLogger().debug("delete: entity {} was removed", entity);
            return null;
        }));
    }

    @Override
    public void deleteAllById(@NonNull Iterable<? extends Long> longs) {
        withTransactionCommitAndSession((session, transaction) -> {
            MutationQuery mutationQuery = withCriteriaDelete(session, (root, criteriaDelete, criteriaBuilder) -> {
                CriteriaBuilder.In<Long> inClause = criteriaBuilder.in(root.get(getPrimaryKeyColumnName()));
                for (Long id : longs) {
                    inClause.value(id);
                }
                return criteriaDelete.where(inClause);
            });
            mutationQuery.executeUpdate();
            getLogger().debug("deleteAllById: successfully deleted entities with id of {}", longs);
            return null;
        });
    }

    @Override
    public void deleteAll(@NonNull Iterable<? extends T> entities) {
        withTransactionCommitAndSession(((session, transaction) -> {
            for (T entity : entities) {
                session.remove(entity);
                getLogger().debug("deleteAll: entity {} was removed", entity);
            }
            return null;
        }));
    }

    @Override
    public void deleteAll() {
        withTransactionCommitAndSession((session, transaction) -> {
            MutationQuery mutationQuery = withCriteriaDelete(session, (root, criteriaDelete, criteriaBuilder) -> criteriaDelete);
            mutationQuery.executeUpdate();
            getLogger().debug("deleteAll: successfully deleted all entities");
            return null;
        });
    }

}
