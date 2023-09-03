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
    return {
      firstName: null,
      middleName: null,
      lastName: null,
      pesel: null,
      phone: null,
      email: null,
    };
  },
  methods: {
    save: function () {
      const data = {
        firstName: this.firstName ? this.firstName : null,
        middleName: this.middleName ? this.middleName : null,
        lastName: this.lastName ? this.lastName : null,
        pesel: this.pesel ? this.pesel : null,
        phone: this.phone ? this.phone : null,
        email: this.email ? this.email : null,
      };
      this.axios.put(`${config.serverUrl}/me`, data, { headers: {"Authorization" : `Bearer ${this.sessionStore.accessToken()}`} });
    }
  },
  mounted() {
    this.axios.get(`${config.serverUrl}/me`, { headers: {"Authorization" : `Bearer ${this.sessionStore.accessToken()}`} })
        .then(response => {
          this.firstName = response.data.firstName;
          this.middleName = response.data.middleName;
          this.lastName = response.data.lastName;
          this.pesel = response.data.pesel;
          this.phone = response.data.phone;
          this.email = response.data.email;
        })
        .catch(error => {
          console.log("Błąd podczas pobierania pacjentów.", error)
        });
  }
}
</script>

<template>
  <div class="border rounded p-3">
    <div class="h4 mb-3 text-center">Szczegóły pacjenta</div>
    <div class="mb-3 row">
      <label for="inputFirstName" class="col-2 col-form-label">Imię</label>
      <div class="col-10">
        <input type="text" class="form-control" id="inputFirstName" v-model="firstName">
      </div>
    </div>
    <div class="mb-3 row">
      <label for="inputMiddleName" class="col-2 col-form-label">Drugie imię</label>
      <div class="col-10">
        <input type="text" class="form-control" id="inputMiddleName" v-model="middleName">
      </div>
    </div>
    <div class="mb-3 row">
      <label for="inputLastName" class="col-2 col-form-label">Nazwisko</label>
      <div class="col-10">
        <input type="text" class="form-control" id="inputLastName" v-model="lastName">
      </div>
    </div>
    <div class="mb-3 row">
      <label for="inputPesel" class="col-2 col-form-label">Pesel</label>
      <div class="col-10">
        <input type="text" class="form-control" id="inputPesel" v-model="pesel">
      </div>
    </div>
    <div class="mb-3 row">
      <label for="inputPhone" class="col-2 col-form-label">Numer telefonu</label>
      <div class="col-10">
        <input type="tel" class="form-control" id="inputPhone" v-model="phone">
      </div>
    </div>
    <div class="mb-3 row">
      <label for="inputEmail" class="col-2 col-form-label">Email</label>
      <div class="col-10">
        <input type="email" class="form-control" id="inputEmail" v-model="email">
      </div>
    </div>
    <div class="text-end">
      <input class="btn btn-primary" type="button" value="Zapisz" @click="save">
    </div>
  </div>
</template>
