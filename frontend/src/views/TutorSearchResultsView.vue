<script setup lang="ts">
import GridComponent from '@/components/GridComponent.vue'
import TutorCard from '@/components/TutorCard.vue'
import { Endpoints } from '@/composables/endpoints'
import { useGet } from '@/composables/useApi'
import type { User } from '@/models'
import { urlParamsBuilder } from '@/libs/routeBuilder'
import router from '@/router'
import { useRoute } from 'vue-router'
import { NSpin } from 'naive-ui'

const users: User[] = [
  {
    userID: '1',
    name: 'Adam'
  },
  {
    userID: '2',
    name: 'Jacek'
  },
  {
    userID: '3',
    name: 'Rafał'
  },
  {
    userID: '4',
    name: 'Krzysztof'
  },
  {
    userID: '5',
    name: 'Gosia'
  },
  {
    userID: '6',
    name: 'Ania'
  }
]

const params = useRoute().params

const { isFetching, isFinished, data } = useGet(Endpoints.MATCH, {
  subject: params.subject as string,
  forms: params.forms as string
})
</script>

<template>
  <div v-show="isFinished">
    <h1>Search results</h1>
    <GridComponent min-width="250px">
      <TutorCard v-for="user in users" :key="user.userID" :user-id="user.userID">{{
        user.name
      }}</TutorCard>
    </GridComponent>
  </div>
  <NSpin v-show="isFetching"></NSpin>
</template>

<script lang="scss"></script>
