
INSERT INTO public.coordinates(id, latitude, longitude) VALUES (1, 50.25, 50.13);

INSERT INTO public.address (id, building_number, city, country, postal_code, street, coordinates_id)
    VALUES (1, '12', 'Kielce', 'Poland', '25-001', 'al Solidarnosci', '1');

INSERT INTO public.user (id, email, name, password, surname, username, address_id)
    VALUES (1, 'poczta@pocztowska.pl', 'haslo jak', '$2a$10$NBwGzPSWNXVOlSBbVgdqyexaL4SvcSwXEu2ziXyQ4qooER.g5whgS', 'username', 'z', 1);


