<script setup lang="ts">
import { ref } from 'vue'
import jwt_decode from 'jwt-decode'
import { useTokenStore } from '@/stores/tokenStore'
import MainProfileData from '@/components/profile/MainProfileData.vue'
import SectionComponent from '@/components/SectionComponent.vue'
// import { GMapMap, GMapCluster, GMapMarker } from '@fawmi/vue-google-maps'
import { CalendarView, CalendarViewHeader } from 'vue-simple-calendar'
import CalendarComponent, { type CalendarEvent } from '@/components/CalendarComponent.vue'
import ButtonComponent from '@/components/ButtonComponent.vue'

const encodedJwt = useTokenStore().getToken()

const name = ref('')
const mail = ref('')
const imgSrc = ref('')

if (encodedJwt) {
  name.value = (jwt_decode(encodedJwt) as any)['name']
  mail.value = (jwt_decode(encodedJwt) as any)['email']
  imgSrc.value = (jwt_decode(encodedJwt) as any)['picture']
}

const position = ref({ lat: 51.093048, lng: 6.84212 })
const marker = ref({
  position: {
    lat: 51.093048,
    lng: 6.84212
  }
})

const calendarEvents: CalendarEvent[] = [
  {
    label: 'Lesson 1: 10:30',
    dates: [new Date('06/01/2023')],
    type: 'lesson'
  },
  {
    label: 'Lesson 2: 11:30',
    dates: [new Date('06/01/2023')],
    type: 'lesson'
  },
  {
    label: 'Lesson 1: 15:00',
    dates: [new Date('06/09/2023')],
    type: 'lesson'
  },
  {
    label: 'Weekend',
    dates: { repeat: { weekdays: [1, 7] } },
    type: 'free'
  }
]
</script>

<template>
  <h1>Welcome {{ name }}</h1>
  <SectionComponent>
    <MainProfileData
      :name="name"
      :birthday="new Date()"
      :picture_url="imgSrc"
      phone="123 123 123"
    />
  </SectionComponent>

  <h2>Your location</h2>
  <SectionComponent>
    <GMapMap :center="position" :zoom="7" style="width: 100%; height: 900px"> </GMapMap>
  </SectionComponent>
  <h2>Availability</h2>
  <SectionComponent>
    <CalendarComponent :events="calendarEvents"></CalendarComponent>
  </SectionComponent>
  <h2>Your documents</h2>
  <SectionComponent center>
    You are not a teacher!
    <ButtonComponent>Become a teacher</ButtonComponent>
  </SectionComponent>
</template>

<style lang="scss"></style>
