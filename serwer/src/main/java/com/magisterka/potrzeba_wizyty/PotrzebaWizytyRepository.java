package com.magisterka.potrzeba_wizyty;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.time.Instant;
import java.util.List;

@Repository
public interface PotrzebaWizytyRepository extends CrudRepository<PotrzebaWizytyEntity, Long> {
    List<PotrzebaWizytyEntity> findByContextAndClosedAtIsNotNull(String context);

    List<PotrzebaWizytyEntity> findByPatientId(Long patientId);

    void updateByContextAndPatientIdAndClosedAtIsNull(String context, Long patientId, Instant closedAt);
}
