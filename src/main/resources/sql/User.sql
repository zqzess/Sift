DROP TABLE IF EXISTS `User`;
create table User
(
    user_id       INTEGER           not null
        primary key autoincrement,
    user_name     varchar(64)       not null
        unique,
    user_password TEXT              not null,
    user_rank     INTEGER default 0 not null
);
--INSERT INTO User (user_id, user_name, user_password, user_rank) VALUES (1, 'root', 'root', 10);
