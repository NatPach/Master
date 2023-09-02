package com.magisterka.potrzeba_wizyty;

import com.magisterka.ankieta_cykliczna.AnkietaCyklicznaEntity;
import com.magisterka.ankieta_cykliczna.AnkietaCyklicznaRepository;
import com.magisterka.ankieta_cykliczna.Samopoczucie;
import com.magisterka.patient.PatientEntity;
import com.magisterka.patient.PatientRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class PotrzebaWizytyService {
    @Inject
    private PotrzebaWizytyRepository potrzebaWizytyRepository;
    @Inject
    private PatientRepository patientRepository;
    @Inject
    private AnkietaCyklicznaRepository ankietaCyklicznaRepository;

    public List<Long> getListaPacjentowPotrzebujacychWizyty(long doctorId) {
        return potrzebaWizytyRepository.findByDoctorIdAndClosedAtIsNull(doctorId)
                .stream()
                .map(PotrzebaWizytyEntity::getPatientId)
                .distinct()
                .toList();
    }

    public void clearPotrzebyWizytyDlaPacjenta(long doctorId, long patientId) {
        Instant now = Instant.now();
        potrzebaWizytyRepository.updateByDoctorIdAndPatientIdAndClosedAtIsNull(doctorId, patientId, now);
    }

    @Transactional
    public Optional<PotrzebaWizyty> stworzPotrzebeWizytyJesliPotrzeba(AnkietaCyklicznaEntity ankietaCyklicznaEntity) {
        List<String> powodyPotrzebyWizyty = getPowodyPotrzebyWizyty(ankietaCyklicznaEntity);
        if (powodyPotrzebyWizyty.isEmpty()) {
            return Optional.empty();
        }
        String powodyAsString = String.join(", ", powodyPotrzebyWizyty);

        // todo


        resolveLakarzProwadzacyId(ankietaCyklicznaEntity.getPatientId())
                .ifPresent(lekarzProwadzacyId -> potrzebaWizytyRepository.save(new PotrzebaWizytyEntity(
                        null,
                        lekarzProwadzacyId,
                        ankietaCyklicznaEntity.getPatientId(),
                        powodyAsString,
                        null)));

        return Optional.of(new PotrzebaWizyty(powodyAsString, ankietaCyklicznaEntity.getPatientId()));
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

    private Optional<Long> resolveLakarzProwadzacyId(Long patientId) {
        return patientRepository.findById(patientId)
                .map(PatientEntity::getLekarzProwadzacyId);
    }
}
