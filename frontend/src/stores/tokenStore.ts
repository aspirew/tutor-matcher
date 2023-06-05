import { useLocalStorage } from '@vueuse/core';
import { defineStore } from 'pinia';

export const useTokenStore = defineStore('token', () => {
  const currentToken = useLocalStorage('token', "")
  function getToken() {
    return currentToken.value
  }
  function setToken(token?: string) {
    console.log("setting token...")
    currentToken.value = token
  }
  function clearToken() {
    currentToken.value = "";
  }
  return { getToken, setToken, clearToken };
});
