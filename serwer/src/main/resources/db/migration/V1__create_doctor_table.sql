create table `doctor` (
    id bigint not null primary key,
    first_name varchar(127) not null,
    last_name varchar(127) not null,
    username varchar(127) not null,
    password_hash varchar(256) not null
);
CREATE UNIQUE INDEX doctor_username_unique_key ON doctor (username);
