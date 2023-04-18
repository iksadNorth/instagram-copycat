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
            <div class="frame" >
                <com-comment
                    v-for="comment of comments" :key="comment"
                    :data="comment" @setObject="changeObject"
                />
            </div>
                

            <v-divider class="my-4"/>

            <!-- 게시물 각종 버튼들 -->
            <com-article-tools 
                :hideLike="isHiddenLikes"
                :data="data" 
            />

            <!-- 게시글 좋아요 표시 -->
            <com-likes 
                v-if="!isHiddenLikes"
                :data="data" 
            />

            <!-- 작성 일자 -->
            <com-createdAt :data="data" />

            <v-divider/>

            <!-- 게시글 댓글 작성 -->
            <com-post-comment
                v-if="!notAllowedToComment" 
                :data="data" 
                :getObject="commentTo" @setObject="changeObject" 
                @update="fetchComments" 
            />
    </v-col>
    </v-row>
</v-container>
</template>

<script>
import * as Res from "@/dto/Response";
import { Message } from '@/utils/comment'

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
            },
            comments: [
                // {
                //     uid: 1,
                //     pid: 2,
                //     nickname: "NickName",
                //     cid: 12,
                //     createdAt: "2022-02-02",
                //     likes: 83,
                //     content: "게시글 내용",
                // }
            ],
            commentTo: Message.of(),
        }
    },
    computed: {
        pid() {return this.$route.params.pid;},
        notAllowedToComment() {return this.data.notAllowedComments;},
        isHiddenLikes() {return this.data.isHideLikesAndViews;},
    },
    methods: {
        fetchPost: async function() {
            console.log("call fetchPost");
            return this.$axiosAuth({
                method: 'get', url: this.$to(`/articles/${this.pid}`),
            });
        },
        fetchComments() {
            console.log("call fetchComments");
            this.$axiosAuth({
                method: 'get', url: this.$to(`/articles/${this.pid}/comments`),
            }).then(res => {
                this.comments = Res.CommentReadResponse.of(res).content;
            }).catch(res => {
                const error = Res.ErrResponse.of(res);
                console.log(error);
            });
        },
        changeObject(val) {
            this.commentTo = val;
        }
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
        this.fetchComments();
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
        min-width: 400px
    }
    #body {
        background-color: black;
    }
    .inline {
        display: flex;
        align-items: center;
    }
    .frame {
        height: 40vh;
        overflow-x: scroll;
        overflow-y: scroll;
    }
</style>