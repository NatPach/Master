create table `ankieta_cykliczna` (
    id bigint not null auto_increment primary key,
    patient_id bigint not null,
    tetno int not null,
    samopoczucie varchar(64) not null,
    created_at timestamp not null
);
