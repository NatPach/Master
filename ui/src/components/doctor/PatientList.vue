<script>
import {useSessionStore} from "@/stores/session";
import Table from "@/components/Table.vue";
import PatientDetails from "@/components/doctor/PatientDetails.vue";
import config from "@/config";

export default {
  components: {
    Table,
    PatientDetails,
  },
  data: function() {
    return {
      patientTable: {
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
          {
            name: "Email",
            key: "email"
          },
        ],
        data: [],
      },
      selectedPatient: null,
    };
  },
  setup() {
    const sessionStore = useSessionStore();
    return { sessionStore };
  },
  methods: {
    onPatientClick: function (item) {
      this.selectedPatient = item;
    },
  },
  mounted() {
    this.axios.get(`${config.serverUrl}/patients`, { headers: {"Authorization" : `Bearer ${this.sessionStore.accessToken()}`} })
        .then(response => {
          this.patientTable.data = response.data;
        })
        .catch(error => {
          console.log("Błąd podczas pobierania pacjentów.", error)
        });
  }
}
</script>

<template>
  <template v-if="selectedPatient === null">
    <div class="h4 mb-3">
      Lista wszystkich pacjentów
    </div>
    <div>
      <Table :columns="patientTable.columns" :data="patientTable.data" :on-row-click="onPatientClick"/>
    </div>
  </template>
  <template v-else>
    <div class="mb-3">
      <button type="button" class="btn btn-outline-primary" @click="() => selectedPatient = null">Wróć do listy pacjenów</button>
    </div>
    <PatientDetails :patient="selectedPatient"/>
  </template>
</template>
