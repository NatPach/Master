<script>
import {useSessionStore} from "@/stores/session";
import AnkietaWstepna from "@/components/patient/AnkietaWstepna.vue";
import AnkietaCykliczna from "@/components/patient/AnkietaCykliczna.vue";

export default {
  components: {
    AnkietaWstepna,
    AnkietaCykliczna
  },
  data: function () {
    return {
      tabIndex: 0
    };
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
    <li class="nav-item{{ tabIndex === 0 ? ' active' : '' }}">
      <input type="button" class="nav-link" value="Ankieta wstępna" @click="() => tabIndex = 0"/>
    </li>
    <li class="nav-item{{ tabIndex === 1 ? ' active' : '' }}">
      <input type="button" class="nav-link" value="Ankieta cykliczna" @click="() => tabIndex = 1"/>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="#" @click="logout">Wyloguj</a>
    </li>
  </ul>

  <template v-if="tabIndex === 0">
    <AnkietaWstepna/>
  </template>
  <template v-else-if="tabIndex === 1">
    <AnkietaCykliczna />
  </template>
</template>
