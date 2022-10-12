<template>
  <v-card>
    <!-- 게시물 작성자 -->
    <com-profile :data="data" />

    <v-divider/>

    <!-- 게시물 사진 -->
    <com-img :data="data"/>

    <!-- 게시물 각종 버튼들 -->
    <com-article-tools />

    <!-- 게시글 좋아요 표시 -->
    <com-likes :data="data" />

    <!-- 게시글 내용 -->
    <com-article-content :data="data" />

    <v-divider/>

    <!-- 게시글 댓글 작성 -->
    <com-post-comment/>
  </v-card>
</template>

<script>
export default {
    props: {
        data: Object,
        // data: {
        //     writer: "kakao_career",
        //     imgSrc: "article-img-example.jpg",
        //     likes: 83,
        //     content: "모델 구조가 스케일링에 어떠한 영향을 미칠까?",
        //     createdAt: "8월 5",
        // },
    },
}
</script>

<style scoped>
    .v-card {
        padding: 10px;
    }
</style>