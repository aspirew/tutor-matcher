<script setup lang="ts">
import router from '@/router'
import ButtonComponent from '../ButtonComponent.vue'
import GridComponent from '../GridComponent.vue'
import { Routes } from '@/router/routes'
import ImageComponent from '../ImageComponent.vue'

type LoginProps = {
  name: string
  birthday?: Date
  phone?: string
  picture_url: string
}

defineProps<LoginProps>()
</script>

<template>
  <GridComponent :columns="3" :center="true">
    <div>
      <ImageComponent :src="picture_url" />
    </div>
    <GridComponent :columns="2">
      <div>Name:</div>
      <div>
        {{ name }}
      </div>
      <div>Birthday:</div>
      <div>
        {{ birthday?.toDateString() ?? 'Not provided' }}
      </div>
      <div>Phone:</div>
      <div>
        {{ phone ?? 'Not provided' }}
      </div>
    </GridComponent>
    <div>
      <ButtonComponent @click="router.push(Routes.PROFILE_EDIT)">Edit</ButtonComponent>
    </div>
  </GridComponent>
</template>
