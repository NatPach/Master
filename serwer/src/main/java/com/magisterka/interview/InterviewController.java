package com.magisterka.interview;

import com.magisterka.security.AuthAttributesProvider;
import com.magisterka.security.SecurityRoles;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;
import io.micronaut.security.annotation.Secured;
import jakarta.inject.Inject;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Secured(SecurityRoles.PATIENT)
@Controller
public class InterviewController {
    @Inject
    private AuthAttributesProvider authAttributesProvider;
    @Inject
    private InterviewRepository interviewRepository;

    @Get("/patient/interview")
    public Optional<Interview> getPatientInterview() {
        long patientId = authAttributesProvider.getPatientId();
        return interviewRepository.findByPatientId(patientId)
                .map(entity -> new Interview(entity.getWeight(), entity.getHeight(), entity.getBloodType()));
    }

    @Put("/patient/interview")
    public Interview putPatientInterview(@NotNull @Valid @Body Interview interview) {
        long patientId = authAttributesProvider.getPatientId();
        InterviewEntity entity = new InterviewEntity(null, patientId, interview.getWeight(), interview.getHeight(), interview.getBloodType());
        return interviewRepository.findByPatientId(patientId)
                .map(existing -> {
                    entity.setId(existing.getId());
                    return interviewRepository.update(entity);
                })
                .or(() -> Optional.of(interviewRepository.save(entity)))
                .map(value -> new Interview(value.getWeight(), value.getHeight(), value.getBloodType()))
                .orElseThrow();
    }
}
