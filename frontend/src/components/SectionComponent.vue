<script setup lang="ts">
type SectionProps = {
  center?: boolean
}
defineProps<SectionProps>();
</script>

<template>
  <div class="section" :class="{center: center}" >
    <slot />
  </div>
</template>

<style lang="scss">

.section {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    flex-direction: column;
    padding: 20px;
}
</style>