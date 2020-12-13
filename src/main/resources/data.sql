--coordinates
INSERT INTO public.coordinates(id, latitude, longitude) VALUES (1, 50.25, 50.13);
INSERT INTO public.coordinates(id, latitude, longitude) VALUES (2, 50.5915108, 21.0749304);
-- users
INSERT INTO public.user (id, email, name, password, surname, username, coordinates_id)
    VALUES (1, 'poczta@pocztowska.pl', 'haslo jak', '$2a$10$NBwGzPSWNXVOlSBbVgdqyexaL4SvcSwXEu2ziXyQ4qooER.g5whgS', 'username', 'z', 1);
INSERT INTO public.user (id, email, name, password, surname, username, coordinates_id)
    VALUES (2, 'poczta1@pocztowska.com', 'Wojciech', '$2a$10$aDoBYGbsQNHueWHeD92Jkeamw1rydYYXzeaufmu4hg8LyV00/h5E.', 'haslo x', 'x', 2);
-- rooms
INSERT INTO PUBLIC.CHAT_ROOM (ID, RECIPIENT_ID, SENDER_ID) VALUES (1, 2, 1);
-- messages
INSERT INTO PUBLIC.CHAT_MESSAGE (ID, CONTENT, TIMESTAMP, ROOM_ID, RECIPIENT_ID, SENDER_ID) VALUES (1, 'Hello user 2', '2012-09-17 18:47:52.690000', 1, 2, 1);
INSERT INTO PUBLIC.CHAT_MESSAGE (ID, CONTENT, TIMESTAMP, ROOM_ID, RECIPIENT_ID, SENDER_ID) VALUES (2, 'Hello user 1', '2012-09-17 18:47:52.690000', 1, 1, 2);
--categories
INSERT INTO PUBLIC.CATEGORY (ID, NAME) VALUES (1, 'Fantasy');
INSERT INTO PUBLIC.CATEGORY (ID, NAME) VALUES (2, 'Biography');