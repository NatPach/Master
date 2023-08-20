<script>
import {useSessionStore} from "@/stores/session";
import LoggedUser from "@/security/logged-user";
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
      loginAs: 'doctor',
      email: null,
      password: null
    };
  },
  methods: {
    login: function (_) {
      this.errorMessage = null;
      console.log(this.loginAs, this.email, this.password);
      const that = this;
      this.axios.post(`${config.serverUrl}/${this.loginAs}/login`, {email: this.email, password: this.password})
          .then(response => {
            console.log(response);
            that.sessionStore.loggedUser = new LoggedUser(
                this.loginAs,
                response.data.username,
                response.data.access_token);
          })
          .catch(error => {
            if (error.response.status === 401) {
              this.errorMessage = "Błędny email lub hasło.";
              return;
            }
            console.log("Błąd podczas logowania.", error)
          });
    }
  },
  setup: function () {
    const sessionStore = useSessionStore();
    return { sessionStore };
  }
}
</script>

<template>
  <div class="d-flex justify-content-center mt-5">
    <div class="border rounded p-3" style="width: 550px">
      <div class="mb-4">
        <span class="lead">Logowanie</span>
      </div>
      <div class="mb-3">
        <label for="inputLoginAs" class="form-label">Zaloguj jako</label>
        <div class="d-flex justify-content-around">
          <div class="form-check">
            <input class="form-check-input" type="radio" name="inputLoginAs" id="inputLoginAsDoctor" value="doctor" v-model="loginAs">
            <label class="form-check-label" for="inputLoginAsDoctor">
              Lekarz
            </label>
          </div>
          <div class="form-check">
            <input class="form-check-input" type="radio" name="inputLoginAs" id="inputLoginAsPatient" value="patient" v-model="loginAs">
            <label class="form-check-label" for="inputLoginAsPatient">
              Pacjent
            </label>
          </div>
        </div>
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
      <div class="mb-3">
        <button class="btn btn-primary float-end" @click="login">Login</button>
      </div>
    </div>
  </div>
</template>
