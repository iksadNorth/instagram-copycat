<template>
    <v-container>
        <!-- 계정 정보란 -->
        <v-row>
            <com-account-profile class="mx-16 my-6" :uid="uid" />
        </v-row>

        <v-divider class="my-6" />

        <!-- 사진 나열란 -->
        <v-row>
            <com-container-img :album="album" />
        </v-row>
        <v-row v-if="album.length==0" justify="center">
            <h1>{{ label.noItem.label }}</h1>
        </v-row>
    </v-container>
</template>

<script>
import * as Res from "@/dto/Response";

export default {
    data() {
        return {
            label: {
                noItem: {label: "게시물이 존재하지 않습니다."}
            },
            album: [],
        }
    },
    computed: {
        uid() {return this.$route.params.uid;}
    },
    watch: {
        uid() {this.$router.go()},
    },
    methods: {
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
        this.fetchArticles();
    }
}
</script>

<style>

</style>