<script>
import {useSessionStore} from "@/stores/session";
import config from "@/config";

export default {
  setup() {
    const sessionStore = useSessionStore();
    return {
      sessionStore
    };
  },
  data: function() {
    return {
      grupyKrwi: [],
      trybyZycia: [],
      wzrost: null,
      grupaKrwi: null,
      trybZycia: null,
      przyjmowaneLeki: null,
      alergie: null
    };
  },
  methods: {
    initializeBloodTypes: function () {
      return this.axios.get(`${config.serverUrl}/blood-types`, { headers: {"Authorization" : `Bearer ${this.sessionStore.accessToken()}`} })
          .then(response => {
            this.grupyKrwi = response.data;
          })
          .catch(error => {
            console.log("Błąd podczas pobierania listy grupy krwi.", error)
          });
    },
    initializeTrybyZycia: function () {
      return this.axios.get(`${config.serverUrl}/tryby-zycia`, { headers: {"Authorization" : `Bearer ${this.sessionStore.accessToken()}`} })
          .then(response => {
            this.trybyZycia = response.data;
          })
          .catch(error => {
            console.log("Błąd podczas pobierania listy trybow zycia.", error)
          });
    },
    initializeForm: function () {
      this.axios.get(`${config.serverUrl}/ankieta-wstepna`, { headers: {"Authorization" : `Bearer ${this.sessionStore.accessToken()}`} })
          .then(response => {
            this.wzrost = response.data.wzrost;
            this.grupaKrwi = response.data.grupaKrwi;
            this.trybZycia = response.data.trybZycia;
            this.przyjmowaneLeki = response.data.przyjmowaneLeki;
            this.alergie = response.data.alergie;
          })
          .catch(error => {
            if (error.response.status === 404) {
              console.log(`Pacjent nie wypełnił ankiety wstepnej.`);
              return;
            }
            console.log("Błąd podczas pobierania pacjentów.", error)
          });
    },
    save: function () {
      const data = {
        wzrost: this.wzrost ? this.wzrost : null,
        grupaKrwi: this.grupaKrwi ? this.grupaKrwi : (this.grupaKrwi === 0 ? 0 : null),
        trybZycia: this.trybZycia ? this.trybZycia : null,
        przyjmowaneLeki: this.przyjmowaneLeki ? this.przyjmowaneLeki : null,
        alergie: this.alergie ? this.alergie : null
      };
      this.axios.put(`${config.serverUrl}/ankieta-wstepna`, data, { headers: {"Authorization" : `Bearer ${this.sessionStore.accessToken()}`} });
    }
  },
  mounted() {
    Promise.all([this.initializeBloodTypes(), this.initializeTrybyZycia()])
        .then(values => this.initializeForm());
  }
}
</script>

<template>
  <div class="border rounded p-3">
    <div class="mb-3 row">
      <label for="inputHeight" class="col-sm-2 col-form-label">Wzrost</label>
      <div class="col-sm-10">
        <input type="number" class="form-control" id="inputHeight" v-model="wzrost">
      </div>
    </div>
    <div class="mb-3 row">
      <label for="inputBloodType" class="col-sm-2 col-form-label">Grupa krwi</label>
      <div class="col-sm-10">
        <select class="form-select" aria-label="Wybierz grupe krwi" v-model="grupaKrwi">
          <option value="">Open this select menu</option>
          <template v-for="item in grupyKrwi">
            <option :value="item">{{ item }}</option>
          </template>
        </select>
      </div>
    </div>
    <div class="mb-3 row">
      <label for="inputTrybZycia" class="col-sm-2 col-form-label">Tryb zycia</label>
      <div class="col-sm-10">
        <select class="form-select" aria-label="Wybierz tryb zycia" v-model="trybZycia">
          <option value="">Open this select menu</option>
          <template v-for="item1 in trybyZycia">
            <option :value="item1">{{ item1 }}</option>
          </template>
        </select>
      </div>
    </div>
    <div class="mb-3 row">
      <label for="inputMedicines" class="col-sm-2 col-form-label">Przyjmowane leki</label>
      <div class="col-sm-10">
        <input type="text" class="form-control" id="inputMedicines" v-model="przyjmowaneLeki">
      </div>
    </div>
    <div class="mb-3 row">
      <label for="inputAlergies" class="col-sm-2 col-form-label">Alergie</label>
      <div class="col-sm-10">
        <input type="text" class="form-control" id="inputAlergies" v-model="alergie">
      </div>
    </div>
    <div class="text-end">
      <input class="btn btn-primary" type="button" value="Zapisz" @click="save">
    </div>
  </div>
</template>
