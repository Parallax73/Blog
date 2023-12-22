create table token(
    id int auto_increment not null primary key ,
    token varchar(100) not null,
    tokenType varchar(50) not null ,
    revoked boolean not null ,
    expired boolean not null,
    user_id int,

    foreign key (user_id) references users(id)

)