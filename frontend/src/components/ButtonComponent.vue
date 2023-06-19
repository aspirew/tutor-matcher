<script setup lang="ts">
import { NButton } from 'naive-ui'

type ButtonProps = {
  wide?: boolean
}

withDefaults(defineProps<ButtonProps>(), {
  wide: false
})
</script>

<template>
  <NButton class="button" :class="{ wide: wide }" primary color="#FFA500"><slot /></NButton>
</template>

<style>
.button {
  color: '#FFA500';
  width: fit-content;
}

.wide {
  width: 100%;
}
</style>
