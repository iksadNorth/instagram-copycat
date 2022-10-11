<template>
    <div class="d-flex flex-row align-center mt-3">
        <p
        @click="onClickLikes" class="clickable" 
        ><strong>{{ label.likes.label }} {{ data.likes }}{{ label.unit.label }}</strong></p>
    </div>
</template>

<script>
export default {
    props: {
        data: Object,
        // data: {
        //         likes: 83,
        //     },
    },
    data() {
        return {
            label: {
                likes: {label: "좋아요"},
                unit: {label: "개"}
            },
        }
    },
    methods: {
        onClickLikes() {console.log("Click onClickLikes")},
    },
}
</script>
<style scoped>
    .clickable {
        cursor: pointer;
    }
</style>