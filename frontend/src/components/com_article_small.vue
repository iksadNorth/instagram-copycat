<template>
  <v-card>
    <!-- 게시물 작성자 -->
    <com-profile :data="data" />

    <v-divider/>

    <!-- 게시물 사진 -->
    <com-img :data="data" />

    <!-- 게시물 각종 버튼들 -->
    <com-article-tools :data="data" />

    <!-- 게시글 좋아요 갯수 표시 -->
    <com-likes :data="data" />

    <!-- 게시글 내용 -->
    <com-article-content :data="data" />

    <!-- 댓글 더보기 -->
    <p 
      class="comment clickable my-2"
      @click="label.more.click"
    >{{ label.more.label }}</p>

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
        //     name: "kakao_career",
        //     imgSrc: "article-img-example.jpg",
        //     comments: 83,
        //     likes: 83,
        //     content: "모델 구조가 스케일링에 어떠한 영향을 미칠까?",
        //     createdAt: "8월 5",
        // },
    },
    data() {
      return {
        label: {
          more: {label: `댓글 ${this.data.comments}개 더보기`, click: this.onClickMore}
        }
      }
    },
    methods: {
      onClickMore() {
        console.log("Click onClickMore");
        this.$router.push(`/p/${this.data.pid}`);
      }
    },
}
</script>

<style scoped>
    .v-card {
        padding: 10px;
    }
    .comment {
        color: grey;
        font-size: 13px;
    }
    .clickable {
        cursor: pointer;
    }
</style>