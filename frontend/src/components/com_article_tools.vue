<template>
    <div class="d-flex flex-row align-center">
        <v-icon class="mr-4 clickable" @click="onClickComment">mdi-comment-outline</v-icon>
        <v-icon class="mr-4 clickable" @click="onClickSend">mdi-send-outline</v-icon>
        <v-icon class="mr-4 clickable" @click="onClickHeart">mdi-heart-outline</v-icon>
        <v-spacer></v-spacer>
        <v-icon class="clickable" @click="onClickBookmark">mdi-bookmark-outline</v-icon>
    </div>
</template>

<script>
export default {
    methods: {
        onClickHeart() {console.log("Click onClickHeart")},
        onClickComment() {console.log("Click onClickComment")},
        onClickSend() {console.log("Click onClickSend")},
        onClickBookmark() {console.log("Click onClickBookmark")},
    },
}
</script>

<style scoped>
    .clickable {
        cursor: pointer;
    }
</style>