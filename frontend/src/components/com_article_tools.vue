<template>
    <div class="d-flex flex-row align-center">
        <v-icon class="mr-4 clickable" @click="onClickComment">mdi-comment-outline</v-icon>
        <v-icon class="mr-4 clickable" @click="onClickHeart">{{ heartIcon }}</v-icon>
    </div>
</template>

<script>
import * as Res from "@/dto/Response";

export default {
    props: {
        data: Object,
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
    },
    methods: {
        filpLikes() {this.label.heart.state = !this.label.heart.state;},
        setLikes(flag) {this.label.heart.state = flag;},

        fetchLikes() {
            console.log("call fetchLikes");
                this.$axiosAuth({
                    method: 'post', url: this.$to(`/articles/${this.data.pid}/like`),
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
                    method: 'delete', url: this.$to(`/articles/${this.data.pid}/like`),
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
                    method: 'get', url: this.$to(`/articles/${this.data.pid}/like`),
                }).then(res => {
                    console.log("좋아요 확인 성공.")
                    const flag = Res.LikeReadResponse.of(res).isLike;
                    console.log(flag)
                    this.setLikes(flag);
                }).catch(res => {
                    const error = Res.ErrResponse.of(res);
                    console.log(error);
                });
        },
        onClickHeart() {
            console.log("Click onClickHeart");
            this.fetchLikes();
        },
        onClickComment() {
            console.log("Click onClickComment");
            this.$router.push(`/p/${this.data.pid}`);
        },
    },
    mounted() {
        this.fetchIsLikes();
    },
}
</script>

<style scoped>
    .clickable {
        cursor: pointer;
    }
</style>