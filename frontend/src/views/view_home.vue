<template>
<!-- 로그인하지 않았을 때 -->
<v-container v-if="!isLogin">
    <v-row id="body">
        <!-- 좌측 열 -->
        <v-col id="left--sector" 
        class="pa-16"
        >
            <v-img
            src="@/assets/homepageLeft.png"
            ></v-img>
        </v-col>

        <!-- 우측 열 -->
        <v-col id="right--sector"
        cols="5"
        >
            <v-row class="right--rows"><com-login class="w-100"/></v-row>
            <v-row class="right--rows"><com-join class="w-100"/></v-row>
            <v-row class="right--rows"><com-app class="w-100"/></v-row>
        </v-col>
    </v-row>
</v-container>

<!-- 로그인했을 때 -->
<v-container v-if="isLogin">
    <v-row id="body" align="start">
        <!-- 좌측 열 -->
        <v-col id="left--sector" 
        >
            <div 
                v-for="feed of feeds" :key="feed"
                class="my-5"
            >
                <com-article-sm :data="feed"/>
            </div>
            <div v-if="feeds.length==0">
                <h2 class="text-center">{{ label.recommendFollow.label }}</h2>
            </div>
        </v-col>

        <!-- 우측 열 -->
        <v-col id="right--sector"
        >
            <com-profile :data="myProfile" size="60"/>
            <com-follow-rec :data="yetFollows"/>
        </v-col>
    </v-row>
</v-container>
</template>

<script>
import * as Res from "@/dto/Response";

export default {
    data() {
        return {
            label: {
                recommendFollow: {label: "다른 사람들 팔로우해보세요!!"}
            },
            myProfile: {
                uid: 634643,
                nickname: "kakao_career",
                imgSrc: "article-img-example.jpg",
                likes: 83,
                content: "모델 구조가 스케일링에 어떠한 영향을 미칠까?",
                createdAt: "8월 5",
            },
            feeds: [
                // {
                //     pid: ~~,
                //     createdAt: ~~,
                    
                //     uid: ~~,
                //     nickname: ~~,
                //     imgSrc: ~~,
                //     content: ~~,

                //     comments: ~~,
                //     likes: ~~,
                //     views: ~~,
                // }
            ],
            yetFollows: [
                // {
                //     uid: ~~,
                //     name: ~~,
                //     nickname: ~~,
                //     introduction: ~~,
                //     articles: ~~,
                //     followers: ~~,
                //     followings: ~~,
                // }
            ],
        }
    },
    computed: {
        isLogin() {return this.$store.getters["isLogin"];},
    },
    methods: {
        fetchMyProfile() {
            console.log("call fetchMyProfile");
            this.$axiosAuth({
                method: 'get', url: this.$to(`/accounts/principal`),
            }).then(res => {
                this.myProfile = Res.AccountReadByIdResponse.of(res).content;
            }).catch(res => {
                const error = Res.ErrResponse.of(res);
                console.log(error);
            });
        },
        fetchFeeds() {
            console.log("call fetchFeeds");
            this.$axiosAuth({
                method: 'get', url: this.$to(`/accounts/principal/articles/follow`),
            }).then(res => {
                this.feeds = this.feeds.concat(Res.ArticleReadResponse.of(res).content);
            }).catch(res => {
                const error = Res.ErrResponse.of(res);
                console.log(error);
            });
        },
        fetchYetFollow() {
            console.log("call fetchYetFollow");
            this.$axiosAuth({
                method: 'get', url: this.$to(`/accounts/principal/following`),
            }).then(res => {
                this.yetFollows = this.yetFollows.concat(Res.AccountReadResponse.of(res).content);
            }).catch(res => {
                const error = Res.ErrResponse.of(res);
                console.log(error);
            });
        },
    },
    mounted() {
        this.fetchMyProfile();
        this.fetchFeeds();
        this.fetchYetFollow();
    }
}
</script>

<style scoped>
    #left--sector {
        /* background-color: beige; */
    }
    #right--sector {
        /* background-color: rgb(156, 203, 153); */
    }
    #body {
        /* background-color: blueviolet; */
    }
    .right--rows {
        /* background-color: aqua; */
        
        margin: 1px;
    }
    .v-row {
        justify-content: center;
        align-items: center;
    }
    .v-col {
        padding: 30px;

        justify-content: center;
        align-items: center;
    }
    .v-card {
        border-radius: 15px;
    }
</style>
