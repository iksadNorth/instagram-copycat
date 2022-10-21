<template>
    <v-container>
        <!-- 계정 정보란 -->
        <v-row>
            <com-account-profile class="mx-16 my-6" :data="user" />
        </v-row>

        <v-divider class="my-6" />

        <!-- 사진 나열란 -->
        <v-row>
            <com-container-img :album="album" />
        </v-row>
    </v-container>
</template>

<script>
import * as Req from "@/dto/Request";
import * as Res from "@/dto/Response";

export default {
    data() {
        return {
            user: {
                uid: 0,
                name: "",
                nickname: "",
                articles: 0,
                followers: 0,
                followings: 0,
                introduction: "",
            },
            album: [
                {
                    pid: 90384,
                    imgSrc: "https://img.freepik.com/premium-photo/abstract-blue-background-with-smooth-lines_476363-5999.jpg?w=900",
                    likes: 42,
                    comments: 21,
                },
                {
                    pid: 66367,
                    imgSrc: "https://img.freepik.com/premium-photo/city-landscape-with-group-building-sky-sunlight_254791-864.jpg?w=900",
                    likes: 15,
                    comments: 14,
                },
                {
                    pid: 8734,
                    imgSrc: "https://img.freepik.com/premium-photo/background-empty-red-dark-podium-with-lights-tile-floor-3d-rendering_314485-400.jpg?w=900",
                    likes: 72,
                    comments: 16,
                },
                {
                    pid: 34987,
                    imgSrc: "https://img.freepik.com/free-photo/water-texture-background-pastel-blue-design_53876-146663.jpg?w=900&t=st=1665476193~exp=1665476793~hmac=42a4b7f81eee85146011a5ca0d710b4c063080bc440e7f2c3ec9ecf205f4ff83",
                    likes: 36,
                    comments: 8,
                },
            ],
        }
    },
    computed: {
        uid() {return this.$route.params.uid;}
    },
    methods: {
        fetchProfile() {
            // 기본 정보 fetch
            this.$axios({
                method: 'get', url: this.$to(`/accounts/${this.uid}`),
            }).then(res => {
                this.user = Object.assign(
                    this.user, 
                    Res.AccountReadByIdResponse.of(res).content
                    );
            }).catch(res => {
                const error = Res.ErrResponse.of(res);
                console.log(error);
            });
        },
        fetchArticles() {
            // 게시물 조회 fetch
            this.$axios({
                method: 'get', url: this.$to(`/accounts/${this.uid}/articles`),
            }).then(res => {
                this.album = this.album.concat(Res.ArticleReadResponse.of(res).content);
            }).catch(res => {
                const error = Res.ErrResponse.of(res);
                console.log(error);
            });
        },
    },
    mounted() {
        this.fetchProfile();
        this.fetchArticles();
    }
}
</script>

<style>

</style>