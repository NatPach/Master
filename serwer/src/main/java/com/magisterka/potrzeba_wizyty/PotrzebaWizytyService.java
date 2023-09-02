package com.magisterka.potrzeba_wizyty;

import com.magisterka.ankieta_cykliczna.AnkietaCyklicznaEntity;
import com.magisterka.ankieta_cykliczna.AnkietaCyklicznaRepository;
import com.magisterka.ankieta_cykliczna.Samopoczucie;
import com.magisterka.patient.PatientEntity;
import com.magisterka.patient.PatientRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class PotrzebaWizytyService {
    @Inject
    private PotrzebaWizytyRepository potrzebaWizytyRepository;
    @Inject
    private PatientRepository patientRepository;
    @Inject
    private AnkietaCyklicznaRepository ankietaCyklicznaRepository;

    public List<PotrzebaWizyty> getNotClosedPotrzebyWizyty(PotrzebaWizytyContext context) {
        return potrzebaWizytyRepository.findByContextAndClosedAtIsNotNull(context.asString())
                .stream()
                .map(this::mapFromEntity)
                .toList();
    }

    public void stworzPotrzebeWizytyJesliPotrzeba(AnkietaCyklicznaEntity ankietaCyklicznaEntity) {
        List<String> powodyPotrzebyWizyty = getPowodyPotrzebyWizyty(ankietaCyklicznaEntity);
        if (powodyPotrzebyWizyty.isEmpty()) {
            return;
        }
        String powodyAsString = String.join(", ", powodyPotrzebyWizyty);
        List<PotrzebaWizytyContext> contexts = resolveContext(ankietaCyklicznaEntity.getPatientId());
        contexts.forEach(context -> potrzebaWizytyRepository.save(new PotrzebaWizytyEntity(
                null,
                context.asString(),
                ankietaCyklicznaEntity.getPatientId(),
                powodyAsString,
                null)));
    }

    private List<String> getPowodyPotrzebyWizyty(AnkietaCyklicznaEntity ankietaCyklicznaEntity) {
        List<String> powody = new ArrayList<>();
        if (ankietaCyklicznaEntity.getTetno() > 100) {
            powody.add("wysokie tętno");
        }
        if (ankietaCyklicznaEntity.getTetno() < 60) {
            powody.add("niskie tętno");
        }
        List<AnkietaCyklicznaEntity> ankietyCykliczneZOstatniegoTygodnia = ankietaCyklicznaRepository.findByPatientIdAndCreatedAtAfterOrderByCreatedAtAsc(ankietaCyklicznaEntity.getPatientId(), Instant.now().minus(Duration.ofDays(7)));
        if (ankietyCykliczneZOstatniegoTygodnia.size() >= 3) {
            long gorszeSamopoczucia = ankietyCykliczneZOstatniegoTygodnia.stream()
                    .filter(ankieta -> Samopoczucie.GORSZE.equals(ankieta.getSamopoczucie()))
                    .count();
            if (gorszeSamopoczucia >= ankietyCykliczneZOstatniegoTygodnia.size() - 1) {
                powody.add("gorsze samopoczucie");
            }

            double roznicaWagi = ankietyCykliczneZOstatniegoTygodnia.get(0).getWaga() - ankietyCykliczneZOstatniegoTygodnia.get(ankietyCykliczneZOstatniegoTygodnia.size() - 1).getWaga();
            if (roznicaWagi >= 5) {
                powody.add("spadek wagi");
            } else if (roznicaWagi <= -5) {
                powody.add("wzrost wagi");
            }
        }
        return powody;
    }

    private List<PotrzebaWizytyContext> resolveContext(Long patientId) {
        List<PotrzebaWizytyContext> contexts = new ArrayList<>();
        contexts.add(new PotrzebaWizytyContext.PotrzebaWizytyDlaPacjenta(patientId));
        patientRepository.findById(patientId)
                .map(PatientEntity::getLekarzProwadzacyId)
                .ifPresent(lekarzProwadzacyId -> contexts.add(new PotrzebaWizytyContext.PotrzebaWizytyDlaLekarza(lekarzProwadzacyId)));
        return contexts;
    }

    private PotrzebaWizyty mapFromEntity(PotrzebaWizytyEntity entity) {
        return new PotrzebaWizyty(
                entity.getId(),
                entity.getDetails(),
                entity.getPatientId());
    }
}
