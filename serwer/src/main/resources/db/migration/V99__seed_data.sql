# seed doctors
INSERT INTO doctor (id, first_name, last_name, email, password_hash)
VALUES (1, 'Kamil', 'Lekarz', 'lekarz@gmail.com', '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08');

# seed patients
INSERT INTO patient (id, first_name, middle_name, last_name, pesel, email, password_hash, lekarz_prowadzacy_id)
VALUES (1, 'Tomek', 'Grzegorz', 'Pacjent', '96082600000', 'pacjent@gmail.com', '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08', 1),
       (2, 'Inny', null, 'Pacjent', '11111111111', 'nowy@gmail.com', '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08', null);

# seed ankieta wstepna
INSERT INTO ankieta_wstepna (patient_id, weight, height, blood_type, tryb_zycia, przyjmowane_leki, alergie)
VALUES (1, 93, 185,  'ZERO_PLUS', 'AKTYWNY', 'claritine', 'orzechy ziemne, pyłki');

# seed ankieta cykliczna
INSERT INTO ankieta_cykliczna (patient_id, samopoczucie, tetno, uwagi_zdrowotne, inne_uwagi_zdrowotne, created_at)
VALUES (1, 'GORSZE', 190, 'SPADEK_WAGI,POTRZEBA_WIZYTY', 'bolaca glowa', '2023-08-20 10:57:50'),
       (1, 'LEPSZE', 130, null, null, '2023-08-20 15:57:52'),
       (1, 'BEZ_ZMIAN', 120, 'SPADEK_WAGI', null, '2023-08-20 20:57:54'),
       (1, 'BEZ_ZMIAN', 121, null, null, '2023-08-21 18:36:22');