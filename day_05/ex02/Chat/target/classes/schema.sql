-- noinspection SqlDialectInspectionForFile

-- noinspection SqlNoDataSourceInspectionForFile

CREATE SCHEMA IF NOT EXISTS chat_app;

CREATE TABLE IF NOT EXISTS chat_app.user (
    id SERIAL PRIMARY KEY,
    user_login TEXT UNIQUE NOT NULL,
    user_password TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS chat_app.chat_room (
    id SERIAL PRIMARY KEY,
    room_name TEXT NOT NULL UNIQUE,
    room_owner_id INT NOT NULL,
    CONSTRAINT fk_users
    FOREIGN KEY (room_owner_id)
    REFERENCES chat_app.user(id)
);

CREATE TABLE IF NOT EXISTS chat_app.message (
    id SERIAL PRIMARY KEY,
    message_text TEXT,
    date_and_time timestamp,
    author_id INT,
    room_id INT,
    CONSTRAINT fk_users
    FOREIGN KEY(author_id)
    REFERENCES chat_app.user(id),   
    CONSTRAINT fk_chat_rooms
    FOREIGN KEY(room_id)
    REFERENCES chat_app.chat_room(id)
);

CREATE TABLE IF NOT EXISTS chat_app.chat_room_user(
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    room_id INT NOT NULL,
    CONSTRAINT fk_users
    FOREIGN KEY (user_id) REFERENCES chat_app.user(id),
    CONSTRAINT fk_chat_rooms
    FOREIGN KEY (room_id) REFERENCES chat_app.chat_room(id)
);



