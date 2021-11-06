package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.Room;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryImpl;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class Program {
    private static final String ddlPath = "/schema.sql";
    private static final String dmlPath = "/data.sql";

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    private static MessagesRepository messagesRepository;

    static {
        config.setJdbcUrl("jdbc:postgresql://localhost/chat_db");
        config.setUsername("sjolynn");
        config.setPassword("123");
        ds = new HikariDataSource(config);
        messagesRepository = new MessagesRepositoryImpl(ds);
    }

    public static void main(String[] args) {
        try {
            createSchema();
            populateSchemaWithData();
        } catch (Exception e) {
            System.out.println("Exception occurred when creating and populating schema" + e);
            return;
        }
        normalUpdateMessageTest();
        updateMessageWithNewAuthorAndRoomTest();
        try {
            updateNonExistingMessageTest();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            updateMessageWithMessageIdEqualsNullTest();
        } catch (Exception e) {
            System.out.println(e);
        }

        updateMessageWithAuthorEqualsNullTest();


        updateMessageWithRoomEqualsNullTest();
        updateMessageWithAuthorIdEqualsNullTest();
        updateMessageWithRoomIdEqualsNullTest();
        updateMessageWithTextEqualsNullTest();
        try {
            updateMessageWithNonExistingAuthorIdTest();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            updateMessageWithNonExistingRoomIdTest();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void createSchema() throws IOException, SQLException {
        try (InputStream DDLInputStream = Program.class.getResourceAsStream(ddlPath)) {
            if (DDLInputStream == null ) throw new IOException("Failed to open " + ddlPath);
            List<String> ddlStatements = parseSqlStatemts(DDLInputStream);
            updateDataBase(ds, Arrays.asList("DROP SCHEMA IF EXISTS chat_app CASCADE"));
            updateDataBase(ds, ddlStatements);
        }
    }

    private static void populateSchemaWithData() throws IOException, SQLException {
        List<String> dmlStatements = null;
        try (InputStream isDML = Program.class.getResourceAsStream(dmlPath)) {
            if (isDML == null) throw new IOException("Failed to open *.sql");
            dmlStatements = parseSqlStatemts(isDML);
            updateDataBase(ds, dmlStatements);
        }
    }

    private static void updateDataBase(DataSource dataSource, List<String> sqlStatements) throws SQLException {
        try (Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement()) {
            for (String sqlSatement : sqlStatements) {
                try {
                    statement.executeUpdate(sqlSatement);
                } catch (SQLException e) {
                    throw new SQLException(e.getMessage() + sqlSatement);
                }
            }
        }
    }

    private static List<String> parseSqlStatemts(InputStream is) {
        Scanner sc = new Scanner(is).useDelimiter(";\\s*");
        List<String> statements = new ArrayList<>();
        while (sc.hasNext()) {
            statements.add(sc.next());
        }
        return statements;
    }

    private static void normalUpdateMessageTest() {
        System.out.println("---------normalUpdateMessageTest()-----------");
        Message message = messagesRepository.findById(1L).orElseThrow(() -> new RuntimeException("Failed to fetch message from data base"));
        System.out.println(message);
        message.setText("Changed message text");
        message.setTimeStamp(null);
        messagesRepository.update(message);
        Message updatedMessage = messagesRepository.findById(1L).orElseThrow(() -> new RuntimeException("Failed to fetch message from data base"));
        System.out.println(updatedMessage);
    }

    private static void updateMessageWithNewAuthorAndRoomTest() {
        System.out.println("---------updateMessageWithNewAuthorAndRoomTest()-----------");
        Message message = messagesRepository.findById(1L).orElseThrow(() -> new RuntimeException("Failed to fetch message from data base"));
        System.out.println(message);
        User user = new User();
        user.setId(5L);
        Room room = new Room();
        room.setId(5L);
        message.setAuthor(user);
        message.setChatroom(room);
        messagesRepository.update(message);
        Message updatedMessage = messagesRepository.findById(1L).orElseThrow(() -> new RuntimeException("Failed to fetch message from data base"));
        System.out.println(updatedMessage);
    }

    private static void updateNonExistingMessageTest() {
        System.out.println("---------updateNonExistingMessageTest()-----------");
        Long nonExistingMessageId = 100L;
        User author = new User();
        author.setId(1L);
        Room room = new Room();
        room.setId(1L);

        Message message = new Message(nonExistingMessageId, author, room, "text", Timestamp.valueOf(LocalDateTime.now()));

        System.out.println(message);

        message.setText("Changed message text");
        message.setTimeStamp(Timestamp.valueOf(LocalDateTime.of(2000, 10, 11, 10, 10, 10, 10)));
        messagesRepository.update(message);
    }

    private static void updateMessageWithMessageIdEqualsNullTest() {
        System.out.println("---------updateMessageWithMessageIdEqualsNullTest()-----------");

        Message message = messagesRepository.findById(1L).orElseThrow(() -> new RuntimeException("Failed to fetch message from data base"));
        System.out.println(message);
        message.setId(null);
        messagesRepository.update(message);
        Message updatedMessage = messagesRepository.findById(1L).orElseThrow(() -> new RuntimeException("Failed to fetch message from data base"));
        System.out.println(updatedMessage);
    }

    private static void updateMessageWithAuthorEqualsNullTest() {
        System.out.println("---------updateMessageWithAuthorEqualsNullTest()-----------");
        Message message = messagesRepository.findById(1L).orElseThrow(() -> new RuntimeException("Failed to fetch message from data base"));
        System.out.println(message);
        message.setText("Changed message text");
        message.setTimeStamp(Timestamp.valueOf(LocalDateTime.of(2000, 10, 11, 10, 10, 10, 10)));
        message.setAuthor(null);
        messagesRepository.update(message);
        Message updatedMessage = messagesRepository.findById(1L).orElseThrow(() -> new RuntimeException("Failed to fetch message from data base"));
        System.out.println(updatedMessage);
    }

    private static void updateMessageWithRoomEqualsNullTest() {
        System.out.println("---------updateMessageWithRoomEqualsNullTest()-----------");

        Message message = messagesRepository.findById(1L).orElseThrow(() -> new RuntimeException("Failed to fetch message from data base"));
        System.out.println(message);
        message.setText("Changed message text");
        message.setTimeStamp(Timestamp.valueOf(LocalDateTime.of(2000, 10, 11, 10, 10, 10, 10)));
        message.setChatroom(null);
        messagesRepository.update(message);
        Message updatedMessage = messagesRepository.findById(1L).orElseThrow(() -> new RuntimeException("Failed to fetch message from data base"));
        System.out.println(updatedMessage);
    }

    private static void updateMessageWithAuthorIdEqualsNullTest() {
        System.out.println("---------updateMessageWithAuthorIdEqualsNullTest()-----------");

        Message message = messagesRepository.findById(2L).orElseThrow(() -> new RuntimeException("Failed to fetch message from data base"));
        System.out.println(message);
        message.setText("Changed message text");
        message.setTimeStamp(Timestamp.valueOf(LocalDateTime.of(2000, 10, 11, 10, 10, 10, 10)));
        User user = new User();
        user.setId(null);
        message.setAuthor(user);
        messagesRepository.update(message);
        Message updatedMessage = messagesRepository.findById(2L).orElseThrow(() -> new RuntimeException("Failed to fetch message from data base"));
        System.out.println(updatedMessage);
    }

    private static void updateMessageWithRoomIdEqualsNullTest() {
        System.out.println("---------updateMessageWithRoomIdEqualsNullTest()-----------");

        Message message = messagesRepository.findById(4L).orElseThrow(() -> new RuntimeException("Failed to fetch message from data base"));
        System.out.println(message);
        message.setText("Changed message text");
        message.setTimeStamp(Timestamp.valueOf(LocalDateTime.of(2000, 10, 11, 10, 10, 10, 10)));
        Room room = new Room();
        room.setId(null);
        message.setChatroom(room);
        messagesRepository.update(message);
        Message updatedMessage = messagesRepository.findById(4L).orElseThrow(() -> new RuntimeException("Failed to fetch message from data base"));
        System.out.println(updatedMessage);
    }

    private static void updateMessageWithTextEqualsNullTest() {
        System.out.println("---------updateMessageWithTextEqualsNullTest()-----------");

        Message message = messagesRepository.findById(5L).orElseThrow(() -> new RuntimeException("Failed to fetch message from data base"));
        System.out.println(message);
        message.setText(null);
        message.setTimeStamp(Timestamp.valueOf(LocalDateTime.of(2000, 10, 11, 10, 10, 10, 10)));
        messagesRepository.update(message);
        Message updatedMessage = messagesRepository.findById(5L).orElseThrow(() -> new RuntimeException("Failed to fetch message from data base"));
        System.out.println(updatedMessage);
    }

    private static void updateMessageWithNonExistingAuthorIdTest() {
        System.out.println("---------updateMessageWithNonExistingAuthorIdTest()-----------");

        Message message = messagesRepository.findById(1L).orElseThrow(() -> new RuntimeException("Failed to fetch message from data base"));
        System.out.println(message);
        User user = new User();
        user.setId(100L);
        message.setAuthor(user);
        messagesRepository.update(message);
        Message updatedMessage = messagesRepository.findById(1L).orElseThrow(() -> new RuntimeException("Failed to fetch message from data base"));
        System.out.println(updatedMessage);
    }

    private static void updateMessageWithNonExistingRoomIdTest() {
        System.out.println("---------updateMessageWithNonExistingRoomIdTest()-----------");

        Message message = messagesRepository.findById(1L).orElseThrow(() -> new RuntimeException("Failed to fetch message from data base"));
        System.out.println(message);
        Room room = new Room();
        room.setId(100L);
        message.setChatroom(room);
        messagesRepository.update(message);
        Message updatedMessage = messagesRepository.findById(1L).orElseThrow(() -> new RuntimeException("Failed to fetch message from data base"));
        System.out.println(updatedMessage);
    }
}
