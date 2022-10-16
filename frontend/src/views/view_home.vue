<template>
<!-- 로그인하지 않았을 때 -->
<v-container v-if="!isLogin">
    <v-row id="body">
        <!-- 좌측 열 -->
        <v-col id="left--sector" 
        class="pa-16"
        >
            <v-img
            src="@/assets/homepageLeft.png"
            ></v-img>
        </v-col>

        <!-- 우측 열 -->
        <v-col id="right--sector"
        cols="5"
        >
            <v-row class="right--rows"><com-login class="w-100"/></v-row>
            <v-row class="right--rows"><com-join class="w-100"/></v-row>
            <v-row class="right--rows"><com-app class="w-100"/></v-row>
        </v-col>
    </v-row>
</v-container>

<!-- 로그인했을 때 -->
<v-container v-if="isLogin">
    <v-row id="body" align="start">
        <!-- 좌측 열 -->
        <v-col id="left--sector" 
        >
            <com-article-sm :data="data"/>
            <com-article-sm :data="data"/>
        </v-col>

        <!-- 우측 열 -->
        <v-col id="right--sector"
        >
            <com-profile :data="data" size="60"/>
            <com-follow-rec :data="data"/>
        </v-col>
    </v-row>
</v-container>
</template>

<script>
export default {
    data() {
        return {
            data: {
                uid: 634643,
                writer: "kakao_career",
                imgSrc: "article-img-example.jpg",
                likes: 83,
                content: "모델 구조가 스케일링에 어떠한 영향을 미칠까?",
                createdAt: "8월 5",
            },
        }
    },
    computed: {
        isLogin() {return this.$store.getters["isLogin"];},
    },
}
</script>

<style scoped>
    #left--sector {
        /* background-color: beige; */
    }
    #right--sector {
        /* background-color: rgb(156, 203, 153); */
    }
    #body {
        /* background-color: blueviolet; */
    }
    .right--rows {
        /* background-color: aqua; */
        
        margin: 1px;
    }
    .v-row {
        justify-content: center;
        align-items: center;
    }
    .v-col {
        padding: 30px;

        justify-content: center;
        align-items: center;
    }
    .v-card {
        border-radius: 15px;
    }
</style>
