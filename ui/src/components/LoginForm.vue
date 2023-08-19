<script>
import {useSessionStore} from "@/stores/session";
import LoggedUser from "@/security/logged-user";

export default {
  props: {
    userType: {
      type: String,
      required: true
    },
  },
  data: function () {
    return {
      email: null,
      password: null
    };
  },
  methods: {
    login: function (_) {
      console.log(this.email, this.password);
      const that = this;
      this.axios.post("http://localhost:8080/doctor/login", {email: this.email, password: this.password})
          .then(response => {
            console.log(response);
            that.sessionStore.loggedUser = new LoggedUser(
                'doctor',
                response.data.username,
                response.data.access_token);
          })
          .catch(error => console.log("Błąd podczas logowania.", error));
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
    <div class="border rounded p-3" style="width: 450px">
      <div class="mb-3">
        <span>Logowanie <span class="small">({{ this.userType === 'doctor' ? "lekarz" : "pacjent" }})</span></span>
      </div>
      <div class="mb-3">
        <label for="inputEmail" class="form-label">Email</label>
        <input type="email" class="form-control" id="inputEmail" v-model="email">
      </div>
      <div class="mb-3">
        <label for="inputPassword" class="form-label">Hasło</label>
        <input type="password" class="form-control" id="inputPassword" v-model="password">
      </div>
      <div class="mb-3">
        <button class="btn btn-primary float-end" @click="login">Login</button>
      </div>
    </div>
  </div>
</template>
