package com.github.andriiyan.spring_data_access.impl.utils;

import com.github.andriiyan.spring_data_access.api.model.Event;
import com.github.andriiyan.spring_data_access.api.model.Ticket;
import com.github.andriiyan.spring_data_access.api.model.User;
import com.github.andriiyan.spring_data_access.impl.model.EventEntity;
import com.github.andriiyan.spring_data_access.impl.model.TicketEntity;
import com.github.andriiyan.spring_data_access.impl.model.UserEntity;
import com.github.andriiyan.spring_data_access.impl.utils.file.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.*;

/**
 * Utility class for dumping objects into the file.
 */
class DumpUtils {

    private static final Logger logger = LoggerFactory.getLogger(DumpUtils.class);

    @NonNull
    private final FileUtils fileUtils;
    @NonNull
    private String rootFolder;
    private final int itemCount;

    /**
     * Creates instance of the [DumpUtils] class.
     *
     * @param rootFolder specifies root directory where dump files will be created.
     * @param fileUtils file utilities for write models into the files.
     * @param itemCount count of the items that should be generated and wrote into the each dump file.
     */
    public DumpUtils(@NonNull String rootFolder, @NonNull FileUtils fileUtils, int itemCount) {
        this.rootFolder = rootFolder;
        this.fileUtils = fileUtils;
        this.itemCount = itemCount;
    }

    /**
     * Writes models into the files.
     *
     * As result there will be created 3 files under the [rootFolder] directory:
     * - events;
     * - tickets;
     * - users;
     * File's extensions will variate depending on the chose Serializer (which injects into the FileUtils). possible
     * variants of extensions are: .json, .object.
     */
    DumpResult dump() {
        try {
            final Collection<User> users = dumpUsers();
            logger.info("Users {} were dumped into the {}/users{}", users, rootFolder, suffix());
            final Collection<Event> events = dumpEvents();
            logger.info("Events {} were dumped into the {}/events{}", events, rootFolder, suffix());
            final Collection<Ticket> tickets = dumpTickets();
            logger.info("Tickets {} were dumped into the {}/tickets{}", tickets, rootFolder, suffix());
            return new DumpResult(events, users, tickets);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return new DumpResult();
    }

    /**
     * Writes event models into the "events" file under the rootFolder directory.
     * @throws IOException in case any IO exception during the operation.
     */
    private Collection<Event> dumpEvents() throws IOException {
        Collection<Event> events = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            events.add(new EventEntity(i, "Test #" + i, new Date()));
        }
        fileUtils.writeIntoFile(rootFolder + "events" + suffix(), events);
        return events;
    }

    /**
     * Writes ticket models into the "tickets" file under the rootFolder directory.
     * @throws IOException in case any IO exception during the operation.
     */
    private Collection<Ticket> dumpTickets() throws IOException {
        Collection<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            tickets.add(new TicketEntity(i, i, i, Ticket.Category.PREMIUM, i));
        }
        fileUtils.writeIntoFile(rootFolder + "tickets" + suffix(), tickets);
        return tickets;
    }

    /**
     * Writes user models into the "users" file under the rootFolder directory.
     * @throws IOException in case any IO exception during the operation.
     */
    private Collection<User> dumpUsers() throws IOException {
        Collection<User> events = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            events.add(new UserEntity(i, "Test #" + i, "email #" + i));
        }
        fileUtils.writeIntoFile(rootFolder + "users" + suffix(), events);
        return events;
    }

    /**
     * @return file's extension.
     */
    @NonNull
    private String suffix() {
        return fileUtils.fileExtension();
    }

    public void setRootFolder(@NonNull String rootFolder) {
        this.rootFolder = rootFolder;
    }

    public static class DumpResult {
        final List<Event> dumpedEvents;
        final List<User> dumpedUsers;
        final List<Ticket> dumpedTickets;

        public DumpResult() {
            this.dumpedEvents = Collections.emptyList();
            this.dumpedUsers = Collections.emptyList();
            this.dumpedTickets = Collections.emptyList();
        }

        public DumpResult(Collection<Event> dumpedEvents, Collection<User> dumpedUsers, Collection<Ticket> dumpedTickets) {
            this.dumpedEvents = new ArrayList<>(dumpedEvents);
            this.dumpedUsers = new ArrayList<>(dumpedUsers);
            this.dumpedTickets = new ArrayList<>(dumpedTickets);
        }

        public List<Event> getDumpedEvents() {
            return dumpedEvents;
        }

        public List<Ticket> getDumpedTickets() {
            return dumpedTickets;
        }

        public List<User> getDumpedUsers() {
            return dumpedUsers;
        }
    }
}
