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
      waga: null,
      wzrost: null,
      grupaKrwi: null
    };
  },
  methods: {
    save: function () {
      console.log(this);
      const data = {
        waga: this.waga ? this.waga : null,
        wzrost: this.wzrost ? this.wzrost : null,
        grupaKrwi: this.grupaKrwi ? this.grupaKrwi : (this.grupaKrwi === 0 ? 0 : null)
      };
      this.axios.put(`${config.serverUrl}/ankieta-wstepna`, data, { headers: {"Authorization" : `Bearer ${this.sessionStore.accessToken()}`} });
    }
  },
  mounted() {
    this.axios.get(`${config.serverUrl}/ankieta-wstepna`, { headers: {"Authorization" : `Bearer ${this.sessionStore.accessToken()}`} })
        .then(response => {
          this.waga = response.data.waga;
          this.wzrost = response.data.wzrost;
          this.grupaKrwi = response.data.grupaKrwi;
        })
        .catch(error => {
          if (error.response.status === 404) {
            console.log(`Pacjent nie wypełnił ankiety wstepnej.`);
            return;
          }
          console.log("Błąd podczas pobierania pacjentów.", error)
        });
  }
}
</script>

<template>
  <div class="border rounded p-3">
    <div class="mb-3 row">
      <label for="inputWeight" class="col-sm-2 col-form-label">Waga</label>
      <div class="col-sm-10">
        <input type="number" class="form-control" id="inputWeight" v-model="waga">
      </div>
    </div>
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
          <option value="0">0</option>
          <option value="A">A</option>
          <option value="B">B</option>
          <option value="AB">AB</option>
        </select>
      </div>
    </div>
    <div class="text-end">
      <input class="btn btn-primary" type="button" value="Zapisz" @click="save">
    </div>
  </div>
</template>
