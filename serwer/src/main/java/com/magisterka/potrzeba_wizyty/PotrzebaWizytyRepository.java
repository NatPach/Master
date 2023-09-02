package com.magisterka.potrzeba_wizyty;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface PotrzebaWizytyRepository extends CrudRepository<PotrzebaWizytyEntity, Long> {
    List<PotrzebaWizytyEntity> findByContextAndClosedAtIsNotNull(String context);
}
