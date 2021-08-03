INSERT INTO chat_app.user(user_login, user_password)
VALUES ('Yadiel Vang', 'qwerty'),
('Damien Figueroa', 'qwerty'),
('Iliana Pitts', 'qwerty'),
('Justus Shah', 'qwerty'),
('Ingrid Hawkins', 'qwerty');

INSERT INTO chat_app.chatroom (room_name, room_owner_id) VALUES
    ( 'white room',     (SELECT id from chat_app.user WHERE user_login='Yadiel Vang') ),
    ( 'blue room', (SELECT id from chat_app.user WHERE user_login='Yadiel Vang' ) ),
    ( 'red room', (SELECT id from chat_app.user WHERE user_login='Yadiel Vang' ) ),
    ( 'green room', (SELECT id from chat_app.user WHERE user_login='Yadiel Vang' ) ),
    ( 'orange room', (SELECT id from chat_app.user WHERE user_login='Yadiel Vang' ) );


INSERT INTO chat_app.message (message_text, date_and_time, author_id, room_id) VALUES
    ( 'Act justly.', '2021-07-06 11:35:07', (SELECT id from chat_app.user WHERE user_login='Yadiel Vang'), (SELECT id from chat_app.chatroom WHERE room_name='white room') ),
    ( 'Aim high.', '2021-07-06 11:36:07', (SELECT id from chat_app.user WHERE user_login='Damien Figueroa' ), (SELECT id from chat_app.chatroom WHERE room_name='blue room') ),
    ( 'Chill out', '2021-07-06 11:37:07', (SELECT id from chat_app.user WHERE user_login='Iliana Pitts' ), (SELECT id from chat_app.chatroom WHERE room_name='blue room') ),
    ( 'Dance today', '2021-07-06 11:38:07', (SELECT id from chat_app.user WHERE user_login='Justus Shah' ), (SELECT id from chat_app.chatroom WHERE room_name='blue room') ),
    ( 'Don’t panic', '2021-07-06 11:39:07', (SELECT id from chat_app.user WHERE user_login='Ingrid Hawkins' ), (SELECT id from chat_app.chatroom WHERE room_name='blue room') );