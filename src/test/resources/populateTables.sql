
INSERT INTO categories (category_id, name) VALUES (1, 'exhibition');
INSERT INTO categories (category_id, name) VALUES (2, 'movie');
INSERT INTO categories (category_id, name) VALUES (3, 'theatre');

INSERT INTO locations (location_id, name, working_hours, type, address, description, capacity_people)
VALUES (100, 'Drunk oyster', '08:00-22:00', 'bar', 'FooBar street', 'description test', 300);
INSERT INTO locations (location_id, name, working_hours, type, address, description, capacity_people)
VALUES (101, 'Moes', '06:00-00:00', 'tavern', 'the great street', 'description bla bla bla for test', 750);

INSERT INTO events  (event_id, name, date, price, status, description, location_id)
VALUES (1000, 'Oxxxymiron concert', '13-08-2021 12:00:00', 5000, 'actual', 'Oxxxymion is', 101);
INSERT INTO events  (event_id, name, date, price, status, description, location_id)
VALUES (1001, 'Basta', '14-09-2019 15:30:00', 1000, 'actual', 'Bla bla bla', 100);

INSERT INTO users (user_id, name, surname, email, login, password, type)
VALUES (2000, 'Dennis', 'Yudin', 'dennisYudin@mail.ru', 'Big boss', '0000', 'customer');
INSERT INTO users (user_id, name, surname, email, login, password, type)
VALUES (2001, 'Mark', 'Batmanov', 'redDragon@mail.ru', 'HelloWorld', '1234', 'customer');

INSERT INTO tickets (ticket_id, event_name, unique_number, creation_date, status, user_id, event_id)
VALUES (3000, 'Oxxxymiron concert', '123456789', '17-02-1992 05:30:00', 'actual', 2000, 1000);
INSERT INTO tickets (ticket_id, event_name, unique_number, creation_date, status, user_id, event_id)
VALUES (3001, 'Basta', '987654321', '18-02-1986 02:30:00', 'cancelled', 2001, 1001);