CREATE TABLE Employee(
    eid int primary key AUTO_INCREMENT,
    ename varchar(50),
    salary bigint,
    deptname varchar(50)
);
-- Insert dummy employee records
INSERT INTO Employee (ename, salary, deptname)
VALUES
('John Doe', 50000, 'HR'),
('Jane Smith', 65000, 'Finance'),
('Michael Johnson', 72000, 'IT'),
('Emily Davis', 48000, 'Marketing'),
('Robert Brown', 85000, 'Operations'),
('Sophia Wilson', 55000, 'Sales'),
('David Miller', 60000, 'Finance'),
('Olivia Taylor', 70000, 'IT');
CREATE TABLE User(
    uid int primary key,
    username varchar (50),
    password varchar(68),
    role varchar(50)
);
-- Dummy users for Spring Security with BCrypt passwords
-- Real password: admin123
INSERT INTO Users (uid, username, password, role) VALUES
(1, 'admin', '$2a$10$7QkYQmVjYhQ1hPjYvQfFhUuYkQjV7XkWZkXqYjV7XkWZkXqYjV7XkW', 'ROLE_ADMIN');

-- Real password: user123
INSERT INTO User (uid, username, password, role) VALUES
(2, 'user', '$2a$10$Q9hPjYvQfFhUuYkDow1wYkYwQ1hPjYvQfFhUuYkDow1wYkYwQ1hPj', 'ROLE_USER');

-- Real password: manager123
INSERT INTO User (uid, username, password, role) VALUES
(3, 'manager', '$2a$10$AbCdEfGhIjKlMnOpQrStUvWxYz1234567890abcdefghijklmnop', 'ROLE_MANAGER');