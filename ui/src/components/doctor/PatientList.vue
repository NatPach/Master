<script>
import {useSessionStore} from "@/stores/session";
import config from "@/config";

export default {
  data: function() {
    return {
      patientList: [],
      selectedPatientId: null,
      ankietaWstepna: null,
      ankietaCyklicznaList: null
    };
  },
  setup() {
    const patientHeaders = [
      { text: "Id", value: "id" },
      { text: "Imię", value: "firstName", sortable: true },
      { text: "Nazwisko", value: "lastName", sortable: true },
      { text: "Email", value: "email", sortable: true }
    ];
    const ankietaCyklicznaHeaders = [
      { text: "Tętno", value: "tetno" },
      { text: "Samopoczucie", value: "samopoczucie" },
      { text: "Data pomiaru", value: "createdAt", sortable: true }
    ];
    const sessionStore = useSessionStore();
    return {
      patientHeaders,
      sessionStore,
      ankietaCyklicznaHeaders
    };
  },
  methods: {
    onPatientClick: function (item) {
      const patientId = item.id;
      this.selectedPatientId = patientId;
      this.axios.get(`${config.serverUrl}/patients/${patientId}/ankieta-wstepna`, { headers: {"Authorization" : `Bearer ${this.sessionStore.accessToken()}`} })
          .then(response => {
            this.ankietaWstepna = response.data;
          })
          .catch(error => {
            this.clearPatientDetails();
            if (error.response.status === 404) {
              console.log(`Pacjent o id = ${patientId} nie wypełnił ankiety wstepnej.`);
              return;
            }
            console.log(`Błąd podczas pobierania ankiety wstepnej pacjenta o id = ${patientId}.`, error)
          });
      this.axios.get(`${config.serverUrl}/patients/${patientId}/ankieta-cykliczna`, { headers: {"Authorization" : `Bearer ${this.sessionStore.accessToken()}`} })
          .then(response => {
            this.ankietaCyklicznaList = response.data.map(value => {
              value.samopoczucie = value.samopoczucie.toLowerCase().replace('_', ' ');
              value.createdAt = new Date(value.createdAt).toLocaleString();
              return value;
            });
          })
          .catch(error => {
            console.log(`Błąd podczas pobierania ankiet cyklicznych pacjenta o id = ${patientId}.`, error)
          });
    },
    clearPatientDetails: function () {
      this.ankietaWstepna = null;
    }
  },
  mounted() {
    this.axios.get(`${config.serverUrl}/patients`, { headers: {"Authorization" : `Bearer ${this.sessionStore.accessToken()}`} })
        .then(response => {
          this.patientList = response.data;
        })
        .catch(error => {
          console.log("Błąd podczas pobierania pacjentów.", error)
        });
  }
}
</script>

<template>
  <div class="mb-5">
    <EasyDataTable
        :headers="patientHeaders"
        :items="patientList"
        @click-row="onPatientClick"
    />
  </div>

  <div class="border rounded p-3">
    <div class="h4 mb-3">Szczegóły pacjenta</div>
    <template v-if="ankietaWstepna !== null">
      <div class="mb-3 row">
        <label for="staticWeight" class="col-sm-2 col-form-label">Waga</label>
        <div class="col-sm-10">
          <input type="text" readonly class="form-control-plaintext" id="staticWeight" :value="ankietaWstepna.waga">
        </div>
      </div>
      <div class="mb-3 row">
        <label for="staticHeight" class="col-sm-2 col-form-label">Wzrost</label>
        <div class="col-sm-10">
          <input type="text" readonly class="form-control-plaintext" id="staticHeight" :value="ankietaWstepna.wzrost">
        </div>
      </div>
      <div class="mb-3 row">
        <label for="staticBloodType" class="col-sm-2 col-form-label">Grupa krwi</label>
        <div class="col-sm-10">
          <input type="text" readonly class="form-control-plaintext" id="staticBloodType" :value="ankietaWstepna.grupaKrwi">
        </div>
      </div>
      <div class="h5 mb-3">Ankiety cykliczne</div>
      <EasyDataTable
          :headers="ankietaCyklicznaHeaders"
          :items="ankietaCyklicznaList"
      />
    </template>
    <template v-else>
      <template v-if="selectedPatientId !== null">
        Pacjent o id {{ selectedPatientId }} nie wypełnił jeszcze ankiety.
      </template>
      <template v-else>
        Proszę wybrać pacjenta.
      </template>
    </template>
  </div>
</template>
