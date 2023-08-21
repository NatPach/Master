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
  data: function () {
    const headers = [
      { text: "Tętno", value: "tetno" },
      { text: "Samopoczucie", value: "samopoczucie" },
      { text: "Data pomiaru", value: "createdAt", sortable: true }
    ];
    return {
      headers: headers,
      items: [],
      tetno: null,
      samopoczucie: null
    };
  },
  methods: {
    save: function () {
      const data = {
        tetno: this.tetno ? this.tetno : null,
        samopoczucie: this.samopoczucie ? this.samopoczucie : null
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
    },
    refreshList: function () {
      this.axios.get(`${config.serverUrl}/ankieta-cykliczna`, { headers: {"Authorization" : `Bearer ${this.sessionStore.accessToken()}`} })
          .then(response => {
            this.items = response.data.map(value => {
              value.samopoczucie = value.samopoczucie.toLowerCase().replace('_', ' ');
              value.createdAt = new Date(value.createdAt).toLocaleString();
              return value;
            });
          })
          .catch(error => {
            console.log("Błąd podczas pobierania ankiet cyklicznych.", error)
          });
    }
  },
  mounted() {
    this.refreshList();
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
          <option value="LEPSZE">lepsze</option>
          <option value="BEZ_ZMIAN">bez zmian</option>
          <option value="GORSZE">gorsze</option>
        </select>
      </div>
    </div>
    <div class="text-end">
      <input class="btn btn-primary" type="button" value="Dodaj" @click="save">
    </div>
  </div>
  <EasyDataTable
      :headers="headers"
      :items="items"
  />
</template>
