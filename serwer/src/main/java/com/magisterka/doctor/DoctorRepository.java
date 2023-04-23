package com.magisterka.doctor;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends CrudRepository<DoctorEntity, Long> {
    Optional<DoctorEntity> find(String email);
}
