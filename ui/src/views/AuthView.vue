<script>
import LoginForm from "@/components/LoginForm.vue";
import RegisterForm from "@/components/RegisterForm.vue";
import DoctorView from "@/views/DoctorView.vue";
import PatientView from "@/views/PatientView.vue";
import {useSessionStore} from "@/stores/session";
import {computed} from "vue";

export default {
  components: {
    LoginForm,
    RegisterForm,
    DoctorView,
    PatientView
  },
  data: function() {
    return {
      form: "login",
      loginFormUserType: "doctor",
      registerFormUserType: "doctor"
    };
  },
  setup() {
    const sessionStore = useSessionStore();
    return {
      loggedUser: computed(() => sessionStore.loggedUser)
    }
  },
  methods: {
    isUserLogged: function () {
      return this.loggedUser !== null;
    },
    switchForm: function (form) {
      this.form = form;
      this.loginFormUserType = "doctor";
      this.registerFormUserType = "doctor";
    }
  }
}
</script>

<template>
  <template v-if="isUserLogged()">
    <template v-if="loggedUser.isDoctor()">
      <DoctorView/>
    </template>
    <template v-else>
      <PatientView/>
    </template>
  </template>
  <template v-else>
    <div class="d-flex justify-content-center mt-5">
      <div style="width: 550px">
        <template v-if="form === 'login'">
          <ul class="nav nav-tabs nav-fill">
            <li class="nav-item">
              <input type="button" class="nav-link active" aria-current="page" value="Logowanie"/>
            </li>
            <li class="nav-item">
              <input type="button" class="nav-link" aria-current="page" value="Rejestracja" @click="() => this.switchForm('register')"/>
            </li>
          </ul>
          <div class="border rounded-bottom p-3 border-top-0">
            <LoginForm :user-type="loginFormUserType"/>
          </div>
        </template>
        <template v-else>
          <ul class="nav nav-tabs nav-fill">
            <li class="nav-item">
              <input type="button" class="nav-link" aria-current="page" value="Logowanie" @click="() => this.switchForm('login')"/>
            </li>
            <li class="nav-item">
              <input type="button" class="nav-link active" aria-current="page" value="Rejestracja"/>
            </li>
          </ul>
          <div class="border rounded-bottom p-3 border-top-0">
            <RegisterForm :user-type="registerFormUserType"/>
          </div>
        </template>
      </div>
    </div>
  </template>
</template>
