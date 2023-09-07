DROP TABLE IF EXISTS `File`;
create table File
(
    file_id               INTEGER             not null
        primary key autoincrement,
    file_name             varchar(255)        not null,
    file_size             varchar(255)        not null,
    file_path             TEXT                not null,
    file_upload_user_name varchar(64),
    file_upload_time      varchar(128),
    file_update_user_name varchar(64),
    file_update_time      varchar(64),
    file_rank             INTEGER,
    file_hash             varchar(255)        not null,
    file_type             INTEGER default 0   not null,
    file_dir              TEXT    default "/" not null,
    file_media_type       file_media_type       INTEGER
);