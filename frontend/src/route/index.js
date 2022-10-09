import { createWebHistory, createRouter } from 'vue-router';

const routes = [
  { path: '/', component: () => import("@/views/view_home.vue")},
];

export default createRouter({
  history: createWebHistory(),
  routes,
});
