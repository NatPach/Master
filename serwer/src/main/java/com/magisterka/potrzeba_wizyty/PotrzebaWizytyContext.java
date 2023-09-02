package com.magisterka.potrzeba_wizyty;

public sealed interface PotrzebaWizytyContext {
    String asString();

    record PotrzebaWizytyDlaLekarza(Long doctorId) implements PotrzebaWizytyContext {
        @Override
        public String asString() {
            return "doctor:" + doctorId;
        }
    }

    record PotrzebaWizytyDlaPacjenta(Long patientId) implements PotrzebaWizytyContext {
        @Override
        public String asString() {
            return "patient:" + patientId;
        }
    }
}
