<script>
import {useSessionStore} from "@/stores/session";
import Table from "@/components/Table.vue";
import AnkietaCyklicznaTable from "@/components/AnkietaCyklicznaTable.vue";
import config from "@/config";

export default {
  components: {
    Table,
    AnkietaCyklicznaTable
  },
  props: {
    patient: {
      type: Object,
      required: true
    }
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
      ankietaWstepna: null,
      ankietaCyklicznaData: []
    };
  },
  setup() {
    const sessionStore = useSessionStore();
    return { sessionStore };
  },
  methods: {
    fillAnkietaWstepna() {
      this.axios.get(`${config.serverUrl}/patients/${this.patient.id}/ankieta-wstepna`, { headers: {"Authorization" : `Bearer ${this.sessionStore.accessToken()}`} })
          .then(response => {
            this.ankietaWstepna = response.data;
          })
          .catch(error => {
            this.ankietaWstepna = null;
            if (error.response.status === 404) {
              console.log(`Pacjent o id = ${this.patient.id} nie wypełnił ankiety wstepnej.`);
              return;
            }
            console.log(`Błąd podczas pobierania ankiety wstepnej pacjenta o id = ${this.patient.id}.`, error)
          });
    },
    fillAnkietyCykliczne() {
      this.axios.get(`${config.serverUrl}/patients/${this.patient.id}/ankieta-cykliczna`, { headers: {"Authorization" : `Bearer ${this.sessionStore.accessToken()}`} })
          .then(response => {
            this.ankietaCyklicznaData = response.data;
          })
          .catch(error => {
            this.ankietaCyklicznaData = [];
            console.log(`Błąd podczas pobierania ankiet cyklicznych pacjenta o id = ${this.patient.id}.`, error)
          });
    },
    oznaczPotrzebyWizytyJakoPrzeczytane() {
      this.axios.delete(`${config.serverUrl}/patients/${this.patient.id}/potrzeby-wizyty`, { headers: {"Authorization" : `Bearer ${this.sessionStore.accessToken()}`} })
          .then(() => {
            console.log(`Poprawnie wyczyszczono potrzeby wizyty dla pacjenta z id = ${this.patient.id}`);
          })
          .catch(error => {
            console.log(`Błąd podczas czyszczenia potrzeb wizyt pacjenta o id = ${this.patient.id}.`, error);
          });
    }
  },
  mounted() {
    this.fillAnkietaWstepna();
    this.fillAnkietyCykliczne();
    this.oznaczPotrzebyWizytyJakoPrzeczytane();
  }
}
</script>

<template>
  <div class="border rounded p-3">
    <div class="h4 mb-3">Szczegóły pacjenta</div>
    <div class="row mb-5">
      <div class="col-6">
        <div class="mb-1 row">
          <label for="staticFirstName" class="col-4 col-form-label fw-bold">Imie</label>
          <div class="col-8">
            <input type="text" readonly class="form-control-plaintext" id="staticFirstName" :value="patient.firstName">
          </div>
        </div>
      </div>
      <div class="col-6">
        <div class="mb-1 row">
          <label for="staticLastName" class="col-4 col-form-label fw-bold">Nazwiko</label>
          <div class="col-8">
            <input type="text" readonly class="form-control-plaintext" id="staticLastName" :value="patient.lastName">
          </div>
        </div>
      </div>
      <div class="col-6">
        <div class="mb-1 row">
          <label for="staticPesel" class="col-4 col-form-label fw-bold">Pesel</label>
          <div class="col-8">
            <input type="text" readonly class="form-control-plaintext" id="staticPesel" :value="patient.pesel">
          </div>
        </div>
      </div>
      <div class="col-6">
        <div class="mb-1 row">
          <label for="staticEmail" class="col-4 col-form-label fw-bold">Email</label>
          <div class="col-8">
            <input type="text" readonly class="form-control-plaintext" id="staticEmail" :value="patient.email">
          </div>
        </div>
      </div>
    </div>
    <div class="h4 mb-3">Ankieta wstępna</div>
    <template v-if="ankietaWstepna !== null">
      <div class="row mb-5">
        <div class="col-6">
          <div class="mb-1 row">
            <label for="staticHeight" class="col-4 col-form-label fw-bold">Wzrost</label>
            <div class="col-8">
              <input type="text" readonly class="form-control-plaintext" id="staticHeight" :value="ankietaWstepna.wzrost">
            </div>
          </div>
        </div>
        <div class="col-6">
          <div class="mb-1 row">
            <label for="staticBloodType" class="col-4 col-form-label fw-bold">Grupa krwi</label>
            <div class="col-8">
              <input type="text" readonly class="form-control-plaintext" id="staticBloodType" :value="ankietaWstepna.grupaKrwi">
            </div>
          </div>
        </div>
        <div class="col-6">
          <div class="mb-1 row">
            <label for="staticTrybZycia" class="col-4 col-form-label fw-bold">Tryb życia</label>
            <div class="col-8">
              <input type="text" readonly class="form-control-plaintext" id="staticTrybZycia" :value="ankietaWstepna.trybZycia">
            </div>
          </div>
        </div>
        <div class="col-6">
          <div class="mb-1 row">
            <label for="staticPrzyjmowaneLeki" class="col-4 col-form-label fw-bold">Przyjmowane leki</label>
            <div class="col-8">
              <input type="text" readonly class="form-control-plaintext" id="staticPrzyjmowaneLeki" :value="ankietaWstepna.przyjmowaneLeki">
            </div>
          </div>
        </div>
        <div class="col-6">
          <div class="mb-1 row">
            <label for="staticAlergie" class="col-4 col-form-label fw-bold">Alergie</label>
            <div class="col-8">
              <input type="text" readonly class="form-control-plaintext" id="staticAlergie" :value="ankietaWstepna.alergie">
            </div>
          </div>
        </div>
      </div>
    </template>
    <template v-else>
      <div class="mb-5">
        Pacjent o id {{ patient.id }} nie wypełnił jeszcze ankiety.
      </div>
    </template>
    <div class="h4 mb-3">Ankiety cykliczne</div>
    <div class="mb-5">
      <AnkietaCyklicznaTable :data="ankietaCyklicznaData" />
    </div>
  </div>
</template>
