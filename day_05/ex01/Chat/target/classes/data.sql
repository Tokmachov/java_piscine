-- noinspection SqlDialectInspectionForFile

-- noinspection SqlNoDataSourceInspectionForFile

-- noinspection SqlNoDataSourceInspection

INSERT INTO chat_app.user(user_login, user_password)
VALUES ('Yadiel Vang', 'qwerty'),
('Damien Figueroa', 'qwerty'),
('Iliana Pitts', 'qwerty'),
('Justus Shah', 'qwerty'),
('Ingrid Hawkins', 'qwerty');

INSERT INTO chat_app.chat_room (room_name, room_owner_id) VALUES
    ( 'white room',     (SELECT id from chat_app.user WHERE user_login='Yadiel Vang') ),
    ( 'blue room', (SELECT id from chat_app.user WHERE user_login='Yadiel Vang' ) ),
    ( 'red room', (SELECT id from chat_app.user WHERE user_login='Yadiel Vang' ) ),
    ( 'green room', (SELECT id from chat_app.user WHERE user_login='Yadiel Vang' ) ),
    ( 'orange room', (SELECT id from chat_app.user WHERE user_login='Yadiel Vang' ) );




INSERT INTO chat_app.chat_room_user (user_id, room_id) VALUES
    ((SELECT id from chat_app.user WHERE user_login='Yadiel Vang'), (SELECT id from chat_app.chat_room WHERE room_name='blue room')),
    ((SELECT id from chat_app.user WHERE user_login='Ingrid Hawkins'), (SELECT id from chat_app.chat_room WHERE room_name='blue room')),
    ((SELECT id from chat_app.user WHERE user_login='Iliana Pitts'), (SELECT id from chat_app.chat_room WHERE room_name='blue room')),
    ((SELECT id from chat_app.user WHERE user_login='Yadiel Vang'), (SELECT id from chat_app.chat_room WHERE room_name='white room')),
    ((SELECT id from chat_app.user WHERE user_login='Iliana Pitts'), (SELECT id from chat_app.chat_room WHERE room_name='white room'));