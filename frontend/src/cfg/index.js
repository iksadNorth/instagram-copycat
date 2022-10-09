export default {
    install(Vue) {
        Vue.config.globalProperties.$backendUrl = 'http://localhost:8080';
    }
}