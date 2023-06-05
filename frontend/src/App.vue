<script setup lang="ts">
import { NConfigProvider } from 'naive-ui'
import MainNav from './components/nav/MainNav.vue'

const theme = {
  common: {
    primaryColor: '#FFA500'
  },
  Input: {
    railColor: '#FFA500',
    railColorActive: '#FFA500'
  }
}
</script>

<template>
  <NConfigProvider :theme-overrides="theme">
    <MainNav>
      <RouterView />
    </MainNav>
  </NConfigProvider>
</template>

<style lang="scss">
@font-face {
  font-family: 'Ubuntu';
  src: local('Ubuntu'), url(./fonts/Ubuntu/Ubuntu-Regular.ttf) format('truetype');
}

html,
body,
#app {
  height: 100%;
  margin: 0;
  font-family: 'Ubuntu';
}

#app {
  display: flex;
  flex-direction: column;
}
</style>
