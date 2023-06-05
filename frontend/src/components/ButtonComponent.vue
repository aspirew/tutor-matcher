<script setup lang="ts">
import { NButton } from 'naive-ui'
</script>

<template>
  <NButton class="button" primary color="#FFA500"><slot /></NButton>
</template>

<style>
.button {
  color: '#FFA500';
  width: fit-content;
}
</style>
