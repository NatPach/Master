<script>
import { Line } from 'vue-chartjs'
import 'chartjs-adapter-date-fns';
import { pl } from 'date-fns/locale';
import annotationPlugin from 'chartjs-plugin-annotation';
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale, PointElement, LineElement, TimeScale } from 'chart.js'

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale, PointElement, LineElement, TimeScale, annotationPlugin);

export default {
  components: {
    Line
  },
  props: {
    labels: {
      type: Array,
      required: true,
    },
    data: {
      type: Array,
      required: true
    },
    minX: {
      type: Number,
      required: false,
    },
    maxX: {
      type: Number,
      required: false,
    },
    minYBoundary: {
      type: Number,
      required: false,
    },
    maxYBoundary: {
      type: Number,
      required: false,
    },
    title: {
      type: String,
      required: false
    }
  },
  computed: {
    chartData() {
      return {
        labels: this.labels,
        datasets: [
          {
            data: this.data,
            borderColor: '#FF6384',
            backgroundColor: '#FFB1C1',
          }
        ]
      };
    }
  },
  data() {
    const boundariesColor = 'blue';
    const annotation1 = {
      type: 'line',
      borderColor: boundariesColor,
      borderWidth: 3,
      label: {
        enabled: true,
        backgroundColor: boundariesColor,
        borderColor: boundariesColor,
        borderRadius: 10,
        borderWidth: 2,
        // content: (ctx) => 'Lower bound: ' + this.minYBoundary,
        rotation: 'auto'
      },
      scaleID: 'y',
      value: this.minYBoundary
    };
    const annotation2 = {
      type: 'line',
      borderColor: boundariesColor,
      borderWidth: 3,
      label: {
        enabled: true,
        backgroundColor: boundariesColor,
        borderColor: boundariesColor,
        borderRadius: 10,
        borderWidth: 2,
        // content: (ctx) => 'Upper bound: ' + this.maxYBoundary,
        rotation: 'auto'
      },
      scaleID: 'y',
      value: this.maxYBoundary
    };
    return {
      chartOptions: {
        plugins: {
          title: {
            display: !!this.title,
            text: this.title
          },
          legend: {
            display: false
          },
          annotation: {
            clip: false,
            annotations: {
              annotation1,
              annotation2
            }
          },
        },
        responsive: true,
        scales: {
          x: {
            type: 'time',
            adapters: {
              date: {
                locale: pl
              }
            },
            time: {
              unit: 'day',
              displayFormats: {
                day: 'MM-dd',
              }
            },
            suggestedMin: this.minX ? this.minX : null,
            suggestedMax: this.maxX ? this.maxX : null
          }
        },
      }
    };
  },
}
</script>

<template>
  <Line
      :options="chartOptions"
      :data="chartData"
  />
</template>