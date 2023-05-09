import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import GoogleSignInPlugin from "vue3-google-signin"

const gauthOption = {
    clientId: '943855035200-9hak1399kqev4q9fj92srmpikbrgtib2.apps.googleusercontent.com',
    redirectUri: 'http://localhost:8080/oauth/callback/google',
    scope: 'openid email'
  }

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(GoogleSignInPlugin, gauthOption);

app.mount('#app')
