-- ================================
-- STAFF (BASE TABLE)
-- ================================
INSERT INTO staff (id, name, staff_type) VALUES
                                             ('S1', 'John Pilot', 'AIRLINE'),
                                             ('S2', 'Maria Stewart', 'AIRLINE'),
                                             ('S3', 'Luke Copilot', 'AIRLINE'),
                                             ('S4', 'Anna Cabin', 'AIRLINE'),
                                             ('S5', 'Tim Navigator', 'AIRLINE'),
                                             ('S6', 'George Loader', 'AIRPORT'),
                                             ('S7', 'Emily Gate', 'AIRPORT'),
                                             ('S8', 'Chris Check', 'AIRPORT'),
                                             ('S9', 'Zoe Baggage', 'AIRPORT'),
                                             ('S10', 'Oliver Security', 'AIRPORT');


-- ================================
-- AIRLINE EMPLOYEE (JOINED TABLE)
-- ================================
INSERT INTO airline_employee (id, role) VALUES
                                            ('S1', 'PILOT'),
                                            ('S2', 'FLIGHT_ATTENDANT'),
                                            ('S3', 'CO_PILOT'),
                                            ('S4', 'FLIGHT_ATTENDANT'),
                                            ('S5', 'GROUND_STAFF');


-- ================================
-- AIRPORT EMPLOYEE
-- ================================
INSERT INTO airport_employee (id, designation, department) VALUES
                                                               ('S6', 'Loader', 'Logistics'),
                                                               ('S7', 'Gate Agent', 'Customer Service'),
                                                               ('S8', 'Document Check', 'Security'),
                                                               ('S9', 'Baggage Handler', 'Logistics'),
                                                               ('S10', 'Security Officer', 'Security');


-- ================================
-- AIRPLANES
-- ================================
INSERT INTO airplanes (id, number, capacity) VALUES
                                                 ('A1', 101, 180),
                                                 ('A2', 102, 150),
                                                 ('A3', 103, 200),
                                                 ('A4', 104, 220),
                                                 ('A5', 105, 160),
                                                 ('A6', 106, 140),
                                                 ('A7', 107, 300),
                                                 ('A8', 108, 250),
                                                 ('A9', 109, 190),
                                                 ('A10', 110, 170);


-- ================================
-- NOTICEBOARDS
-- ================================
INSERT INTO noticeboards (id, date) VALUES
                                        ('NB1', '2025-01-01'),
                                        ('NB2', '2025-01-02'),
                                        ('NB3', '2025-01-03'),
                                        ('NB4', '2025-01-04'),
                                        ('NB5', '2025-01-05'),
                                        ('NB6', '2025-01-06'),
                                        ('NB7', '2025-01-07'),
                                        ('NB8', '2025-01-08'),
                                        ('NB9', '2025-01-09'),
                                        ('NB10', '2025-01-10');


-- ================================
-- FLIGHTS
-- ================================
INSERT INTO flights (id, name, departure_time, airplane_id, noticeboard_id) VALUES
                                                                                ('F1', 'Flight 101', '08:15:00', 'A1', 'NB1'),
                                                                                ('F2', 'Flight 202', '09:20:00', 'A2', 'NB2'),
                                                                                ('F3', 'Flight 303', '10:45:00', 'A3', 'NB3'),
                                                                                ('F4', 'Flight 404', '13:10:00', 'A4', 'NB4'),
                                                                                ('F5', 'Flight 505', '15:00:00', 'A5', 'NB5'),
                                                                                ('F6', 'Flight 606', '17:35:00', 'A6', 'NB6'),
                                                                                ('F7', 'Flight 707', '19:50:00', 'A7', 'NB7'),
                                                                                ('F8', 'Flight 808', '06:55:00', 'A8', 'NB8'),
                                                                                ('F9', 'Flight 909', '11:25:00', 'A9', 'NB9'),
                                                                                ('F10', 'Flight 010', '21:15:00', 'A10', 'NB10');


-- ================================
-- PASSENGERS
-- ================================
INSERT INTO passengers (id, name, currency) VALUES
                                                ('P1', 'Alice', 'EUR'),
                                                ('P2', 'Bob', 'USD'),
                                                ('P3', 'Charlie', 'GBP'),
                                                ('P4', 'Diana', 'EUR'),
                                                ('P5', 'Ethan', 'USD'),
                                                ('P6', 'Fiona', 'GBP'),
                                                ('P7', 'George', 'EUR'),
                                                ('P8', 'Hannah', 'USD'),
                                                ('P9', 'Ivan', 'EUR'),
                                                ('P10', 'Julia', 'GBP');


-- ================================
-- TICKETS
-- ================================
INSERT INTO tickets (id, passenger_id, flight_id, category, price, seat_number) VALUES
                                                                                    ('T1', 'P1', 'F1', 'ECONOMY', 120.50, '12A'),
                                                                                    ('T2', 'P2', 'F2', 'BUSINESS', 520.00, '2C'),
                                                                                    ('T3', 'P3', 'F3', 'ECONOMY', 130.00, '18B'),
                                                                                    ('T4', 'P4', 'F4', 'FIRST_CLASS', 900.00, '1A'),
                                                                                    ('T5', 'P5', 'F5', 'PREMIUM_ECONOMY', 250.00, '7D'),
                                                                                    ('T6', 'P6', 'F6', 'ECONOMY', 110.00, '15F'),
                                                                                    ('T7', 'P7', 'F7', 'BUSINESS', 480.00, '3A'),
                                                                                    ('T8', 'P8', 'F8', 'ECONOMY', 100.00, '20C'),
                                                                                    ('T9', 'P9', 'F9', 'PREMIUM_ECONOMY', 260.00, '6E'),
                                                                                    ('T10', 'P10', 'F10', 'ECONOMY', 115.00, '14B');


-- ================================
-- LUGGAGES
-- ================================
INSERT INTO luggages (id, ticket_id, status, size) VALUES
                                                       ('L1', 'T1', 'CHECKED_IN', 'SMALL'),
                                                       ('L2', 'T2', 'LOADED', 'MEDIUM'),
                                                       ('L3', 'T3', 'DELIVERED', 'LARGE'),
                                                       ('L4', 'T4', 'CHECKED_IN', 'SMALL'),
                                                       ('L5', 'T5', 'LOADED', 'MEDIUM'),
                                                       ('L6', 'T6', 'DELIVERED', 'LARGE'),
                                                       ('L7', 'T7', 'CHECKED_IN', 'SMALL'),
                                                       ('L8', 'T8', 'LOADED', 'MEDIUM'),
                                                       ('L9', 'T9', 'DELIVERED', 'LARGE'),
                                                       ('L10', 'T10', 'CHECKED_IN', 'SMALL');


-- ================================
-- FLIGHT ASSIGNMENTS
-- ================================
INSERT INTO flight_assignments (id, flight_id, airline_employee_id) VALUES
                                                                        ('FA1', 'F1', 'S1'),
                                                                        ('FA2', 'F2', 'S2'),
                                                                        ('FA3', 'F3', 'S3'),
                                                                        ('FA4', 'F4', 'S4'),
                                                                        ('FA5', 'F5', 'S5'),
                                                                        ('FA6', 'F6', 'S1'),
                                                                        ('FA7', 'F7', 'S2'),
                                                                        ('FA8', 'F8', 'S3'),
                                                                        ('FA9', 'F9', 'S4'),
                                                                        ('FA10', 'F10', 'S5');
