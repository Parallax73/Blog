create table Posts (
    id SERIAL not null primary key ,
    text text not null,
    date date not null,
    author text not null
);