<script>
import Table from "@/components/Table.vue";
import {useSessionStore} from "@/stores/session";
import config from "@/config";
import PatientDetails from "@/components/doctor/PatientDetails.vue";

export default {
  components: {
    PatientDetails,
    Table,
  },
  data: function () {
    return {
      columns: [
        {
          name: 'Imię',
          key: 'firstName'
        },
        {
          name: 'Nazwisko',
          key: 'lastName'
        },
        {
          name: "Pesel",
          key: "pesel"
        },
        {
          name: "Numer telefonu",
          key: "phone"
        },
      ],
      data: [],
      patientMap: {},
      selectedPatient: null,
    };
  },
  setup() {
    const sessionStore = useSessionStore();
    return {
      sessionStore
    };
  },
  methods: {
    onPatientClick: function (item) {
      this.selectedPatient = item;
    },
    loadPatientMap: function () {
      return this.axios.get(`${config.serverUrl}/patients`, { headers: {"Authorization" : `Bearer ${this.sessionStore.accessToken()}`} })
          .then(response => {
            response.data.forEach(patient => this.patientMap[patient.id] = patient);
          })
          .catch(error => {
            console.log("Błąd podczas pobierania pacjentów.", error)
          });
    },
    loadPatientsPotrzebujacychWizyty: function () {
      this.axios.get(`${config.serverUrl}/potrzeby-wizyty`, { headers: {"Authorization" : `Bearer ${this.sessionStore.accessToken()}`} })
          .then(response => {
            this.data = response.data.map(patient => this.patientMap[patient.patientId]);
          })
          .catch(error => {
            console.log("Błąd podczas pobierania pacjentów.", error)
          });
    },
    backToList: function () {
      this.data = [];
      this.loadPatientsPotrzebujacychWizyty();
      this.selectedPatient = null;
    }
  },
  mounted() {
    this.loadPatientMap()
        .then(() => this.loadPatientsPotrzebujacychWizyty());
  }
}
</script>

<template>
  <template v-if="selectedPatient === null">
    <template v-if="data.length === 0">
      <div class="alert alert-success" role="alert">
        <h4 class="alert-heading">Dobra wiadomość!</h4>
        <p>Lista pacjentów potrzebujących wizyty jest pusta!</p>
        <hr>
        <p class="mb-0">Gdy tylko którykolwiek pacjent będzie potrzebował wizyty pojawi się na liście w tym miejscu.</p>
      </div>
    </template>
    <template v-else>
      <div class="h4 mb-3">
        Pacjenci potrzebujący wizyt
      </div>
      <div class="mb-5">
        <Table :data="data" :columns="columns" :on-row-click="onPatientClick"/>
      </div>
    </template>
  </template>
  <template v-else>
    <div class="mb-3">
      <button type="button" class="btn btn-outline-primary" @click="backToList">Wróć do listy pacjenów potrzebujących wizyty</button>
    </div>
    <PatientDetails :patient="selectedPatient"/>
  </template>
</template>
