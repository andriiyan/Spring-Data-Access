package com.github.andriiyan.spring_data_access.impl.utils;

import com.github.andriiyan.spring_data_access.api.exceptions.NotEnoughMoneyException;
import com.github.andriiyan.spring_data_access.api.facade.BookingFacade;
import com.github.andriiyan.spring_data_access.impl.model.EventEntity;
import com.github.andriiyan.spring_data_access.impl.model.TicketEntity;
import com.github.andriiyan.spring_data_access.impl.model.UserAccountEntity;
import com.github.andriiyan.spring_data_access.impl.model.UserEntity;
import com.github.andriiyan.spring_data_access.impl.utils.file.FileUtils;
import com.github.andriiyan.spring_data_access.impl.utils.file.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Utility class for dumping objects into the file.
 */
class DumpUtils {

    private static final Logger logger = LoggerFactory.getLogger(DumpUtils.class);

    private final String eventFilePath;
    private final String userFilePath;
    private final String userAccountFilePath;
    private final String ticketFilePath;
    @NonNull
    private final Serializer serializer;
    private boolean read;
    private boolean enabled;

    // dump optional fields

    /**
     * count of the items that should be generated and wrote into each dump file.
     */
    private int itemCount;

    // store optional fields

    @Nullable
    private BookingFacade bookingFacade;

    DumpUtils(@NonNull String eventFilePath,
              @NonNull String userFilePath,
              @NonNull String userAccountFilePath,
              @NonNull String ticketFilePath,
              @NonNull Serializer serializer,
              boolean read
    ) {
        this(eventFilePath, userFilePath, userAccountFilePath, ticketFilePath, serializer, read, true);
    }

    /**
     * Creates instance of the [DumpUtils] class.
     *
     * @param serializer specifies how to serialize/deserialize objects.
     * @param read       defines if dump should read and populate data or just create a set of data and dump them.
     * @param enabled    defines whether dump is enabled.
     */
    DumpUtils(
            String eventFilePath,
            String userFilePath,
            String userAccountFilePath,
            String ticketFilePath,
            @NonNull Serializer serializer,
            boolean read,
            boolean enabled
    ) {
        this.eventFilePath = eventFilePath;
        this.userFilePath = userFilePath;
        this.userAccountFilePath = userAccountFilePath;
        this.ticketFilePath = ticketFilePath;
        this.serializer = serializer;
        this.read = read;
        this.enabled = enabled;
    }

    void init() {
        if (enabled) {
            if (read) {
                store();
            } else {
                dump();
            }
        }
    }

    /**
     * Writes models into the files.
     */
    private DumpResult dump() {
        try {
            final Collection<UserEntity> users = dumpUsers();
            logger.info("Users {} were dumped into the {}", users, userFilePath);
            final Collection<EventEntity> events = dumpEvents();
            logger.info("Events {} were dumped into the {}", events, eventFilePath);
            final Collection<TicketEntity> tickets = dumpTickets();
            logger.info("Tickets {} were dumped into the {}", tickets, ticketFilePath);
            final Collection<UserAccountEntity> userAccountEntities = dumpUserAccounts();
            logger.info("UserAccounts {} were dumped into the {}", userAccountEntities, userAccountFilePath);
            return new DumpResult(events, users, tickets, userAccountEntities);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return new DumpResult();
    }

    private void store() {
        if (bookingFacade == null) {
            logger.warn("DumpUtils configured to read data and populate the database, but bookingFacade was not provided." +
                    "Make sure you've set it via setBookingFacade method");
            return;
        }
        try {
            Collection<UserEntity> userEntities = FileUtils.readFromFile(serializer, new File(userFilePath), UserEntity.class);
            Collection<UserAccountEntity> userAccountEntities = FileUtils.readFromFile(serializer, new File(userAccountFilePath), UserAccountEntity.class);
            Collection<EventEntity> eventEntities = FileUtils.readFromFile(serializer, new File(eventFilePath), EventEntity.class);
            Collection<TicketEntity> ticketEntities = FileUtils.readFromFile(serializer, new File(ticketFilePath), TicketEntity.class);
            for (UserEntity user : userEntities) {
                bookingFacade.createUser(user);
            }
            for (UserAccountEntity userAccount : userAccountEntities) {
                bookingFacade.refillUser(userAccount.getAmount(), userAccount.getUserId());
            }
            for (EventEntity event : eventEntities) {
                bookingFacade.createEvent(event);
            }
            for (TicketEntity entity : ticketEntities) {
                bookingFacade.bookTicket(entity.getUserId(), entity.getEventId(), entity.getPlace(), entity.getCategory());
            }
            logger.info("Data successfully populated, with \n\tusers: {},\n\n\tuserAccounts: {},\n\n\tevents: {}, \n\n\ttickets: {}", userEntities, userAccountEntities, eventEntities, ticketEntities);
        } catch (IOException | NotEnoughMoneyException e) {
            logger.error("Exception while reading the dumped data", e);
            throw new RuntimeException(e);
        }
    }


    /**
     * Writes event models into the "events" file under the rootFolder directory.
     *
     * @throws IOException in case any IO exception during the operation.
     */
    private Collection<EventEntity> dumpEvents() throws IOException {
        Collection<EventEntity> events = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            events.add(new EventEntity("Test #" + i, new Date(), i + 20));
        }
        FileUtils.writeIntoFile(serializer, eventFilePath, events);
        return events;
    }

    /**
     * Writes ticket models into the "tickets" file under the rootFolder directory.
     *
     * @throws IOException in case any IO exception during the operation.
     */
    private Collection<TicketEntity> dumpTickets() throws IOException {
        Collection<TicketEntity> tickets = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            tickets.add(new TicketEntity(i, i + 1, i + 1, TicketEntity.Category.PREMIUM, i));
        }
        FileUtils.writeIntoFile(serializer, ticketFilePath, tickets);
        return tickets;
    }

    /**
     * Writes user models into the "users" file under the rootFolder directory.
     *
     * @throws IOException in case any IO exception during the operation.
     */
    private Collection<UserEntity> dumpUsers() throws IOException {
        Collection<UserEntity> events = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            events.add(new UserEntity(i, "Test #" + i, "email #" + i));
        }
        FileUtils.writeIntoFile(serializer, userFilePath, events);
        return events;
    }

    /**
     * Writes userAccount models into the "users" file under the rootFolder directory.
     *
     * @throws IOException in case any IO exception during the operation.
     */
    private Collection<UserAccountEntity> dumpUserAccounts() throws IOException {
        Collection<UserAccountEntity> events = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            events.add(new UserAccountEntity(i, i * 10));
        }
        FileUtils.writeIntoFile(serializer, userAccountFilePath, events);
        return events;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isRead() {
        return read;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public void setBookingFacade(@Nullable BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    public static class DumpResult {
        final List<EventEntity> dumpedEvents;
        final List<UserEntity> dumpedUsers;
        final List<TicketEntity> dumpedTickets;
        final List<UserAccountEntity> dumpedUserAccounts;

        public DumpResult() {
            this.dumpedEvents = Collections.emptyList();
            this.dumpedUsers = Collections.emptyList();
            this.dumpedTickets = Collections.emptyList();
            this.dumpedUserAccounts = Collections.emptyList();
        }

        public DumpResult(Collection<EventEntity> dumpedEvents, Collection<UserEntity> dumpedUsers, Collection<TicketEntity> dumpedTickets, Collection<UserAccountEntity> userAccountEntities) {
            this.dumpedEvents = new ArrayList<>(dumpedEvents);
            this.dumpedUsers = new ArrayList<>(dumpedUsers);
            this.dumpedTickets = new ArrayList<>(dumpedTickets);
            this.dumpedUserAccounts = new ArrayList<>(userAccountEntities);
        }

        public List<EventEntity> getDumpedEvents() {
            return dumpedEvents;
        }

        public List<TicketEntity> getDumpedTickets() {
            return dumpedTickets;
        }

        public List<UserEntity> getDumpedUsers() {
            return dumpedUsers;
        }
    }
}
