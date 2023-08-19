// import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import axios from 'axios'
import VueAxios from 'vue-axios'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap"

import App from './App.vue'

const app = createApp(App)

axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
axios.defaults.headers.common['Content-type'] = 'application/json';
// axios.defaults.headers.common['Referer'] = 'http://localhost:5173';
axios.defaults.headers.common['Referrer-Policy'] = 'no-referer';

app.use(createPinia())
app.use(VueAxios, axios)

app.mount('#app')
