export default {
    install(Vue) {
        Vue.component("com_footer", () => import("@/components/com_footer.vue"));
        Vue.component("com_header", () => import("@/components/com_header.vue"));
    }
  };
  