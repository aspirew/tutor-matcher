<script setup lang="ts">
import ButtonComponent from '@/components/ButtonComponent.vue'
import GridComponent from '@/components/GridComponent.vue'
import SectionComponent from '@/components/SectionComponent.vue'
import SelectableItemComponent from '@/components/SelectableItemComponent.vue'
import { buildSearchUrl } from '@/libs/routeBuilder'
import router from '@/router'
import { NSelect } from 'naive-ui'
import { ref } from 'vue'
import { toast } from 'vue3-toastify'

const selectOptions = ref([
  {
    label: 'Math',
    value: 'math'
  },
  {
    label: 'Physics',
    value: 'physics'
  },
  {
    label: 'English',
    value: 'english'
  }
])

const subject = ref(null)
const remoteRef = ref(true)
const atHomeRef = ref(true)
const atTeacherRef = ref(true)

const preapareLessonFormsForRequest = () => {
  const forms = []
  if (remoteRef.value) forms.push('remotely')
  if (atHomeRef.value) forms.push('atHome')
  if (atTeacherRef.value) forms.push('atTeacher')
  return forms
}

const submit = () => {
  if (!subject.value || (!remoteRef.value && !atHomeRef.value && !atTeacherRef.value)) {
    toast.warning('You need to fill in all data!')
    return
  }
  router.push(buildSearchUrl(subject.value, preapareLessonFormsForRequest()))
}
</script>

<template>
  <h1>Search for a perfect tutor</h1>
  <SectionComponent>
    <h3>What would you like to learn?</h3>
    <NSelect v-model:value="subject" :options="selectOptions" placeholder="Subject" />
  </SectionComponent>
  <SectionComponent>
    <h3>Select form of lesson</h3>
    <GridComponent :columns="3" max-width="100px">
      <SelectableItemComponent v-model="remoteRef">Remotely</SelectableItemComponent>
      <SelectableItemComponent v-model="atHomeRef">At home</SelectableItemComponent>
      <SelectableItemComponent v-model="atTeacherRef">At teacher's</SelectableItemComponent>
    </GridComponent>
  </SectionComponent>
  <SectionComponent>
    <ButtonComponent wide @click="submit">Search</ButtonComponent>
  </SectionComponent>
</template>

<script lang="scss"></script>
