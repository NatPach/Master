<script>

export default {
  props: {
    columns: {
      type: Object,
      required: true
    },
    data: {
      type: Object,
      required: true
    }
  },
  data: function () {
    return {
      cellHovered: null,
      defaultMapper: (value, row, key) => value
    }
  },
  methods: {
    extractCellValue: function (column, row) {
      const finalMapper = column.mapper || this.defaultMapper;
      return finalMapper(row[column.key], row, column.key);
    },
    shouldCellBeHighlighted: function (rowIndex, colIndex) {
      return this.cellHovered !== null && (this.cellHovered.rowIndex === rowIndex);
    },
    onCellEnter: function (rowIndex, colIndex) {
      this.cellHovered = {
        rowIndex: rowIndex,
        colIndex: colIndex
      };
    },
    onCellLeave: function () {
      this.cellHovered = null;
    },
  }
}
</script>

<style>
.table th {
  background: #ffcccc !important;
}

.cell-hovered {
  background: linear-gradient(#ffdcdb, white 25%, white 75%, #ffdcdb) !important;
}
</style>

<template>
  <div class="">
    <table class="table mb-0">
      <thead>
        <tr>
          <template v-for="column in columns">
            <th scope="col" class="py-3 px-4">
              {{ column.name }}
            </th>
          </template>
        </tr>
      </thead>
      <tbody>
        <template v-for="(row, rowIndex) in data">
          <tr>
            <template v-for="(column, colIndex) in columns">
              <td v-bind:class="((shouldCellBeHighlighted(rowIndex, colIndex)) ? 'cell-hovered' : '') + ' py-3 px-4'" v-on:mouseover="onCellEnter(rowIndex, colIndex)" v-on:mouseleave="onCellLeave()">
                {{ extractCellValue(column, row) }}
              </td>
            </template>
          </tr>
        </template>
      </tbody>
    </table>
  </div>
</template>