<template>
    <v-icon class="mr-4 clickable" @click="onClickHeart">{{ heartIcon }}</v-icon>
</template>

<script>
import * as Res from "@/dto/Response";

export default {
    props: {
        data: Object,
        // {
        //     pid: Number,
        // }
        isComment: {
            type: Boolean,
            default: false,
        },
    },
    data() {
        return {
            label: {
                heart: {isLike: "mdi-heart", noLike: "mdi-heart-outline", state: false,}
            },
        }
    },
    computed: {
        heartIcon() {return this.label.heart.state ? this.label.heart.isLike : this.label.heart.noLike;},
        endPoint() {
            return !this.isComment ? 
            this.$to(`/articles/${this.data.pid}/like`) : 
            this.$to(`/comments/${this.data.cid}/like`)
        },
    },
    methods: {
        filpLikes() {this.label.heart.state = !this.label.heart.state;},
        setLikes(flag) {this.label.heart.state = flag;},

        fetchLikes() {
            console.log("call fetchLikes");
                this.$axiosAuth({
                    method: 'post', url: this.endPoint,
                }).then(res => {
                    console.log("좋아요 삽입 성공.")
                    this.filpLikes();
                }).catch(res => {
                    const error = Res.ErrResponse.of(res);
                    console.log(error);
                });
        },
        fetchUnLikes() {
            console.log("call fetchUnLikes");
                this.$axiosAuth({
                    method: 'delete', url: this.endPoint,
                }).then(res => {
                    console.log("좋아요 삭제 성공.")
                    this.filpLikes();
                }).catch(res => {
                    const error = Res.ErrResponse.of(res);
                    console.log(error);
                });
        },
        fetchIsLikes() {
            console.log("call fetchIsLikes");
                this.$axiosAuth({
                    method: 'get', url: this.endPoint,
                }).then(res => {
                    console.log("좋아요 확인 성공.")
                    const flag = Res.LikeReadResponse.of(res).isLike;
                    this.setLikes(flag);
                }).catch(res => {
                    const error = Res.ErrResponse.of(res);
                    console.log(error);
                });
        },
        onClickHeart() {
            console.log("Click onClickHeart");
            if(this.label.heart.state) {
                this.fetchUnLikes();
            } else {
                this.fetchLikes();
            }
        },
    },
    mounted() {
        this.fetchIsLikes();
    },
}
</script>

<style>
    .clickable {
        cursor: pointer;
    }
</style>