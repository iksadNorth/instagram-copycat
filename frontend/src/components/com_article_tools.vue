<template>
    <div class="d-flex flex-row align-center">
        <v-icon class="mr-4 clickable" @click="onClickComment">mdi-comment-outline</v-icon>
        <com-btn-like :data=data v-if="!hideLike"/>
    </div>
</template>

<script>
export default {
    props: {
        data: Object,
        hideLike: {
            type: Boolean,
            default: false,
        }
    },
    methods: {
        onClickComment() {
            console.log("Click onClickComment");
            this.$router.push(`/p/${this.data.pid}`);
        },
    },
}
</script>

<style scoped>
    .clickable {
        cursor: pointer;
    }
</style>