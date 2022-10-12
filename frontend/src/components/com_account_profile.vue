<template>
    <v-row>
        <v-col cols="4">
            <v-avatar 
                color="red" :size="size" variant="tonal"
                class="mr-4 clickable"
                @click="onClickProfile"
            >{{ mkAvatar(data.name) }}</v-avatar>
        </v-col>
        <v-col class="ygaps">
            <v-row class="xgaps">
                <p
                    class="clickable title"
                ><strong>{{data.name}}</strong></p>
                <v-btn
                    flat density="compact"
                    @click="onClickFollow" class="btn--follow"
                ><strong>팔로우</strong></v-btn>
            </v-row>
            <v-row class="xgaps">
                <p
                    class="clickable metaInfo" @click="onClickArticles"
                >{{ label.articles.label }}&nbsp;<strong>{{ data.articles }}</strong></p>
                <p
                    class="clickable metaInfo" @click="onClickFollowers"
                >{{ label.followers.label }}&nbsp;<strong>{{ data.followers }}</strong></p>
                <p
                    class="clickable metaInfo" @click="onClickFollowings"
                >{{ label.followings.label }}&nbsp;<strong>{{ data.followings }}</strong></p>
            </v-row>
            <v-row class="intro">
                <p
                ><strong>{{ data.nickname }}</strong></p>
                <p
                >{{ data.introduction }}</p>
            </v-row>
        </v-col>
    </v-row>
</template>

<script>
export default {
    props: {
        data: Object,
        // data: {
        //         name: "iksad",
        //         nickname: "kakao_career",
        //         articles: "832",
        //         followers: "2535",
        //         followings: "13",
        //         introduction: "자기소개 하는 란.",
        //     },
        size: {
            type: [Number, String],
            default: 125,
        }
    },
    data() {
        return {
            label: {
                articles: {label: "게시물"},
                followers: {label: "팔로워"},
                followings: {label: "팔로우"},
            }
        }
    },
    methods: {
        mkAvatar(nickname) { return nickname.slice(0, 2).toUpperCase(); },

        onClickArticles() {console.log("Click onClickArticles")},
        onClickFollowers() {console.log("Click onClickFollowers")},
        onClickFollowings() {console.log("Click onClickFollowings")},

        onClickFollow() {console.log("Click onClickFollow")},
    },
}
</script>

<style scoped>
    .clickable {
        cursor: pointer;
    }
    .ygaps > * {
        margin-bottom: 2%;
        margin-top: 2%;
    }
    .xgaps > * {
        margin-right: 8%;
    }
    .title {
        font-size: larger;
    }
    .metaInfo {
        font-size: medium;
    }
    .btn--follow {
        background-color: dodgerblue;
        color: white;
        padding-left: 4%;
        padding-right: 4%;
    }
    .intro {
        display: block;
    }
</style>