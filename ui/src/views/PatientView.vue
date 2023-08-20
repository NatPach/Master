<script>
import {useSessionStore} from "@/stores/session";
import AnkietaWstepna from "@/components/patient/AnkietaWstepna.vue";

export default {
  components: {
    AnkietaWstepna
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
      <a class="nav-link active" href="#">Ankieta wstępna</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="#" @click="logout">Wyloguj</a>
    </li>
  </ul>

  <AnkietaWstepna/>
</template>
