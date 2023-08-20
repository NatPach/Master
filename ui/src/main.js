// import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import axios from 'axios'
import VueAxios from 'vue-axios'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap"
import Vue3EasyDataTable from 'vue3-easy-data-table';
import 'vue3-easy-data-table/dist/style.css';

import App from './App.vue'

const app = createApp(App)

axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
axios.defaults.headers.common['Content-type'] = 'application/json';
axios.defaults.headers.common['Referrer-Policy'] = 'no-referer';

app.use(createPinia())
app.use(VueAxios, axios)
app.component('EasyDataTable', Vue3EasyDataTable);

app.mount('#app')
