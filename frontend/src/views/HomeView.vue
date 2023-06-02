<script setup lang="ts">
import LoginComponent from '@/components/LoginComponent.vue'
import { useTokenStore } from '@/stores/tokenStore'
const { getToken, clearToken } = useTokenStore()
</script>

<template>
  <p v-if="getToken()">You are logged in!</p>
  <LoginComponent></LoginComponent>
  <button @click="clearToken">Log out</button>
</template>
