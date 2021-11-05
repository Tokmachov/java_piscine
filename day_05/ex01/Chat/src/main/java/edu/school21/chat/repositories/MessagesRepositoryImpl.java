package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;

import java.sql.*;
import java.util.Optional;

public class MessagesRepositoryImpl implements MessagesRepository {
    private static final String selectMessageSQL = "SELECT * FROM chat_app.message WHERE id = ?";
    private static final String selectChatRoomSQL = "SELECT * FROM chat_app.chat_room WHERE id = ?";
    private static final String selectUserSQL = "SELECT * FROM chat_app.user WHERE id = ?";
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

            final Long authorId = messageResultSet.getLong(4);
            final Long chatRoomId = messageResultSet.getLong(5);

            authorStatement.setLong(1, authorId);
            chatRoomStatement.setLong(1, chatRoomId);
            ResultSet authorResultSet = authorStatement.executeQuery();
            ResultSet chatRoomResultSet = chatRoomStatement.executeQuery();

            if (!authorResultSet.next()) return Optional.empty();
            if (!chatRoomResultSet.next()) return Optional.empty();

            Message message = createMessage(messageResultSet);
            User user = createUser(authorResultSet);
            Chatroom chatRoom = createChatRoom(chatRoomResultSet);
            message.setAuthor(user);
            message.setChatroom(chatRoom);

            return Optional.of(message);

        } catch (SQLException throwables) {
            System.out.println(throwables);
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

    private Chatroom createChatRoom(ResultSet resultSet) throws SQLException {
        Chatroom chatRoom = new Chatroom();
        chatRoom.setId(resultSet.getLong(1));
        chatRoom.setName(resultSet.getString(2));
        return chatRoom;
    }
}