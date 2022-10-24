<template>
    <v-container v-if="isLoaded">
    <v-row id="body" align="center">
        <!-- 좌측 열 -->
        <v-col id="left--sector"
        >
            <com-img class="ma-n3 my-n8" :data="data"/>
        </v-col>

        <!-- 우측 열 -->
        <v-col id="right--sector"
        cols="5"
        >   
            <!-- 프로필 표시 -->
            <div class="inline ma-4">
                <com-profile :data="data"/>
                <com-btn-follow class="mx-8" :data="data" />
            </div>
            <v-divider />
            <!-- 게시글 내용 표시 -->
            <com-article-content class="ml-1 mt-1" :data="data" isExistAvatar />

            <!-- 댓글 표시 -->
            <com-comment class="ml-1 mt-1" :data="data" />

            <v-divider class="my-4"/>

            <!-- 게시물 각종 버튼들 -->
            <com-article-tools :data="data" />

            <!-- 게시글 좋아요 표시 -->
            <com-likes :data="data" />

            <!-- 작성 일자 -->
            <com-createdAt :data="data" />

            <v-divider/>

            <!-- 게시글 댓글 작성 -->
            <com-post-comment/>
    </v-col>
    </v-row>
</v-container>
</template>

<script>
import * as Res from "@/dto/Response";

export default {
    data() {
        return {
            isLoaded: false,
            data: {
                uid:0,
                pid:this.pid,
                nickname: "",
                imgSrc: "https://cdn.pixabay.com/photo/2022/10/05/07/37/desert-7500086__340.jpg",
                likes: 0,
                content: "",
                createdAt: "",
            }
        }
    },
    computed: {
        pid() {return this.$route.params.pid;},
    },
    methods: {
        fetchPost: async function() {
            console.log("call fetchPost");
            return this.$axiosAuth({
                method: 'get', url: this.$to(`/articles/${this.pid}`),
            });
        },
    },
    created: async function() {
        try {
            const res = await this.fetchPost();
            this.data = Object.assign(this.data, Res.ArticleReadByIdResponse.of(res).content);
            this.isLoaded = true;
        } catch(res) {
            const error = Res.ErrResponse.of(res);
            console.log(error);
        }
    },
}
</script>

<style scoped>
    #left--sector {
        background-color: white;
        color: white;
    }
    #right--sector {
        background-color: rgb(235, 235, 235);
    }
    #body {
        background-color: black;
    }
    .inline {
        display: flex;
        align-items: center;
    }
</style>