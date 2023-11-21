CREATE TABLE IF NOT EXISTS users
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    age       TINYINT      NOT NULL,
    email     VARCHAR(100) NOT NULL
);
ALTER TABLE users
    ADD COLUMN gender VARCHAR(30) NOT NULL AFTER last_name;

INSERT INTO users(name, last_name, gender, age, email)
VALUES ('Иван', 'Иванович', 'Мужской', 34, 'iii@gmail.com');
INSERT INTO users(name, last_name, gender, age, email)
VALUES ('Георгий', 'Петрович', 'Мужской', 22, 'gp@gmail.com');
INSERT INTO users(name, last_name, gender, age, email)
VALUES ('Александ', 'Сергеевич', 'Мужской', 44, 'sssss@gmail.com');
INSERT INTO users(name, last_name, gender, age, email)
VALUES ('Кирилл', 'Алексеевич', 'Мужской', 26, 'KPA@gmail.com');


DROP TABLE IF EXISTS users;
