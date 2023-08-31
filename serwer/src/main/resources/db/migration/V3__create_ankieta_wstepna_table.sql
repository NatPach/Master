create table `ankieta_wstepna` (
    id bigint not null auto_increment primary key,
    patient_id bigint not null,
    height float not null,
    blood_type varchar(127) not null,
    tryb_zycia varchar(127) not null,
    przyjmowane_leki varchar(512) null,
    alergie varchar(512) null
);
