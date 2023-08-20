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
    return this.loggedUser != null;
  }

  function logout() {
    this.loggedUser = null;
  }

  function accessToken() {
    return this.loggedUser.accessToken;
  }

  return { loggedUser, isUserLogged, logout, accessToken }
})
