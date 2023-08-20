<script>
import {useSessionStore} from "@/stores/session";
import config from "@/config";

export default {
  data: function() {
    return {
      items: []
    };
  },
  setup() {
    const headers = [
      { text: "Id", value: "id" },
      { text: "Imię", value: "firstName", sortable: true },
      { text: "Nazwisko", value: "lastName", sortable: true },
      { text: "Email", value: "email", sortable: true }
    ];
    return {
      headers
    };
  },
  mounted() {
    const sessionStore = useSessionStore();
    this.axios.get(`${config.serverUrl}/patients`, { headers: {"Authorization" : `Bearer ${sessionStore.accessToken()}`} })
        .then(response => {
          this.items = response.data;
        })
        .catch(error => {
          console.log("Błąd podczas pobierania pacjentów.", error)
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
