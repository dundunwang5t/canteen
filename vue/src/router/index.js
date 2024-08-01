import { createRouter, createWebHistory } from 'vue-router'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {path: '/', name: 'login', component: () => import('@/views/Login.vue')},
    {
      path: '/manager',
      name: 'Manager',
      redirect: "/home",
      component: () => import('@/views/Manager.vue'),
      children: [
        { path: '/home', name: 'Home', component: () => import('@/views/manager/Home.vue') },
        { path: '/admin', name: 'Admin', component: () => import('@/views/manager/Admin.vue') },
        { path: '/person', name: 'Person', component: () => import('@/views/manager/Person.vue') },
        { path: '/user', name: 'User', component: () => import('@/views/manager/User.vue') },
        { path: '/tables', name: 'Tables', component: () => import('@/views/manager/Tables.vue') },
        { path: '/order', name: 'Order', component: () => import('@/views/manager/Order.vue') },
        { path: '/foods', name: 'Foods', component: () => import('@/views/manager/Foods.vue') },
        { path: '/orderManager', name: 'OrderManager', component: () => import('@/views/manager/OrderManager.vue') },
      ]
    },

    { path: '/register', name: 'register', component: () => import('@/views/Register.vue') },
  ]




})




export default router