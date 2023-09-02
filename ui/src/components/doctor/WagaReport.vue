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
    wagaChartData() {
      return this.ankietaCyklicznaData.filter(item => {
        return item.createdAt >= (this.currentTimestampInSec - WEEK_IN_SECONDS);
      }).map(item => {
        return item.waga;
      }).reverse();
    },
    wagaChartLabels() {
      return this.ankietaCyklicznaData.filter(item => {
        return item.createdAt >= (this.currentTimestampInSec - WEEK_IN_SECONDS);
      }).map(item => {
        return item.createdAt * 1000;
      }).reverse();
    },
    wagaChartMinX() {
      return (this.currentTimestampInSec - WEEK_IN_SECONDS) * 1000;
    },
    wagaChartMaxX() {
      return this.currentTimestampInSec * 1000;
    },
    wagaSummary() {
      if (!this.ankietaCyklicznaData) {
        return 'Wypełniono za mało danych, aby wygenerować podsumowanie.'
      }
      const chartData = this.wagaChartData;
      const roznicaWagi = chartData[0] - chartData[chartData.length - 1];
      if (roznicaWagi >= 5) {
        return `W przeciągu ostatniego tygodnia waga spadła o ${roznicaWagi.toFixed(1)}kg`;
      } else if (roznicaWagi <= -5) {
        return `W przeciągu ostatniego tygodnia waga wzrosła o ${-1 * roznicaWagi.toFixed(1)}kg`;
      } else {
        return "Waga prawidłowa.";
      }
    }
  },
}
</script>

<template>
  <TimelineChart :labels="wagaChartLabels" :data="wagaChartData" :min-x="wagaChartMinX" :max-x="wagaChartMaxX" title="Waga"/>
  <div class="text-center mt-1">
    {{ wagaSummary }}
  </div>
</template>
