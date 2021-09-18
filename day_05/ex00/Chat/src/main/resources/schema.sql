CREATE SCHEMA IF NOT EXISTS chat_app;

CREATE TABLE IF NOT EXISTS chat_app.user (
    id SERIAL PRIMARY KEY,
    user_login TEXT UNIQUE NOT NULL,
    user_password TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS chat_app.chatroom (
    id SERIAL PRIMARY KEY,
    room_name TEXT NOT NULL,
    room_owner_id INT,
    CONSTRAINT fk_users
    FOREIGN KEY(room_owner_id)
    REFERENCES chat_app.user(id)
);

CREATE TABLE IF NOT EXISTS chat_app.message (
    id SERIAL PRIMARY KEY,
    message_text TEXT NOT NULL,
    date_and_time timestamp NOT NULL,
    author_id INT,
    room_id INT,
    CONSTRAINT fk_users
    FOREIGN KEY(author_id)
    REFERENCES chat_app.user(id),   
    CONSTRAINT fk_chatrooms
    FOREIGN KEY(room_id)
    REFERENCES chat_app.chatroom(id)
);

CREATE TABLE IF NOT EXISTS chat_app.chatroom_user(
    id SERIAL PRIMARY KEY,
    user_id int NOT NULL,
    FOREIGN KEY (user_id) REFERENCES chat_app.user(id),
    room_id int NOT NULL,
    FOREIGN KEY (room_id) REFERENCES chat_app.chatroom(id)
);



