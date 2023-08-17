<script setup>
import HelloWorld from './components/HelloWorld.vue'
import { useSessionStore } from '@/stores/session'

const sessionStore = useSessionStore()
</script>

<template>
  <template v-if="sessionStore.loggedUser != null">
    zalogowany
  </template>
  <template v-else>
    nie zalogowany
  </template>
</template>
