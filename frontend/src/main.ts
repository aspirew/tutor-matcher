import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import GoogleSignInPlugin from "vue3-google-signin"
import Vue3Toastify, { type ToastContainerOptions } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';
import VCalendar from 'v-calendar';
import 'v-calendar/style.css';

const gauthOption = {
    clientId: '943855035200-9hak1399kqev4q9fj92srmpikbrgtib2.apps.googleusercontent.com',
    redirectUri: 'http://localhost:8080/oauth/callback/google',
    scope: 'openid email'
  }

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(GoogleSignInPlugin, gauthOption);
app.use(Vue3Toastify, {
  autoClose: 2000,
} as ToastContainerOptions);
app.use(VCalendar, {})


app.mount('#app')
