<template>
    <v-container>
        <v-row>
            <com-container-img :album="album"/>
        </v-row>
    </v-container>
</template>

<script>
import * as Res from "@/dto/Response";

export default {
    data() {
        return {
            album: [
                // {
                //     pid: 34987,
                //     imgSrc: "https://img.freepik.com/free-photo/water-texture-background-pastel-blue-design_53876-146663.jpg?w=900&t=st=1665476193~exp=1665476793~hmac=42a4b7f81eee85146011a5ca0d710b4c063080bc440e7f2c3ec9ecf205f4ff83",
                //     likes: 36,
                //     comments: 8,
                // },
            ],
        }
    },
    methods: {
        fetchMyRecommend() {
            console.log("call fetchMyRecommend");
            this.$axiosAuth({
                method: 'get', url: this.$to(`/accounts/principal/articles/recommended`),
            }).then(res => {
                this.album = this.album.concat(Res.ArticleReadResponse.of(res).content);
            }).catch(res => {
                const error = Res.ErrResponse.of(res);
                console.log(error);
            });
        },
    },
    mounted() {
        this.fetchMyRecommend();
    },
}
</script>

<style>

</style>