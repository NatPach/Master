<script>
import {useSessionStore} from "@/stores/session";
import PatientList from "@/components/PatientList.vue";

export default {
  components: {
    PatientList
  },
  setup() {
    const sessionStore = useSessionStore();
    return {
      sessionStore: sessionStore
    }
  },
  methods: {
    logout: function () {
      this.sessionStore.logout();
    }
  }
}
</script>

<template>
  <ul class="nav p-3">
    <li class="nav-item">
      <a class="nav-link active" href="#">Pacjenci</a>
    </li>
<!--    <li class="nav-item">-->
<!--      <a class="nav-link" href="#">Link</a>-->
<!--    </li>-->
    <li class="nav-item">
      <a class="nav-link" href="#" @click="logout">Wyloguj</a>
    </li>
  </ul>

  <PatientList/>
</template>
