<template>
    <div class="d-flex flex-row align-center">
        <v-text-field
            :label="inputLabel"
            variant="plain"
            @keydown.esc="setObject"
            v-model="inputs.comment.value"
        ></v-text-field>
        <p 
            @click="onClickPostComment" class="clickable text-blue"
        ><strong>{{ btn.comment.label }}</strong></p>
    </div>
</template>

<script>
import * as Req from "@/dto/Request";
import * as Res from "@/dto/Response";

import { Message } from '@/utils/comment'

export default {
    props: {
        toComment: {
            type: Boolean,
            default: false,
        },
        data: Object,
        // {
        //     pid: '게시물 아이디',
        // }
        getObject: Object,
    },
    data() {
        return {
            inputs: {
                comment: {
                    label: "댓글 달기...", 
                    labelToComment: this.commentToSomeone, 
                    value: undefined,
            },
            },
            btn: {
                comment: {label: "게시"},
            },
        }
    },
    computed: {
        inputLabel() {
            return (this.getObject.cid==null) ? this.inputs.comment.label : this.inputs.comment.labelToComment;
        },
        commentToSomeone() {return `@${this.getObject.nickname}에게 댓글 달기... (취소 시, ESC)`}
    },
    watch: {
        getObject() {
            this.inputs.comment.labelToComment = this.commentToSomeone;
        },
    },
    methods: {
        onClickPostComment() {
            console.log("Click onClickPostComment");
            this.$emit("update");
            this.sendComment(this.inputs.comment.value, this.data.pid, this.getObject.cid);
            this.inputs.comment.value = "";
            this.$emit("setObject", Message.empty());
        },
        sendComment(content, pid, cid) {
            console.log("call sendComment");
            const url = this.$to(
                (cid == null) ? `/articles/${pid}/comments`: `/comments/${cid}/children`
                );
            this.$axiosAuth({
                method: 'post', url: url,
                data: Req.CommentCreateRequest.of(content).param,
            }).then(res => {
                console.log("댓글 달기 성공.")
            }).catch(res => {
                const error = Res.ErrResponse.of(res);
                console.log(error);
            });
        },
        setObject() {
            this.$emit("setObject", Message.empty());
        }
    },
}
</script>

<style scoped>
    .clickable {
        cursor: pointer;
    }
</style>