import login from "@/components/com_login";
import join from "@/components/com_join";
import app_download from "@/components/com_app_download";

export default {
    install(Vue) {
        Vue.component("com-login", login);
        Vue.component("com-join", join);
        Vue.component("com-app", app_download);
    }
  };
  