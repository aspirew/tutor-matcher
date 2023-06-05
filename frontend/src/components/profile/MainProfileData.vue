<script setup lang="ts">
import GridComponent from '../GridComponent.vue';
import { NButton } from 'naive-ui'

type LoginProps = {
  name: string,
  birthday?: Date,
  phone?: string,
  picture_url: string,
}

defineProps<LoginProps>()
</script>

<template>
  <GridComponent :columns="3" :center="true">
    <div>
      <img :src="picture_url" />
    </div>
    <GridComponent :columns="2">
      <div>
        Name: 
      </div>
      <div>
        {{ name }}
      </div>
      <div>
        Birthday: 
      </div>
      <div>
        {{ birthday?.toDateString() ?? "Not provided" }}
      </div>
      <div>
        Phone: 
      </div>
      <div>
        {{ phone ?? "Not provided" }}
      </div>
    </GridComponent>
    <div>
      <NButton primary color="#FFA500">Edit</NButton>
    </div>
  </GridComponent>
  
</template>
