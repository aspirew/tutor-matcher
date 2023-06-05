<script setup lang="ts">
import ButtonComponent from '@/components/ButtonComponent.vue'
import GridComponent from '@/components/GridComponent.vue'
import ImageComponent from '@/components/ImageComponent.vue'
import SectionComponent from '@/components/SectionComponent.vue'
import { NDatePicker, NInput } from 'naive-ui'
import { ref } from 'vue'

const nameRef = ref('')
const birthdayRef = ref(new Date().getTime())
const phoneRef = ref('')
const fileRef = ref()

const fileUpload = (e: Event) => {
  const file = e.target.files[0]
  fileRef.value = URL.createObjectURL(file)
}
</script>

<template>
  <h1>Edit profile data</h1>
  <SectionComponent center>
    <ImageComponent :src="fileRef" />
    <GridComponent :columns="2">
      <span
        >Picture: <input @change="fileUpload" type="file" placeholder="Name" accept="image/*"
      /></span>
      <span>Name: <NInput v-model:value="nameRef" type="text" placeholder="Name" /></span>
      <span>Birthday: <NDatePicker v-model:value="birthdayRef" /></span>
      <span>Phone: <NInput v-model:value="phoneRef" type="text" placeholder="123432848" /></span>
    </GridComponent>
  </SectionComponent>
  <SectionComponent center>
    <ButtonComponent>Edit</ButtonComponent>
  </SectionComponent>
</template>
