<script>
import TimelineChart from "@/components/TimelineChart.vue";

const WEEK_IN_SECONDS = 7 * 24 * 60 * 60;

export default {
  components: {
    TimelineChart,
  },
  props: {
    ankietaCyklicznaData: {
      type: Array,
      required: true,
    }
  },
  data: function () {
    return {
      currentTimestampInSec: Math.floor(Date.now() / 1000),
    }
  },
  computed: {
    tetnoChartData() {
      return this.ankietaCyklicznaData.filter(item => {
        return item.createdAt >= (this.currentTimestampInSec - WEEK_IN_SECONDS);
      }).map(item => {
        return item.tetno;
      }).reverse();
    },
    tetnoChartLabels() {
      return this.ankietaCyklicznaData.filter(item => {
        return item.createdAt >= (this.currentTimestampInSec - WEEK_IN_SECONDS);
      }).map(item => {
        return item.createdAt * 1000;
      }).reverse();
    },
    tetnoChartMinX() {
      return (this.currentTimestampInSec - WEEK_IN_SECONDS) * 1000;
    },
    tetnoChartMaxX() {
      return this.currentTimestampInSec * 1000;
    },
    tetnoSummary() {
      if (!this.ankietaCyklicznaData) {
        return 'Wypełniono za mało danych, aby wygenerować podsumowanie.'
      }
      const tetnoData = this.tetnoChartData;
      if (Math.min(...tetnoData) < 60 || Math.max(...tetnoData) > 100) {
        return 'W przeciągu ostatniego tygodnia wystąpiły anomalie w tętnie.'
      } else {
        return 'Tętno prawidłowe.';
      }
    }
  },
}
</script>

<template>
  <TimelineChart :labels="tetnoChartLabels" :data="tetnoChartData" :min-x="tetnoChartMinX" :max-x="tetnoChartMaxX" :min-y-boundary="60" :max-y-boundary="100" title="Tętno"/>
  <div class="text-center mt-1">
    {{ tetnoSummary }}
  </div>
</template>
