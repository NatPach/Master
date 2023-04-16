package com.magisterka.interview;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface InterviewRepository extends CrudRepository<InterviewEntity, Long> {
    Optional<InterviewEntity> findByPatientId(Long patientId);
}
