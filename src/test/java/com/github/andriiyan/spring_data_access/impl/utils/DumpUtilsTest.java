package com.github.andriiyan.spring_data_access.impl.utils;

import com.github.andriiyan.spring_data_access.BaseContainerTest;
import com.github.andriiyan.spring_data_access.api.dao.EventDao;
import com.github.andriiyan.spring_data_access.api.dao.TicketDao;
import com.github.andriiyan.spring_data_access.api.dao.UserAccountDao;
import com.github.andriiyan.spring_data_access.api.dao.UserDao;
import com.github.andriiyan.spring_data_access.api.facade.BookingFacade;
import com.github.andriiyan.spring_data_access.api.model.Ticket;
import com.github.andriiyan.spring_data_access.impl.model.EventEntity;
import com.github.andriiyan.spring_data_access.impl.model.TicketEntity;
import com.github.andriiyan.spring_data_access.impl.model.UserAccountEntity;
import com.github.andriiyan.spring_data_access.impl.model.UserEntity;
import com.github.andriiyan.spring_data_access.impl.utils.file.Serializer;
import com.github.andriiyan.spring_data_access.impl.utils.file.serializer.ByteSerializer;
import com.github.andriiyan.spring_data_access.impl.utils.file.serializer.JsonSerializer;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

public class DumpUtilsTest extends BaseContainerTest {

    public DumpUtilsTest() {
        super("local");
    }

    @Rule
    public final TemporaryFolder temporaryFolder = TemporaryFolder.builder().assureDeletion().build();

    private void testRead(Serializer serializer) throws IOException {
        File eventFile = temporaryFolder.newFile("eventFilePath.json");
        File userFile = temporaryFolder.newFile("userFilePath.json");
        File userAccountFile = temporaryFolder.newFile("userAccountFilePath.json");
        File ticketFile = temporaryFolder.newFile("ticketFilePath.json");
        // saving some data into files

        UserEntity userEntity = new UserEntity(1, "Test #1", "Email #1");
        EventEntity eventEntity = new EventEntity(1, "Event #2", new Date(), 150);
        UserAccountEntity userAccountEntity = new UserAccountEntity(1, userEntity.getId(), 500);
        TicketEntity ticketEntity = new TicketEntity(1, eventEntity.getId(), userEntity.getId(), Ticket.Category.PREMIUM, 2);

        FileOutputStream userFileOutputStream = new FileOutputStream(userFile);
        serializer.serialize(Collections.singleton(userEntity), userFileOutputStream);
        userFileOutputStream.close();

        FileOutputStream userAccountFileOutputStream = new FileOutputStream(userAccountFile);
        serializer.serialize(Collections.singleton(userAccountEntity), userAccountFileOutputStream);
        userAccountFileOutputStream.close();

        FileOutputStream eventFileOutputStream = new FileOutputStream(eventFile);
        serializer.serialize(Collections.singleton(eventEntity), eventFileOutputStream);
        eventFileOutputStream.close();

        FileOutputStream ticketFileOutputStream = new FileOutputStream(ticketFile);
        serializer.serialize(Collections.singleton(ticketEntity), ticketFileOutputStream);
        ticketFileOutputStream.close();

        // reading saved data
        BookingFacade bookingFacade = context.getBean(BookingFacade.class);
        final DumpUtils dumpUtils = new DumpUtils(
                eventFile.getAbsolutePath(),
                userFile.getAbsolutePath(),
                userAccountFile.getAbsolutePath(),
                ticketFile.getAbsolutePath(),
                serializer,
                true,
                true
        );
        dumpUtils.setBookingFacade(bookingFacade);
        dumpUtils.init();

        // verify data is same
        TicketDao ticketDao = context.getBean(TicketDao.class);
        UserAccountDao userAccountDao = context.getBean(UserAccountDao.class);
        UserDao userDao = context.getBean(UserDao.class);
        EventDao eventDao = context.getBean(EventDao.class);

        EventEntity event = eventDao.findById(1L).get();
        Assert.assertEquals(eventEntity.getTicketPrice(), event.getTicketPrice(), 0.01);
        Assert.assertEquals(eventEntity.getTitle(), event.getTitle());
        Assert.assertEquals(userEntity, userDao.findById(1L).get());
        Assert.assertEquals(ticketEntity, ticketDao.findById(1L).get());
    }

    private void testStore(Serializer serializer) throws IOException {
        File eventFile = temporaryFolder.newFile("eventFilePath.json");
        File userFile = temporaryFolder.newFile("userFilePath.json");
        File userAccountFile = temporaryFolder.newFile("userAccountFilePath.json");
        File ticketFile = temporaryFolder.newFile("ticketFilePath.json");
        // saving some data info files

        // reading saved data
        final DumpUtils dumpUtils = new DumpUtils(
                eventFile.getAbsolutePath(),
                userFile.getAbsolutePath(),
                userAccountFile.getAbsolutePath(),
                ticketFile.getAbsolutePath(),
                serializer,
                false,
                true
        );
        dumpUtils.setItemCount(1);
        dumpUtils.init();

        // verify data is present

        FileInputStream userFileOutputStream = new FileInputStream(userFile);
        Assert.assertEquals(1, serializer.deserialize(userFileOutputStream, UserEntity.class).size());
        userFileOutputStream.close();

        FileInputStream userAccountFileOutputStream = new FileInputStream(userAccountFile);
        Assert.assertEquals(1, serializer.deserialize(userAccountFileOutputStream, UserAccountEntity.class).size());
        userAccountFileOutputStream.close();

        FileInputStream eventFileOutputStream = new FileInputStream(eventFile);
        Assert.assertEquals(1, serializer.deserialize(eventFileOutputStream, EventEntity.class).size());
        eventFileOutputStream.close();

        FileInputStream ticketFileOutputStream = new FileInputStream(ticketFile);
        Assert.assertEquals(1, serializer.deserialize(ticketFileOutputStream, TicketEntity.class).size());
        ticketFileOutputStream.close();
    }

    @Test
    public void testRead() throws IOException {
        testRead(new JsonSerializer());
        clean();
        testRead(new ByteSerializer());
    }

    private void clean() throws IOException {
        context.getBean(TicketDao.class).deleteAll();
        context.getBean(UserAccountDao.class).deleteAll();
        context.getBean(EventDao.class).deleteAll();
        context.getBean(UserDao.class).deleteAll();
        temporaryFolder.delete();
        temporaryFolder.create();
        initialize();
    }

    @Test
    public void testStore() throws IOException {
        testStore(new JsonSerializer());
        clean();
        testStore(new ByteSerializer());
    }

}
