INSERT INTO Parent (id, firstname, lastname) VALUES (1, 'Jan', 'Kowalski');
INSERT INTO Parent (id, firstname, lastname) VALUES (2, 'Maria', 'Nowak');

INSERT INTO School (id, name, hour_price) VALUES (1, 'Szkoła 1', '8.5');
INSERT INTO School (id, name, hour_price) VALUES (2, 'Szkoła 2', '5.25');

INSERT INTO Child (id, firstname, lastname, parent_id, school_id) VALUES (1, 'Brajan', 'Kowalski', 1, 1);
INSERT INTO Child (id, firstname, lastname, parent_id, school_id) VALUES (2, 'Dżesika', 'Kowalska', 1, 1);
INSERT INTO Child (id, firstname, lastname, parent_id, school_id) VALUES (3, 'Jasio', 'Nowak', 2, 1);
INSERT INTO Child (id, firstname, lastname, parent_id, school_id) VALUES (4, 'Basia', 'Nowak', 2, 2);

INSERT INTO Attendance (id, child_id, entry_date, exit_date) VALUES (1, 1, '2024-01-02 07:00:00', '2024-01-02 15:00:00');
INSERT INTO Attendance (id, child_id, entry_date, exit_date) VALUES (2, 2, '2024-01-02 06:59:00', '2024-01-02 12:01:00');
INSERT INTO Attendance (id, child_id, entry_date, exit_date) VALUES (3, 3, '2024-01-03 08:30:00', '2024-01-03 11:05:00');
INSERT INTO Attendance (id, child_id, entry_date, exit_date) VALUES (4, 3, '2024-01-03 05:30:00', '2024-01-03 07:05:00');
INSERT INTO Attendance (id, child_id, entry_date, exit_date) VALUES (5, 4, '2024-01-02 06:59:00', '2024-01-02 14:01:00');