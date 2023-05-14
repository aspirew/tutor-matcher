<script setup lang="ts">
import { useTokenStore } from '@/stores/tokenStore'
import { GoogleSignInButton, type CredentialResponse } from 'vue3-google-signin'
import { toast } from 'vue3-toastify'
import jwt_decode from 'jwt-decode'

const { setToken } = useTokenStore()

// handle success event
const handleLoginSuccess = (response: CredentialResponse) => {
  const { credential } = response
  setToken(credential)
  const name = (jwt_decode(credential!!) as any)['email']
  console.log(name)
}

// handle an error event
const handleLoginError = () => {
  toast('Failed to log in')
}
</script>

<template>
  <GoogleSignInButton @success="handleLoginSuccess" @error="handleLoginError"></GoogleSignInButton>
</template>
