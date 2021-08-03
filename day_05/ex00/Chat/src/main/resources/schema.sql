CREATE SCHEMA IF NOT EXISTS chat_app;

CREATE TABLE IF NOT EXISTS chat_app.user (
    id SERIAL PRIMARY KEY,
    user_login VARCHAR NOT NULL,
    user_password VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS chat_app.chatroom (
    id SERIAL PRIMARY KEY,
    room_name VARCHAR NOT NULL,
    room_owner_id INT,
    CONSTRAINT fk_users
    FOREIGN KEY(room_owner_id)
    REFERENCES chat_app.user(id)
    ON DELETE SET NULL

);

CREATE TABLE IF NOT EXISTS chat_app.message (
    id SERIAL PRIMARY KEY,
    message_text TEXT NOT NULL,
    date_and_time timestamp NOT NULL,
    author_id INT,
    room_id INT,
    CONSTRAINT fk_users
    FOREIGN KEY(author_id)
    REFERENCES chat_app.user(id)
    ON DELETE SET NULL,
    CONSTRAINT fk_chatrooms
    FOREIGN KEY(room_id)
    REFERENCES chat_app.chatroom(id)
    ON DELETE SET NULL
);




