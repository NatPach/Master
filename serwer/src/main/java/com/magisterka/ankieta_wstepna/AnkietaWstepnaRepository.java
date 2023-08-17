package com.magisterka.ankieta_wstepna;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface AnkietaWstepnaRepository extends CrudRepository<AnkietaWstepnaEntity, Long> {
    Optional<AnkietaWstepnaEntity> findByPatientId(Long patientId);
}
