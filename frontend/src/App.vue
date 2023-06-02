<script setup lang="ts">
import MainNav from './components/nav/MainNav.vue'
</script>

<template>
  <MainNav>
    <RouterView />
  </MainNav>
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
