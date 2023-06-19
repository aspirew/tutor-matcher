<script lang="ts" setup>
import { RouterLink } from 'vue-router'
import HeaderComp from './HeaderComp.vue'
import { Routes } from '@/router/routes'
import ContentComp from './ContentComp.vue'
</script>

<template>
  <HeaderComp>
    <template #left>TutorMatcher</template>
    <template #right>
      <RouterLink :to="Routes.HOME"> Home </RouterLink>
      <RouterLink :to="Routes.PROFILE"> Profile </RouterLink>
      <RouterLink :to="Routes.TUTOR_SEARCH"> Search </RouterLink>
    </template>
  </HeaderComp>
  <ContentComp>
    <slot />
  </ContentComp>
</template>
