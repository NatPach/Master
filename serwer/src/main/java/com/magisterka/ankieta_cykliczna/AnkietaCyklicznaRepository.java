package com.magisterka.ankieta_cykliczna;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface AnkietaCyklicznaRepository extends CrudRepository<AnkietaCyklicznaEntity, Long> {
    List<AnkietaCyklicznaEntity> findByPatientId(Long patientId);
}
