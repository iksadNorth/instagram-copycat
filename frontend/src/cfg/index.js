const backendUrl = 'http://localhost:8080';

export default {

    install(Vue) {
        Vue.config.globalProperties.$backendUrl = backendUrl;
        Vue.config.globalProperties.$to = (url) => {return backendUrl + "/api/v1" + url;};

    }
}