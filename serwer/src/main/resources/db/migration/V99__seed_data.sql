# seed doctors
INSERT INTO doctor (id, first_name, last_name, phone, email, password_hash)
VALUES (1, 'Kamil', 'Lekarz', '+48 999 888 777', 'lekarz@gmail.com', '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08');

# seed patients
INSERT INTO patient (id, first_name, middle_name, last_name, pesel, phone, email, password_hash, lekarz_prowadzacy_id)
VALUES (1, 'Tomek', 'Grzegorz', 'Pacjent', '96082600000', '+48 123 542 543', 'pacjent@gmail.com', '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08', 1),
       (2, 'Inny', null, 'Pacjent', '11111111111', '+48 123 123 213', 'nowy@gmail.com', '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08', null),
       (3, 'Kamil', 'Grzegorz', 'Pacjent', '33333333333', '+48 123 999 543', 'pacjent1@gmail.com', '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08', 1),
       (4, 'Ania', null, 'Pacjent 2', '22222222222', '+48 111 542 543', 'pacjent2@gmail.com', '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08', 1);

# seed ankieta wstepna
INSERT INTO ankieta_wstepna (patient_id, height, blood_type, tryb_zycia, przyjmowane_leki, alergie)
VALUES (1, 185,  'ZERO_PLUS', 'AKTYWNY', 'claritine', 'orzechy ziemne, pyłki');

# seed ankieta cykliczna
INSERT INTO ankieta_cykliczna (patient_id, samopoczucie, tetno, waga, potrzeba_wizyty, inne_uwagi_zdrowotne, created_at)
VALUES (1, 'GORSZE', 190, 93, false, 'bolaca glowa', '2023-08-20 10:57:50'),
       (1, 'LEPSZE', 130, 94, false, null, '2023-08-20 15:57:52'),
       (1, 'BEZ_ZMIAN', 120, 93, false, null, '2023-08-20 20:57:54'),
       (1, 'BEZ_ZMIAN', 121, 93, false, null, '2023-08-21 18:36:22');
