<script setup lang="ts">
import { ref } from 'vue'
import jwt_decode from 'jwt-decode'
import { useTokenStore } from '@/stores/tokenStore'

const encodedJwt = useTokenStore().getToken()

const name = ref('')
const mail = ref('')
const imgSrc = ref('')

if (encodedJwt) {
  name.value = (jwt_decode(encodedJwt) as any)['name']
  mail.value = (jwt_decode(encodedJwt) as any)['email']
  imgSrc.value = (jwt_decode(encodedJwt) as any)['picture']
}
</script>

<template>
  <h1>Witaj {{ name }}</h1>
  <h2>{{ mail }}</h2>
  <img :src="imgSrc" />
</template>
