<script>
import LoginForm from "@/components/LoginForm.vue";
import {useSessionStore} from "@/stores/session";
import {computed} from "vue";

export default {
  components: {
    LoginForm
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
      zalogowany jako lekarz
    </template>
    <template v-else>
      zalogowany jako pacjent
    </template>
  </template>
  <template v-else>
    <LoginForm :user-type="loginFormUserType"/>
  </template>
</template>
