INSERT INTO users (username, password) VALUES
('demo', 'password'),
('demo', 'password');

INSERT INTO todos (user_id, title, description, due_date, completed) VALUES
(1, 'aaa', 'AAA', '2024-08-01 12:00:00', FALSE),
(1, 'bbb', 'BBB', '2024-08-02 12:00:00', FALSE),
(2, 'ccc', 'CCC', '2024-08-03 12:00:00', FALSE),
(1, 'dddd','aaaaaaaaaaaaaaaaaaaa', '2024-08-04 12:00:00', FALSE),
(1, 'dddd','', '2024-08-04 12:00:00', FALSE);
