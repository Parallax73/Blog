create table Users(
    id SERIAL not null primary key ,
    email text not null unique,
    password text not null,
    create_Date date not null,
    status text not null,
    role text not null
                  );
