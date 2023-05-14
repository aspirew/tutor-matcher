<script setup lang="ts">
//@ts-nocheck
import { Calendar } from 'v-calendar'

import { ref } from 'vue'

const attributes = ref([
  {
    // Boolean
    dot: true,
    popover: {
      label: 'A lesson'
    },
    dates: [new Date(2018, 0, 1), new Date(2018, 0, 10), new Date(2018, 0, 22)]
  },
  {
    // String
    dot: 'red',
    popover: {
      label: 'A different lesson'
    },
    dates: [new Date(2018, 0, 4), new Date(2018, 0, 10), new Date(2018, 0, 15)]
  },
  {
    // Object
    content: {
      color: 'purple',
      style: {
        fontStyle: 'italic'
      }
    },
    popover: {
      label: 'Day free'
    },
    dates: { repeat: { weekdays: [1, 7] } }
  }
])
</script>

<template>
  <Calendar :initial-page="{ month: 1, year: 2018 }" :attributes="attributes"></Calendar>
</template>
