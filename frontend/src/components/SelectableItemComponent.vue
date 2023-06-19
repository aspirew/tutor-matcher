<script setup lang="ts">
type ItemProps = {
  modelValue: boolean
}

const props = defineProps<ItemProps>()
const emit = defineEmits(['update:modelValue'])

const onClick = () => {
  emit('update:modelValue', !props.modelValue)
}
</script>

<template>
  <div class="item" :class="{ selected: modelValue }" @click="onClick">
    <slot />
  </div>
</template>

<style lang="scss">
.item {
  background-color: #ffa500;
  color: white;
  border-radius: 20px;
  padding: 10px;
  border: solid 1px #ff8400;
  cursor: pointer;
  width: fit-content;
}

.selected {
  border: solid 3px #ff8400;
  padding: 8px;
}
</style>
