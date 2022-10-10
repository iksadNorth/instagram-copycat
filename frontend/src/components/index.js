import login from "@/components/com_login";
import join from "@/components/com_join";
import app_download from "@/components/com_app_download";
import joinform from "@/components/com_joinform";
import birthform from "@/components/com_birthform";
import vertificationform from "@/components/com_vertificationform";
import termform from "@/components/com_termform";
import loginquery from "@/components/com_loginquery";

export default {
    install(Vue) {
        Vue.component("com-login", login);
        Vue.component("com-join", join);
        Vue.component("com-app", app_download);
        Vue.component("com-joinform", joinform);
        Vue.component("com-birthform", birthform);
        Vue.component("com-vertificationform", vertificationform);
        Vue.component("com-termform", termform);
        Vue.component("com-loginquery", loginquery);
    }
};
  