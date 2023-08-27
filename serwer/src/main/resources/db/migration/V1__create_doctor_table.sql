create table `doctor` (
    id bigint not null auto_increment primary key,
    first_name varchar(127) not null,
    last_name varchar(127) not null,
    email varchar(127) not null,
    password_hash varchar(256) not null
);
CREATE UNIQUE INDEX doctor_email_unique_key ON doctor (email);
