<template>
    <v-container>
    <v-row id="body">
        <!-- 좌측 열 -->
        <v-col id="left--sector"
        >
            <com-img class="fill ma-n3 my-n8" :data="data"/>
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
            <com-article-tools />

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
export default {
    data() {
        return {
            data: {
                writer: "kakao_career",
                imgSrc: "article-img-example.jpg",
                likes: 83,
                content: "모델 구조가 스케일링에 어떠한 영향을 미칠까?",
                createdAt: "8월 5",
            }
        }
    },
    computed: {
        pid() {return this.$route.params.pid;},
    },
}
</script>

<style scoped>
    #left--sector {
        background-color: black;
        color: white;
    }
    #right--sector {
        background-color: rgb(156, 203, 153);
    }
    #body {
        background-color: blueviolet;
    }
    .inline {
        display: flex;
        align-items: center;
    }
</style>