CREATE TABLE if not exists _user (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    creation_date datetime NOT NULL,
    role varchar(255)
);

CREATE TABLE if not exists token(
    expired boolean not null,
    id int auto_increment not null primary key,
    revoked boolean not null,
    user_id int,
    token varchar(255),
    token_type varchar(255),

    foreign key (user_id) references _user(id)

);