create table `patient` (
    id bigint not null auto_increment primary key,
    first_name varchar(127) not null,
    middle_name varchar(127) null,
    last_name varchar(127) not null,
    pesel char(11) not null,
    email varchar(63) not null,
    phone varchar(15) not null,
    password_hash varchar(256) not null,
    lekarz_prowadzacy_id bigint null
);
CREATE UNIQUE INDEX patient_email_unique_key ON patient (email);
