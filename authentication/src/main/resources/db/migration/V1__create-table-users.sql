CREATE TABLE if not exists users (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    email VARCHAR(50) NOT NULL UNIQUE,
    username VARCHAR(15) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    creation_date datetime NOT NULL
);