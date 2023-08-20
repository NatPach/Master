<script>
import LoginForm from "@/components/LoginForm.vue";
import DoctorView from "@/views/DoctorView.vue";
import PatientView from "@/views/PatientView.vue";
import {useSessionStore} from "@/stores/session";
import {computed} from "vue";

export default {
  components: {
    LoginForm,
    DoctorView,
    PatientView
  },
  data: function() {
    return {
      loginFormUserType: "doctor"
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
    <LoginForm :user-type="loginFormUserType"/>
  </template>
</template>
