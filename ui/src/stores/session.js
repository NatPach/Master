import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import LoggedUser from "@/security/logged-user";

export const useSessionStore = defineStore('session', () => {
  const loggedUser = ref(null)
  // const doubleCount = computed(() => count.value * 2)
  // function increment() {
  //   count.value++
  // }
  function isUserLogged() {
    return loggedUser != null;
  }

  return { loggedUser, isUserLogged }
})
