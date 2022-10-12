import login                    from "@/components/com_login";
import join                     from "@/components/com_join";
import app_download             from "@/components/com_app_download";
import joinform                 from "@/components/com_joinform";
import birthform                from "@/components/com_birthform";
import vertificationform        from "@/components/com_vertificationform";
import termform                 from "@/components/com_termform";
import loginquery               from "@/components/com_loginquery";
import article_small            from "@/components/com_article_small";
import article_content          from "@/components/com_article_content";
import article_post_comment     from "@/components/com_article_post_comment";
import article_likes            from "@/components/com_article_likes";
import article_created_at       from "@/components/com_article_created_at";
import article_profile          from "@/components/com_article_profile";
import article_tools            from '@/components/com_article_tools'
import image                    from '@/components/com_image'
import follow_recommend         from '@/components/com_follow_recommend'
import container_img            from '@/components/com_container_img'
import image_with_info          from '@/components/com_image_with_info'
import btn_follow               from '@/components/com_btn_follow'
import comment_set              from '@/components/com_comment_set'
import account_profile          from "@/components/com_account_profile";
import hashtag_profile          from "@/components/com_hashtag_profile";

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
        Vue.component("com-article-sm", article_small);

        Vue.component("com-article-content", article_content);
        Vue.component("com-post-comment", article_post_comment);
        Vue.component("com-likes", article_likes);
        Vue.component("com-createdAt", article_created_at);
        Vue.component("com-profile", article_profile);
        Vue.component("com-article-tools", article_tools);
        Vue.component("com-img", image);
        
        Vue.component("com-follow-rec", follow_recommend);
        
        Vue.component("com-container-img", container_img);
        Vue.component("com-img-info", image_with_info);

        Vue.component("com-btn-follow", btn_follow);
        Vue.component("com-comment", comment_set);

        Vue.component("com-account-profile", account_profile);
        Vue.component("com-hashtag-profile", hashtag_profile);
    }
};
  