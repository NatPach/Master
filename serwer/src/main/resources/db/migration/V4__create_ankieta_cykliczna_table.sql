create table `ankieta_cykliczna` (
    id bigint not null auto_increment primary key,
    patient_id bigint not null,
    tetno int not null,
    samopoczucie varchar(64) not null,
    uwagi_zdrowotne varchar(255) null,
    inne_uwagi_zdrowotne varchar(255) null,
    created_at timestamp not null
);
