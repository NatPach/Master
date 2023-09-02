create table `potrzeba_wizyty` (
    id bigint not null auto_increment primary key,
    doctor_id bigint not null,
    patient_id bigint not null,
    details varchar(128) null,
    closed_at timestamp null
);
