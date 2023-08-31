<script>

import {useSessionStore} from "@/stores/session";
import config from "@/config";
import Table from "@/components/Table.vue";

export default {
  components: {
    Table,
  },
  setup() {
    const sessionStore = useSessionStore();
    return {
      sessionStore
    };
  },
  data: function () {
    return {
      samopoczucia: [],

      tetno: null,
      samopoczucie: null,
      waga: null,
      potrzebaWizyty: false,
      inneUwagiZdrowotne: null,

      columns: [
        {
          name: 'Data pomiaru',
          key: 'createdAt',
          mapper: (value, row, key) => {
            if (!value) {
              return null;
            }
            return new Date(value * 1000).toLocaleString()
          }
        },
        {
          name: 'Tętno',
          key: 'tetno'
        },
        {
          name: 'Samopoczucie',
          key: 'samopoczucie'
        },
        {
          name: "Waga",
          key: "waga"
        },
        {
          name: "Potrzeba wizyty",
          key: "potrzebaWizyty",
          mapper: (value, row, key) => value ? 'Tak' : 'Nie'
        },
        {
          name: "Inne uwagi zdrowotne",
          key: "inneUwagiZdrowotne"
        },
      ],
      data: []
    };
  },
  methods: {
    save: function () {
      const data = {
        tetno: this.tetno ? this.tetno : null,
        samopoczucie: this.samopoczucie ? this.samopoczucie : null,
        waga: this.waga ? this.waga : null,
        potrzebaWizyty: this.potrzebaWizyty ? this.potrzebaWizyty : false,
        inneUwagiZdrowotne: this.inneUwagiZdrowotne ? this.inneUwagiZdrowotne : null
      };
      this.axios.post(`${config.serverUrl}/ankieta-cykliczna`, data, { headers: {"Authorization" : `Bearer ${this.sessionStore.accessToken()}`} })
          .finally(() => {
            this.clearForm();
            this.refreshList();
          });
    },
    clearForm: function () {
      this.tetno = null;
      this.samopoczucie = null;
      this.waga = null;
      this.potrzebaWizyty = false;
      this.inneUwagiZdrowotne = null;
    },
    initializeSamopoczucia: function () {
      return this.axios.get(`${config.serverUrl}/samopoczucia`, { headers: {"Authorization" : `Bearer ${this.sessionStore.accessToken()}`} })
          .then(response => {
            this.samopoczucia = response.data;
          })
          .catch(error => {
            console.log("Błąd podczas pobierania listy samopoczucia.", error)
          });
    },
    refreshList: function () {
      this.axios.get(`${config.serverUrl}/ankieta-cykliczna`, { headers: {"Authorization" : `Bearer ${this.sessionStore.accessToken()}`} })
          .then(response => {
            this.data = response.data;
          })
          .catch(error => {
            console.log("Błąd podczas pobierania ankiet cyklicznych.", error)
          });
    }
  },
  mounted() {
    Promise.all([this.initializeSamopoczucia()])
        .then(() => this.refreshList());
  }
}
</script>

<template>
  <div class="border rounded p-3 mb-4">
    <div class="mb-3 row">
      <label for="inputTetno" class="col-sm-2 col-form-label">Tętno</label>
      <div class="col-sm-10">
        <input type="number" class="form-control" id="inputTetno" v-model="tetno">
      </div>
    </div>
    <div class="mb-3 row">
      <label for="inputSamopoczucie" class="col-sm-2 col-form-label">Samopoczucie</label>
      <div class="col-sm-10">
        <select class="form-select" aria-label="Wybierz samopoczucie" v-model="samopoczucie">
          <option value="">Open this select menu</option>
          <template v-for="item in samopoczucia">
            <option :value="item">{{ item }}</option>
          </template>
        </select>
      </div>
    </div>
    <div class="mb-3 row">
      <label for="inputWaga" class="col-sm-2 col-form-label">Waga</label>
      <div class="col-sm-10">
        <input type="number" class="form-control" id="inputWaga" v-model="waga">
      </div>
    </div>
    <div class="mb-3 row">
      <label for="inputPotrzebaWizyty" class="col-sm-2 col-form-label">Potrzeba wizyty</label>
      <div class="col-sm-10">
        <input class="form-check-input" type="checkbox" v-model="potrzebaWizyty" id="inputPotrzebaWizyty">
      </div>
    </div>
    <div class="mb-3 row">
      <label for="inputInneUwagiZdrowotne" class="col-sm-2 col-form-label">Inne uwagi zdrowotne</label>
      <div class="col-sm-10">
        <input type="text" class="form-control" id="inputInneUwagiZdrowotne" v-model="inneUwagiZdrowotne">
      </div>
    </div>
    <div class="text-end">
      <input class="btn btn-primary" type="button" value="Dodaj" @click="save">
    </div>
  </div>

  <div class="mt-5">
    <Table :data="data" :columns="columns"/>
  </div>
</template>
