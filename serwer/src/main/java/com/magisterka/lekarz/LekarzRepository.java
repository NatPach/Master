package com.magisterka.lekarz;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface LekarzRepository extends CrudRepository<LekarzEntity, Long> {
    Optional<LekarzEntity> find(String username);
}
