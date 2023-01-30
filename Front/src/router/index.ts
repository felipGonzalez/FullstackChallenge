import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/components/Home.vue'
import Patient from '@/components/Patient.vue'
import Prescription from '@/components/Prescription.vue'
import PrescriptionList from '@/components/PrescriptionList.vue'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/patient/:id?',
      name: 'patient',
      component: Patient
    },
    {
      path: '/prescription/:id?',
      name: 'prescription',
      component: Prescription
    },
    {
      path: '/prescription-user/:id?',
      name: 'prescription-user',
      component: PrescriptionList
    }
    /*{
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue')
    }*/
  ]
})

export default router
