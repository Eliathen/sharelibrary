
INSERT INTO public.coordinates(id, latitude, longitude) VALUES (1, 50.25, 50.13);
INSERT INTO public.coordinates(id, latitude, longitude) VALUES (2, 50.5915108, 21.0749304);

INSERT INTO public.user (id, email, name, password, surname, username, coordinates_id)
    VALUES (1, 'poczta@pocztowska.pl', 'haslo jak', '$2a$10$NBwGzPSWNXVOlSBbVgdqyexaL4SvcSwXEu2ziXyQ4qooER.g5whgS', 'username', 'z', 1);
INSERT INTO public.user (id, email, name, password, surname, username, coordinates_id)
    VALUES (2, 'poczta1@pocztowska.com', 'Wojciech', '$2a$10$aDoBYGbsQNHueWHeD92Jkeamw1rydYYXzeaufmu4hg8LyV00/h5E.', 'x', 'Kowalski', 2);