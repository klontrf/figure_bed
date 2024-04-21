CREATE DATABASE `figure_bed` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */


create table user
(
    account  varchar(12) not null,
    password varchar(16) not null,
    mail     varchar(32) not null,
    id       int auto_increment
        primary key,
    constraint user_pk
        unique (account)
)
    comment '用户表';

create table source
(
    user_id  int  not null,
    src_path text not null,
    id       int auto_increment
        primary key,
    constraint source_user_id_fk
        foreign key (user_id) references user (id)
            on delete cascade
);

