create table `lekarz` (
    id bigint not null primary key,
    imie varchar(127) not null,
    nazwisko varchar(127) not null,
    username varchar(127) not null,
    password_hash varchar(256) not null
);
CREATE UNIQUE INDEX lekarz_username_unique_key ON lekarz (username);
