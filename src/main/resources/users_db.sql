CREATE TABLE IF NOT EXISTS users
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(300) NOT NULL,
    gender   VARCHAR(30)  NOT NULL,
    age      TINYINT      NOT NULL,
    email    VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL
);
INSERT INTO users(name, gender, age, email, password)
VALUES ('Иванов Иван Иванович', 'Мужской', 34, 'iii@gmail.com', 'gg1');
INSERT INTO users(name, gender, age, email, password)
VALUES ('Пугачёв Георгий Валентинович', 'Мужской', 22, 'gp@gmail.com', 'gg1');
INSERT INTO users(name, gender, age, email, password)
VALUES ('Шумилова Елена Алексеевна', 'Женский', 44, 'sssss@gmail.com', 'gg1');
INSERT INTO users(name, gender, age, email, password)
VALUES ('Иванов Денис Геннадьевич', 'Мужской', 26, 'idg@gmail.com', 'gg1');



CREATE TABLE IF NOT EXISTS roles
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(100) NOT NULL
);
INSERT INTO roles(role_name)
    VALUE ('ROLE_USER');
INSERT INTO roles(role_name)
    VALUE ('ROLE_ADMIN');


CREATE TABLE users_roles
(
    user_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);


DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
