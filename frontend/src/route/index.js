import { createWebHistory, createRouter } from 'vue-router';

const routes = [
  { path: '/', component: () => import("@/views/view_home.vue")},
  { path: '/accounts/emailsignup', component: () => import("@/views/view_emailsignup.vue")},
  { path: '/explore', component: () => import("@/views/view_explore.vue")},
  { path: '/p/:pid', component: () => import("@/views/view_article.vue")},
  { path: '/:uid', component: () => import("@/views/view_account.vue")},
  { path: '/explore/tags/:tag', component: () => import("@/views/view_hashtag.vue")},
];

export default createRouter({
  history: createWebHistory(),
  routes,
});
