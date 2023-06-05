<script setup lang="ts">
type GridProps = {
  columns: number,
  center?: boolean
}

withDefaults(defineProps<GridProps>(), {
  center: false
});
</script>

<template>
  <div class="container">
    <div class="grid" :class="{center: center}" >
      <slot />
    </div>
  </div>
</template>

<style lang="scss">
.grid {
    display: grid;
    grid-template-columns: repeat(v-bind(columns), auto);
    column-gap: 50px;
    row-gap: 15px
}

.container {
  width: fit-content;
}

.center {
  place-items: center;
}
</style>