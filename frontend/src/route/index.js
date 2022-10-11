import { createWebHistory, createRouter } from 'vue-router';

const routes = [
  { path: '/', component: () => import("@/views/view_home.vue")},
  { path: '/accounts/emailsignup', component: () => import("@/views/view_emailsignup.vue")},
  { path: '/explore', component: () => import("@/views/view_explore.vue")},
];

export default createRouter({
  history: createWebHistory(),
  routes,
});
