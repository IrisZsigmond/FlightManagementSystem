-- ============================
-- STAFF (20 entries)
-- ============================
INSERT INTO staff (id, staff_type, name) VALUES
                                             ('S001', 'AIRLINE', 'Robert Ionescu'),
                                             ('S002', 'AIRLINE', 'Elena Popescu'),
                                             ('S003', 'AIRLINE', 'Cristian Pavel'),
                                             ('S004', 'AIRLINE', 'Andreea Dobre'),
                                             ('S005', 'AIRLINE', 'Sorin Preda'),
                                             ('S006', 'AIRLINE', 'Ioana Cristea'),
                                             ('S007', 'AIRLINE', 'Marius Ene'),
                                             ('S008', 'AIRLINE', 'Denisa Grigore'),
                                             ('S009', 'AIRLINE', 'Raluca Timofte'),
                                             ('S010', 'AIRLINE', 'Cătălin Burlacu'),

                                             ('S011', 'AIRPORT', 'Mihai Enache'),
                                             ('S012', 'AIRPORT', 'Sorina Vlad'),
                                             ('S013', 'AIRPORT', 'Alexandra Matei'),
                                             ('S014', 'AIRPORT', 'Victor Marinescu'),
                                             ('S015', 'AIRPORT', 'Diana Manolescu'),
                                             ('S016', 'AIRPORT', 'Sebastian Radu'),
                                             ('S017', 'AIRPORT', 'Laura Iftimie'),
                                             ('S018', 'AIRPORT', 'Ovidiu Constantin'),
                                             ('S019', 'AIRPORT', 'Teodora Ilie'),
                                             ('S020', 'AIRPORT', 'Adrian Vălean');

-- ============================
-- AIRLINE EMPLOYEES (10)
-- ============================
INSERT INTO airline_employee (id, role) VALUES
                                            ('S001', 'PILOT'),
                                            ('S002', 'CO_PILOT'),
                                            ('S003', 'FLIGHT_ATTENDANT'),
                                            ('S004', 'FLIGHT_ATTENDANT'),
                                            ('S005', 'PILOT'),
                                            ('S006', 'CO_PILOT'),
                                            ('S007', 'FLIGHT_ATTENDANT'),
                                            ('S008', 'FLIGHT_ATTENDANT'),
                                            ('S009', 'GROUND_STAFF'),
                                            ('S010', 'GROUND_STAFF');

-- ============================
-- AIRPORT EMPLOYEES (10)
-- ============================
INSERT INTO airport_employee (id, department, designation) VALUES
                                                               ('S011', 'Baggage Handling', 'Loader'),
                                                               ('S012', 'Customer Service', 'Check-in Agent'),
                                                               ('S013', 'Security', 'Security Officer'),
                                                               ('S014', 'Gate Operations', 'Gate Agent'),
                                                               ('S015', 'Customs', 'Passport Control'),
                                                               ('S016', 'Maintenance', 'Technician'),
                                                               ('S017', 'Cleaning', 'Janitor'),
                                                               ('S018', 'Customer Service', 'Supervisor'),
                                                               ('S019', 'Baggage Handling', 'Sorter'),
                                                               ('S020', 'Security', 'Scanner Operator');

-- ============================
-- AIRPLANES (10)
-- ============================
INSERT INTO airplanes (id, number, capacity) VALUES
                                                 ('A001', 1001, 180),
                                                 ('A002', 1002, 220),
                                                 ('A003', 1003, 150),
                                                 ('A004', 1004, 200),
                                                 ('A005', 1005, 165),
                                                 ('A006', 1006, 120),
                                                 ('A007', 1007, 140),
                                                 ('A008', 1008, 250),
                                                 ('A009', 1009, 300),
                                                 ('A010', 1010, 190);

-- ============================
-- NOTICEBOARDS (10)
-- ============================
INSERT INTO noticeboards (id, date) VALUES
                                        ('N001', '2025-12-01'),
                                        ('N002', '2025-12-02'),
                                        ('N003', '2025-12-03'),
                                        ('N004', '2025-12-04'),
                                        ('N005', '2025-12-05'),
                                        ('N006', '2025-12-06'),
                                        ('N007', '2025-12-07'),
                                        ('N008', '2025-12-08'),
                                        ('N009', '2025-12-09'),
                                        ('N010', '2025-12-10');

-- ============================
-- FLIGHTS (10)
-- ============================
INSERT INTO flights (id, name, departure_time, airplane_id, noticeboard_id) VALUES
                                                                                ('F001', 'RO101', '08:00:00', 'A001', 'N001'),
                                                                                ('F002', 'RO202', '10:15:00', 'A002', 'N002'),
                                                                                ('F003', 'RO303', '12:30:00', 'A003', 'N003'),
                                                                                ('F004', 'RO404', '14:45:00', 'A004', 'N004'),
                                                                                ('F005', 'RO505', '16:00:00', 'A005', 'N005'),
                                                                                ('F006', 'RO606', '18:20:00', 'A006', 'N006'),
                                                                                ('F007', 'RO707', '20:10:00', 'A007', 'N007'),
                                                                                ('F008', 'RO808', '06:40:00', 'A008', 'N008'),
                                                                                ('F009', 'RO909', '09:55:00', 'A009', 'N009'),
                                                                                ('F010', 'RO010', '22:30:00', 'A010', 'N010');

-- ============================
-- PASSENGERS (10)
-- ============================
INSERT INTO passengers (id, name, currency) VALUES
                                                ('P001', 'Alexandru Marin', 'EUR'),
                                                ('P002', 'Bianca Radu', 'USD'),
                                                ('P003', 'Tudor Gheorghe', 'EUR'),
                                                ('P004', 'Ioana Marinescu', 'EUR'),
                                                ('P005', 'Vlad Stan', 'GBP'),
                                                ('P006', 'Cristina Gălățeanu', 'USD'),
                                                ('P007', 'Mihai Florescu', 'EUR'),
                                                ('P008', 'Ana Cernat', 'EUR'),
                                                ('P009', 'Radu Neagu', 'USD'),
                                                ('P010', 'Elisa Petcu', 'EUR');

-- ============================
-- TICKETS (10)
-- ============================
INSERT INTO tickets (id, seat_number, category, price, passenger_id, flight_id) VALUES
                                                                                    ('T001', '12A', 'ECONOMY', 120.00, 'P001', 'F001'),
                                                                                    ('T002', '14C', 'ECONOMY', 130.00, 'P002', 'F001'),
                                                                                    ('T003', '2D', 'BUSINESS', 450.00, 'P003', 'F002'),
                                                                                    ('T004', '3A', 'BUSINESS', 470.00, 'P004', 'F003'),
                                                                                    ('T005', '20B', 'ECONOMY', 110.00, 'P005', 'F004'),
                                                                                    ('T006', '7F', 'PREMIUM_ECONOMY', 220.00, 'P006', 'F005'),
                                                                                    ('T007', '1A', 'FIRST_CLASS', 900.00, 'P007', 'F006'),
                                                                                    ('T008', '15D', 'ECONOMY', 115.00, 'P008', 'F007'),
                                                                                    ('T009', '9C', 'PREMIUM_ECONOMY', 250.00, 'P009', 'F008'),
                                                                                    ('T010', '4B', 'BUSINESS', 500.00, 'P010', 'F009');

-- ============================
-- LUGGAGES (10)
-- ============================
INSERT INTO luggages (id, size, status, ticket_id) VALUES
                                                       ('L001', 'MEDIUM', 'CHECKED_IN', 'T001'),
                                                       ('L002', 'LARGE', 'CHECKED_IN', 'T002'),
                                                       ('L003', 'SMALL', 'DELIVERED', 'T003'),
                                                       ('L004', 'MEDIUM', 'LOADED', 'T004'),
                                                       ('L005', 'LARGE', 'DELIVERED', 'T005'),
                                                       ('L006', 'SMALL', 'CHECKED_IN', 'T006'),
                                                       ('L007', 'MEDIUM', 'LOADED', 'T007'),
                                                       ('L008', 'LARGE', 'CHECKED_IN', 'T008'),
                                                       ('L009', 'SMALL', 'DELIVERED', 'T009'),
                                                       ('L010', 'MEDIUM', 'LOADED', 'T010');

-- ============================
-- FLIGHT ASSIGNMENTS (10)
-- ============================
INSERT INTO flight_assignments (id, airline_employee_id, flight_id) VALUES
                                                                        ('FA001', 'S001', 'F001'),
                                                                        ('FA002', 'S002', 'F001'),
                                                                        ('FA003', 'S003', 'F002'),
                                                                        ('FA004', 'S004', 'F003'),
                                                                        ('FA005', 'S005', 'F004'),
                                                                        ('FA006', 'S006', 'F005'),
                                                                        ('FA007', 'S007', 'F006'),
                                                                        ('FA008', 'S008', 'F007'),
                                                                        ('FA009', 'S009', 'F008'),
                                                                        ('FA010', 'S010', 'F009');
