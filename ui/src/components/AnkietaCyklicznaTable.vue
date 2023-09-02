<script>
import Table from "@/components/Table.vue";

export default {
  components: {
    Table,
  },
  props: {
    data: {
      type: Array,
      required: true,
    },
  },
  data: function () {
    return {
      columns: [
        {
          name: 'Data pomiaru',
          key: 'createdAt',
          mapper: (value, row, key) => {
            if (!value) {
              return null;
            }
            return new Date(value * 1000).toLocaleString()
          }
        },
        {
          name: 'Tętno',
          key: 'tetno'
        },
        {
          name: 'Samopoczucie',
          key: 'samopoczucie'
        },
        {
          name: "Waga",
          key: "waga"
        },
        {
          name: "Potrzeba wizyty",
          key: "potrzebaWizyty",
          mapper: (value, row, key) => value ? 'Tak' : 'Nie'
        },
        {
          name: "Inne uwagi zdrowotne",
          key: "inneUwagiZdrowotne"
        },
      ],
    };
  }
}
</script>

<template>
  <Table :data="data" :columns="columns"/>
</template>