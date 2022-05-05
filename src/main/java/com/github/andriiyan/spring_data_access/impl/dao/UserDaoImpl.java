package com.github.andriiyan.spring_data_access.impl.dao;

import com.github.andriiyan.spring_data_access.api.dao.UserDao;
import com.github.andriiyan.spring_data_access.api.model.User;
import com.github.andriiyan.spring_data_access.impl.model.UserEntity;
import com.github.andriiyan.spring_data_access.impl.storage.hibernate.ISessionFactoryProvider;
import com.github.andriiyan.spring_data_access.impl.utils.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

class UserDaoImpl extends BaseDao<User, UserEntity> implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    public UserDaoImpl(ISessionFactoryProvider sessionFactoryProvider) {
        super(sessionFactoryProvider);
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    @NonNull
    protected Class<UserEntity> getEntityClass() {
        return UserEntity.class;
    }

    @Override
    public User getUserByEmail(String email) {
        final Optional<User> mUser = findBy(((root, criteriaBuilder) -> criteriaBuilder.equal(root.get("email"), email)));
        logger.debug("getUserByEmail was invoked with email={} and returning {}", email, mUser);
        return mUser.get();
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        final Iterable<User> users = findPaging(pageNum, pageSize, ((root, criteriaBuilder) -> criteriaBuilder.like(
                root.get("name"), "%" + name + "%"
        )));
        logger.debug("getUsersByName was invoked with name={}, pageSize={}, pageNum={} and returning {}", name, pageSize, pageNum, users);
        return ListUtils.fromIterable(users);
    }
}
