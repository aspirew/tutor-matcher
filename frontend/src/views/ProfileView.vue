<script setup lang="ts">
import { ref } from 'vue'
import jwt_decode from 'jwt-decode'
import { useTokenStore } from '@/stores/tokenStore'
import MainProfileData from '@/components/profile/MainProfileData.vue';
import SectionComponent from '@/components/SectionComponent.vue';
// import { GMapMap, GMapCluster, GMapMarker } from '@fawmi/vue-google-maps'

const encodedJwt = useTokenStore().getToken()

const name = ref('')
const mail = ref('')
const imgSrc = ref('')

if (encodedJwt) {
  name.value = (jwt_decode(encodedJwt) as any)['name']
  mail.value = (jwt_decode(encodedJwt) as any)['email']
  imgSrc.value = (jwt_decode(encodedJwt) as any)['picture']
}

const position = ref({lat: 51.093048, lng: 6.842120})
const marker = ref({
          position: {
            lat: 51.093048, lng: 6.842120
          },
        })

</script>

<template>
  <h1>Witaj {{ name }}</h1>
  <SectionComponent>
    <MainProfileData :name="name" :birthday="new Date()" :picture_url="imgSrc" phone="123 123 123" />
  </SectionComponent>

  <h2>Twoja lokalizacja</h2>
  <SectionComponent>
  <GMapMap
      :center="position"
      :zoom="7"
      style="width: 100%; height: 900px"
  >
  </GMapMap>
  </SectionComponent>

</template>

<style lang="scss">
</style>
