DROP TABLE IF EXISTS `UserAction`;
create table UserAction
(
    action_id   INTEGER           not null
        primary key autoincrement,
    user_name   varchar(64)       not null,
    action_type INTEGER default 0 not null,
    file_id     INTEGER           not null,
    file_name   varchar(255)      not null,
    action_time varchar(128)      not null
);