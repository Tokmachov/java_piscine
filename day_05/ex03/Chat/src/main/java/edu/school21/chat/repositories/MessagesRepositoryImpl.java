package edu.school21.chat.repositories;

import edu.school21.chat.Exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Room;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;

import java.sql.*;
import java.util.Optional;

public class MessagesRepositoryImpl implements MessagesRepository {
    private static final String selectMessageSQL = "SELECT * FROM chat_app.message WHERE id = ?";
    private static final String selectChatRoomSQL = "SELECT * FROM chat_app.chat_room WHERE id = ?";
    private static final String selectUserSQL = "SELECT * FROM chat_app.user WHERE id = ?";
    private static final String insertMessageSQL = "INSERT INTO chat_app.message "
        + "(message_text, date_and_time, author_id, room_id) "
        + "VALUES (?, ?, ?, ?)";
    private static final String updateMessageSQL = "UPDATE chat_app.message SET "
        + " message_text=?, date_and_time=?, author_id=?, room_id=? WHERE id=?";

    private DataSource dataSource;

    @Override
    public Optional<Message> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement messageStatement = connection.prepareStatement(selectMessageSQL);
             PreparedStatement chatRoomStatement = connection.prepareStatement(selectChatRoomSQL);
             PreparedStatement authorStatement = connection.prepareStatement(selectUserSQL)) {

            messageStatement.setLong(1, id);
            ResultSet messageResultSet = messageStatement.executeQuery();
            if (!messageResultSet.next()) return Optional.empty();
            Message message = createMessage(messageResultSet);

            final Long authorId = messageResultSet.getLong(4);
            final Long chatRoomId = messageResultSet.getLong(5);

            authorStatement.setLong(1, authorId);
            chatRoomStatement.setLong(1, chatRoomId);
            ResultSet authorResultSet = authorStatement.executeQuery();
            ResultSet chatRoomResultSet = chatRoomStatement.executeQuery();

            User author = null;
            Room room = null;
            if (authorResultSet.next()) {
                author = createUser(authorResultSet);
            }
            if (chatRoomResultSet.next()) {
                room = createChatRoom(chatRoomResultSet);
            }
            message.setAuthor(author);
            message.setChatroom(room);

            return Optional.of(message);

        } catch (SQLException sqlException) {
            System.err.println(sqlException);
            return Optional.empty();
        }
    }

    public MessagesRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Message createMessage(ResultSet resultSet) throws  SQLException {
        Message message = new Message();
        message.setId(resultSet.getLong(1));
        message.setText(resultSet.getString(2));
        message.setTimeStamp(resultSet.getTimestamp(3));
        return message;
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        User author = new User();
        author.setId(resultSet.getLong(1));
        author.setLogin(resultSet.getString(2));
        author.setPassword(resultSet.getString(3));
        return author;
    }

    private Room createChatRoom(ResultSet resultSet) throws SQLException {
        Room chatRoom = new Room();
        chatRoom.setId(resultSet.getLong(1));
        chatRoom.setName(resultSet.getString(2));
        return chatRoom;
    }

    @Override
    public void save(Message message) {
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(insertMessageSQL, new String[] {"id"})) {
            if (!exists(message.getChatroom(), connection)) {
                throw new NotSavedSubEntityException("Failed to locate chat_room with id: " + message.getChatroom().getId());
            }
            if (!exists((message.getAuthor()), connection)) {
                throw new NotSavedSubEntityException("Failed to locate user with id: " + message.getAuthor().getId());
            }
            try {
                configureSaveStatement(preparedStatement, message);
            } catch (SQLException e) {
                throw new RuntimeException("Failed to configure sql statement with message data" + e.getMessage());
            }
            if (preparedStatement.executeUpdate() > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    message.setId(resultSet.getLong(1));
                } else {
                    throw new RuntimeException("Failed to obtain inserted message id");
                }
            } else {
                throw new RuntimeException("Failed to save message");
            }
        } catch (SQLException sqlException) {
            throw new IllegalArgumentException(sqlException.getMessage());
        } catch (Exception e) {
            throw e;
        }
    }
    private boolean exists(Room room, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectChatRoomSQL)) {
            preparedStatement.setLong(1, room.getId());
            ResultSet resultSet =  preparedStatement.executeQuery();
            return resultSet.next();
        }
    }

    private boolean exists(User user, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectChatRoomSQL)) {
            preparedStatement.setLong(1, user.getId());
            ResultSet resultSet =  preparedStatement.executeQuery();
            return resultSet.next();
        }
    }

    private void configureSaveStatement(PreparedStatement preparedStatement, Message message) throws SQLException {
        preparedStatement.setString(1, message.getText());
        preparedStatement.setTimestamp(2, message.getTimeStamp());
        preparedStatement.setLong(3, message.getAuthor().getId());
        preparedStatement.setLong(4, message.getChatroom().getId());
    }

    @Override
    public void update(Message message) {
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(updateMessageSQL)) {
            configureUpdateStatement(preparedStatement, message);
            if (preparedStatement.executeUpdate() != 1) {
                throw new RuntimeException("Exception: no message with id: " + message.getId() + " in data base");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Exception during update message: " + e.getMessage());
        }
    }

    private void configureUpdateStatement(PreparedStatement preparedStatement, Message message) throws SQLException {
        preparedStatement.setString(1, message.getText());
        preparedStatement.setTimestamp(2, message.getTimeStamp());
        if (message.getAuthor() != null && message.getAuthor().getId() != null) {
            preparedStatement.setLong(3, message.getAuthor().getId());
        } else {
            preparedStatement.setNull(3, Types.INTEGER);
        }
        if (message.getChatroom() != null && message.getChatroom().getId() != null) {
            preparedStatement.setLong(4, message.getChatroom().getId());
        } else {
            preparedStatement.setNull(4, Types.INTEGER);
        }
        if (message.getId() != null) {
            preparedStatement.setLong(5, message.getId());
        } else {
            preparedStatement.setNull(5, Types.INTEGER);
        }
    }
}
