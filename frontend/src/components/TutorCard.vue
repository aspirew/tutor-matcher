<script setup lang="ts">
import router from '@/router'
import { Routes } from '@/router/routes'

type CardProps = {
  userId: string
}

const props = defineProps<CardProps>()

const onClick = () => {
  router.push(`${Routes.PROFILE}/${props.userId}`)
}
</script>

<template>
  <div class="card" @click="onClick">
    <img class="image" src="/profile.png" />
    <h3><slot /></h3>
    <p>1 km od ciebie</p>
    <p>Dostępny od zaraz</p>
  </div>
</template>

<style lang="scss">
.image {
  width: 250px;
  height: 250px;
  border-radius: 10px 10px 0 0;
}
.card {
  width: fit-content;
  height: fit-content;
  border-radius: 10px;
  background-color: lightgray;
  display: flex;
  flex-direction: column;
  align-items: center;
  cursor: pointer;
}
</style>
