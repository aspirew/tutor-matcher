<script setup lang="ts">
type GridProps = {
  columns?: number | 'auto-fill'
  minWidth?: string
  maxWidth?: string
  center?: boolean
}

withDefaults(defineProps<GridProps>(), {
  columns: 'auto-fill',
  minWidth: '20px',
  maxWidth: '1fr',
  center: false
})
</script>

<template>
  <div class="container">
    <div class="grid" :class="{ center: center }">
      <slot />
    </div>
  </div>
</template>

<style lang="scss">
.grid {
  display: grid;
  grid-template-columns: repeat(v-bind(columns), minmax(v-bind(minWidth), v-bind(maxWidth)));
  column-gap: 50px;
  row-gap: 15px;
}

.container {
  width: 100%;
}

.center {
  place-items: center;
}
</style>
