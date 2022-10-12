<template>
    <div class="d-flex flex-row align-center">
        <p
        @click="onClickLikes(data.cid)" class="clickable" :style="{fontSize: size+'px', color: color}"
        ><strong>{{ label.likes.label }} {{ data.likes }}{{ label.unit.label }}</strong></p>
    </div>
</template>

<script>
export default {
    props: {
        data: Object,
        // data: {
        //         cid: 12523,
        //         likes: 83,
        //     },
        size: {
            type: Number,
            default: 15,
        },
        color: {
            type: String,
            default: "black",
        }
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
        onClickLikes(cid) {console.log("Click onClickLikes. cid: " + cid)},
    },
}
</script>
<style scoped>
    .clickable {
        cursor: pointer;
    }
</style>