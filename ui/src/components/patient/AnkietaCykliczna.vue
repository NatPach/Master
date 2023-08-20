<script>

import {useSessionStore} from "@/stores/session";
import config from "@/config";

export default {
  data: function () {
    const headers = [
      { text: "Tętno", value: "tetno" },
      { text: "Samopoczucie", value: "samopoczucie" },
      { text: "Data pomiaru", value: "createdAt", sortable: true }
    ];
    return {
      headers: headers,
      items: []
    };
  },
  mounted() {
    const sessionStore = useSessionStore();
    this.axios.get(`${config.serverUrl}/ankieta-cykliczna`, { headers: {"Authorization" : `Bearer ${sessionStore.accessToken()}`} })
        .then(response => {
          this.items = response.data.map(value => {
            value.samopoczucie = value.samopoczucie.toLowerCase().replace('_', ' ');
            value.createdAt = new Date(value.createdAt).toLocaleString();
            return value;
          });
        })
        .catch(error => {
          console.log("Błąd podczas pobierania ankiet cyklicznych.", error)
        });
  }
}
</script>

<template>
  <EasyDataTable
      :headers="headers"
      :items="items"
  />
</template>
