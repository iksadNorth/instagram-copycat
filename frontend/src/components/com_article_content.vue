<template>
    <div class="d-flex flex-row align-center">
        <p
        @click="onClickProfile" class="clickable" 
        >
            <strong>{{ data.writer }}</strong>
            &nbsp;&nbsp;
            {{ data.content }}
        </p>
    </div>
</template>

<script>
export default {
    props: {
        data: Object,
        // data: {
        //         writer: "게시글 작성자 NickName",
        //         content: "게시글 내용",
        //     },
    },
    methods: {
        onClickProfile() {console.log("Click onClickProfile")},
    },
}
</script>

<style scoped>
    .clickable {
        cursor: pointer;
    }
</style>