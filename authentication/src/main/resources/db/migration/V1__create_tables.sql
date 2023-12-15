create table Users(
    id SERIAL not null primary key ,
    email text not null unique,
    username text not null unique,
    password text not null,
    create_Date date not null,
    role text not null
);
