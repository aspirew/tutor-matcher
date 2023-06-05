import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import { Routes } from './routes'
import ProfileView from '@/views/ProfileView.vue'
import ProfileEditView from '@/views/ProfileEditView.vue'
import { useTokenStore } from '@/stores/tokenStore'
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: Routes.HOME,
      component: HomeView,
      meta: {
        saveForPublic: true
      }
    },
    {
      path: Routes.PROFILE,
      component: ProfileView,
    },
    {
      path: Routes.PROFILE_EDIT,
      component: ProfileEditView,
    },
  ]
})

router.beforeEach(async (to) => {
  const ssoEnabled = import.meta.env.VITE_OAUTH_ENABLED
  if (ssoEnabled === 'true') {
    const { getToken } = useTokenStore()
    const saveForPublic = to.meta.saveForPublic;
    if(!saveForPublic && !getToken()) {
      toast.warning("You need to log in!")
      return Routes.HOME
    }
  }
});

export default router
