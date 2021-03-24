-- FLIGHTS TABLE
DROP TABLE IF EXISTS flights;
CREATE TABLE flights (
    id INTEGER PRIMARY KEY,
    departureLocation VARCHAR(3) NOT NULL,
    arrivalLocation VARCHAR(3) NOT NULL,
    departureTime VARCHAR(5),
    arrivalTime VARCHAR(5),
    flightDate DATE,
    price INTEGER,
    airline VARCHAR(265),
    mealService BOOLEAN
);

-- SEATS TABLE
DROP TABLE IF EXISTS seats;
CREATE TABLE seats (
    flight_id INTEGER NOT NULL,
    seatID INTEGER NOT NULL,
    isAvailable BOOLEAN,
    isFirstClass BOOLEAN,
    isEmergency BOOLEAN
);

-- BOOKINGS TABLE
DROP TABLE IF EXISTS bookings;
CREATE TABLE bookings (
    passenger_name VARCHAR(265),
    user_email VARCHAR(265),
    flight_id INTEGER,
    seat_id INTEGER
);

-- USERS TABLE
DROP TABLE IF EXISTS users;
CREATE TABLE users (
    name VARCHAR(265),
    email VARCHAR(265) UNIQUE NOT NULL,
    password VARCHAR(265) NOT NULL
);

-- Insert users
INSERT INTO users VALUES ("Admin", "admin@example.com", "123");

-- Insert bookings
INSERT INTO bookings VALUES ("Admin" ,"admin@example.com", 1, 3);
INSERT INTO bookings VALUES ("Matthias Book","admin@example.com", 1, 4);

-- Insert flights
INSERT INTO flights VALUES (1, 'RVK', 'AKR', '11:00', '13:00', date('2021-01-01'), 15000, 'Iceland Air', TRUE);
INSERT INTO flights VALUES (2, 'RVK', 'EGL', '11:00', '13:00', date('2021-01-01'), 10000, 'Flugfélag Íslands', FALSE);
INSERT INTO flights VALUES (3, 'RVK', 'ISA', '11:00', '13:00', date('2021-01-01'), 10000, 'Flugfélag Íslands', FALSE);

-- Insert seats
-- Flight 1
INSERT INTO seats VALUES (1, 1, TRUE, TRUE, FALSE);
INSERT INTO seats VALUES (1, 2, TRUE, TRUE, FALSE);
INSERT INTO seats VALUES (1, 3, TRUE, TRUE, FALSE);
INSERT INTO seats VALUES (1, 4, TRUE, TRUE, FALSE);
INSERT INTO seats VALUES (1, 5, TRUE, FALSE, FALSE);
INSERT INTO seats VALUES (1, 6, TRUE, FALSE, FALSE);
INSERT INTO seats VALUES (1, 7, TRUE, FALSE, FALSE);
INSERT INTO seats VALUES (1, 8, TRUE, FALSE, FALSE);
INSERT INTO seats VALUES (1, 9, TRUE, FALSE, TRUE);
INSERT INTO seats VALUES (1, 10, TRUE, FALSE, FALSE);
INSERT INTO seats VALUES (1, 11, TRUE, FALSE, FALSE);
INSERT INTO seats VALUES (1, 12, TRUE, FALSE, TRUE);
--Flight 2
INSERT INTO seats VALUES (2, 1, TRUE, TRUE, FALSE);
INSERT INTO seats VALUES (2, 2, TRUE, TRUE, FALSE);
INSERT INTO seats VALUES (2, 3, TRUE, TRUE, FALSE);
INSERT INTO seats VALUES (2, 4, TRUE, TRUE, FALSE);
INSERT INTO seats VALUES (2, 5, TRUE, FALSE, FALSE);
INSERT INTO seats VALUES (2, 6, TRUE, FALSE, FALSE);
INSERT INTO seats VALUES (2, 7, TRUE, FALSE, FALSE);
INSERT INTO seats VALUES (2, 8, TRUE, FALSE, FALSE);
INSERT INTO seats VALUES (2, 9, TRUE, FALSE, TRUE);
INSERT INTO seats VALUES (2, 10, TRUE, FALSE, FALSE);
INSERT INTO seats VALUES (2, 11, TRUE, FALSE, FALSE);
INSERT INTO seats VALUES (2, 12, TRUE, FALSE, TRUE);
