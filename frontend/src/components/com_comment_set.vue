<template>
    <v-container class="grid">
        <v-row class="flex-nowrap">
            <v-col cols="2">
                <!-- 아바타 -->
                <v-avatar 
                    color="red" :size="size" variant="tonal"
                    class="clickable"
                    @click="onClickProfile"
                >{{ mkAvatar(data.nickname) }}</v-avatar>
            </v-col>
            <v-col>
                <v-row class="flex-nowrap">
                <v-col>
                    <!-- 내용 -->
                    <p>
                        <strong
                            @click="onClickProfile" class="clickable" 
                        >{{ data.nickname }}</strong>
                        &nbsp;&nbsp;
                        {{ data.content }}
                    </p>
            
                    <div class="d-flex flex-row align-center text-straight">
                        <!-- 작성 일자 -->
                        <com-createdAt :data="data" />
            
                        <!-- 좋아요 -->
                        <com-likes :data="data" class="ml-4" size="15" color="grey" />
            
                        <!-- 답글 달기 -->
                        <p
                            @click="onClickPostComment" class="clickable text-grey ml-4"
                        >답글 달기<strong></strong></p>
                    </div>
                </v-col>
                <v-col cols="auto">
                    <!-- 좋아요 버튼 -->
                    <com-btn-like :data="data" isComment />
                </v-col>
                </v-row>
            </v-col>
        </v-row>
        <v-row class="flex-nowrap" v-if="!isEmptyList">
            <v-col cols="2">
                <v-divider class="mt-3" />
            </v-col>
            <v-col>
                <!-- 댓글 확장 상태 -->
                <div v-if="label.moreComment.state=='extended'">
                    <p 
                        @click="onClickHideComments" class="clickable text-grey">
                        {{ label.moreComment.label.hide }}
                    </p>
                    <!-- 대댓글 나열 -->
                    <com-comment
                        v-for="comment of label.moreComment.childrenList" :key="comment"
                        :data="comment" @setObject="changeObject"
                    />
                </div>
                <!-- 댓글 숨기기 상태 -->
                <div v-if="label.moreComment.state=='folded'">
                    <p 
                        @click="onClickMoreComments" class="clickable text-grey">
                        {{ label.moreComment.label.more + numOfComments }}
                    </p>
                </div>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import { Message } from '@/utils/comment';
import * as Res from "@/dto/Response";

export default {
    props: {
        data: Object,
        // data: {
        //         uid: 1,
        //         cid: 12,
        //         nickname: "NickName",
        //         createdAt: 2022/02/02,
        //         likes: 83,
        //         content: "게시글 내용",
        //     },
        isExistAvatar: {
            type: Boolean,
            default: true
        },
    },
    data() {
        return {
            label: {
                moreComment: {
                    label: {more: `답글 달기`, hide: `답글 숨기기`}, 
                    click: {more: this.onClickMoreComments, hide: this.onClickHideComments}, 
                    childrenList: [],
                    // state: "extended",
                    state: "folded",
                },
            },
        }
    },
    computed: {
        numOfComments() {
            return ` (${this.data.children} 개)`;
        },
        isEmptyList() {
            return this.data.children==0;
        },
    },
    methods: {
        mkAvatar(nickname) { return nickname.slice(0, 2).toUpperCase(); },

        onClickProfile() {
            console.log("Click onClickProfile");
            this.$router.push(`/${this.data.uid}`);
        },
        onClickPostComment(){
            console.log("Click onClickProfile");
            this.$emit("setObject", Message.of(this.data.cid, this.data.nickname));
        },
        onClickHideComments(){
            console.log(`Click onClickMoreComments of ${this.data.cid}`);
            this.label.moreComment.state = "folded";
        },
        onClickMoreComments(){
            console.log(`Click onClickMoreComments of ${this.data.cid}`);
            this.fetchMoreComments(this.data.cid);
        },
        fetchMoreComments(cid) {
            this.$axiosAuth({
                method: 'get', url: this.$to(`/comments/${cid}/children`),
            }).then(res => {
                this.label.moreComment.childrenList = Res.CommentReadResponse.of(res).content;
                this.label.moreComment.state = "extended";
            }).catch(res => {
                const error = Res.ErrResponse.of(res);
                console.log(error);
            });
        },
        changeObject(val) {
            this.$emit("setObject", val);
        }
    },
}
</script>

<style scoped>
    .clickable {
        cursor: pointer;
    }
    .text-straight > * {
        white-space:nowrap; 
    }
    com-likes {
        font-size: small;
    }
    .grid {
        grid-template-columns: 1fr 1fr;
        grid-template-rows: 1fr 1fr;
    }
</style>