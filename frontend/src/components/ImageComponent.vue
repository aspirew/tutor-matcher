<script setup lang="ts">
type GridProps = {
  src: string
}

defineProps<GridProps>()
</script>

<template>
  <img :src="src" class="image" />
</template>

<style>
.image {
  max-width: 200px;
  max-height: 200px;
}
</style>
