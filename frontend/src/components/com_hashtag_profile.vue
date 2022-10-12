<template>
    <v-row>
        <v-col cols="4">
            <v-avatar 
                color="red" :size="size" variant="tonal"
                class="mr-4"
            >{{ mkAvatar(data.name) }}</v-avatar>
        </v-col>
        <v-col class="ygaps">
            <v-row>
                <p
                    class="title"
                ><strong>#{{data.name}}</strong></p>
                
            </v-row>
            <v-row>
                <p
                    class="metaInfo"
                >{{ label.articles.label }}&nbsp;<strong>{{ data.articles }}</strong></p>
            </v-row>
            <v-row>
                <v-btn
                    block density="compact"
                    @click="onClickFollow" class="btn--follow"
                ><strong>팔로우</strong></v-btn>
            </v-row>
        </v-col>
    </v-row>
</template>

<script>
export default {
    props: {
        data: Object,
        // data: {
        //         hid: 634643,
        //         name: "가로수길맛집",
        //         articles: "832",
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

        onClickFollow() {console.log("Click onClickArticles")},
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
</style>