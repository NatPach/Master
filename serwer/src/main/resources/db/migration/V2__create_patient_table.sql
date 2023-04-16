create table `patient` (
    id bigint not null primary key,
    first_name varchar(127) not null,
    last_name varchar(127) not null,
    username varchar(127) not null,
    password_hash varchar(256) not null
);
CREATE UNIQUE INDEX patient_username_unique_key ON patient (username);
