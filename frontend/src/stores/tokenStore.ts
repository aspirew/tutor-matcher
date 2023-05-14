import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useTokenStore = defineStore('token', () => {
  const currentToken = ref<string | undefined>(undefined);
  function getToken() {
    return currentToken.value
  }
  function setToken(token?: string) {
    console.log("setting token...")
    currentToken.value = token;
  }
  function clearToken() {
    currentToken.value = undefined;
  }
  return { getToken, setToken, clearToken };
});
