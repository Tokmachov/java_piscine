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

        try {
            saveNewMessageWithAuthorIdEqualsNullTest();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            saveNewMessageWithRoomIdEqualsNullTest();
        } catch (Exception e) {
            System.out.println(e);
        }
        saveNewMessageWithAllFieldsEqualsNullTest();
        saveNewMessageAndPrintNewMessageIdOrErrorIfFailed(1L, 2L);
        saveNewMessageAndPrintNewMessageIdOrErrorIfFailed(10L, 20L);
    }

    private static void saveNewMessageAndPrintNewMessageIdOrErrorIfFailed(Long userId, Long roomId) {
        try {
            User author = new User(userId, "user", "user", new ArrayList<>(), new ArrayList<>());
            Room room = new Room(roomId, "room", author, new ArrayList<>());
            Message message = new Message(null, author, room, "Hello", Timestamp.valueOf(LocalDateTime.now()));
            messagesRepository.save(message);
            System.out.println(message.getId());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private static void saveNewMessageWithAuthorIdEqualsNullTest() {
        User author = new User(null, "user", "user", new ArrayList<>(), new ArrayList<>());
        Room room = new Room(1L, "room", author, new ArrayList<>());
        Message message = new Message(null, author, room, "Hello", Timestamp.valueOf(LocalDateTime.now()));
        messagesRepository.save(message);
        System.out.println(message.getId());
    }

    private static void saveNewMessageWithRoomIdEqualsNullTest() {
        User author = new User(null, "user", "user", new ArrayList<>(), new ArrayList<>());
        Room room = new Room(null, "room", author, new ArrayList<>());
        Message message = new Message(1L, author, room, "Hello", Timestamp.valueOf(LocalDateTime.now()));
        messagesRepository.save(message);
        System.out.println(message.getId());
    }

    private static void saveNewMessageWithAllFieldsEqualsNullTest() {
        Message message = new Message(null, null, null, null, null);
        messagesRepository.save(message);
        System.out.println(message.getId());
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
}
