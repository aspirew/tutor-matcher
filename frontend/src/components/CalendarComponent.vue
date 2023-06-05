<script setup lang="ts">
//@ts-nocheck
import { Calendar } from 'v-calendar'
import type { DateRangeSource } from 'v-calendar/dist/types/src/utils/date/range'
import { computed } from 'vue'

export type CalendarEvent = {
  label: string
  dates: DateRangeSource | Date[]
  type: 'lesson' | 'free'
}

type CalendarProps = {
  events: CalendarEvent[]
}

const colorList = ['blue', 'red', 'brown', 'green', 'yellow', 'coral', 'indigo']

const props = defineProps<CalendarProps>()

const createEventObject = (event: CalendarEvent) => {
  if (event.type === 'free') {
    return {
      content: {
        color: 'purple',
        style: {
          fontStyle: 'italic'
        }
      },
      popover: {
        label: event.label
      },
      dates: event.dates
    }
  }

  return {
    dot: colorList[Math.floor(Math.random() * colorList.length)],
    popover: {
      label: event.label
    },
    dates: event.dates
  }
}

const eventObjectsList = computed(() => props.events.map((e) => createEventObject(e)))
</script>

<template>
  <Calendar expanded :initial-page="new Date()" :attributes="eventObjectsList"></Calendar>
</template>
