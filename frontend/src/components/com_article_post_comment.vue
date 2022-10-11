<template>
    <div class="d-flex flex-row align-center">
        <v-text-field
            :label="inputs.comment.label"
            variant="plain"
        ></v-text-field>
        <p 
        @click="onClickPostComment" class="clickable text-blue"
        ><strong>{{ btn.comment.label }}</strong></p>
    </div>
</template>

<script>
export default {
    data() {
        return {
            inputs: {
                comment: {label: "댓글 달기..."},
            },
            btn: {
                comment: {label: "게시"},
            },
        }
    },
    methods: {
        onClickPostComment() {console.log("Click onClickPostComment")},
    },
}
</script>

<style scoped>
    .clickable {
        cursor: pointer;
    }
</style>