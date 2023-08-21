<script>
import config from "@/config";

export default {
  props: {
    userType: {
      type: String,
      required: true
    },
  },
  data: function () {
    return {
      errorMessage: null,
      successMessage: null,
      registerAs: 'doctor',
      firstName: null,
      lastName: null,
      email: null,
      password: null,
    };
  },
  methods: {
    register: function (_) {
      this.errorMessage = null;
      this.successMessage = null;
      const data = {
        firstName: this.firstName,
        lastName: this.lastName,
        email: this.email,
        password: this.password
      };
      this.axios.post(`${config.serverUrl}/${this.registerAs}/register`, data)
          .then(_ => {
            this.successMessage = "Poprawnie zarejestrowano użytkownika."
          })
          .catch(error => {
            console.log("Błąd podczas rejestracji.", error)
          });
    }
  }
}
</script>

<template>
  <div class="mb-3">
    <label for="inputLoginAs" class="form-label">Zarejestruj jako</label>
    <div class="d-flex justify-content-around">
      <div class="form-check">
        <input class="form-check-input" type="radio" name="inputLoginAs" id="inputLoginAsDoctor" value="doctor" v-model="registerAs">
        <label class="form-check-label" for="inputLoginAsDoctor">
          Lekarz
        </label>
      </div>
      <div class="form-check">
        <input class="form-check-input" type="radio" name="inputLoginAs" id="inputLoginAsPatient" value="patient" v-model="registerAs">
        <label class="form-check-label" for="inputLoginAsPatient">
          Pacjent
        </label>
      </div>
    </div>
  </div>
  <div class="mb-3">
    <label for="inputFirstName" class="form-label">Imię</label>
    <input type="text" class="form-control" id="inputFirstName" v-model="firstName">
  </div>
  <div class="mb-3">
    <label for="inputLastName" class="form-label">Nazwisko</label>
    <input type="text" class="form-control" id="inputLastName" v-model="lastName">
  </div>
  <div class="mb-3">
    <label for="inputEmail" class="form-label">Email</label>
    <input type="email" class="form-control" id="inputEmail" v-model="email">
  </div>
  <div class="mb-4">
    <label for="inputPassword" class="form-label">Hasło</label>
    <input type="password" class="form-control" id="inputPassword" v-model="password">
  </div>
  <template v-if="errorMessage !== null">
    <div class="mb-3 text-danger small text-center">
      <span>{{ errorMessage }}</span>
    </div>
  </template>
  <template v-if="successMessage !== null">
    <div class="mb-3 text-success small text-center">
      <span>{{ successMessage }}</span>
    </div>
  </template>
  <div class="mb-3">
    <button class="btn btn-primary text-end" @click="register">Zarejestruj</button>
  </div>
</template>
