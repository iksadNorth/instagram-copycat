<template>  
    <p
        class="follow clickable"
        @click="onClickFollow(data.uid)"
    ><strong>{{ label.follow.label }}</strong></p>
</template>

<script>
export default {
    props: {
        data: Object,
        // data: {
        //         uid: 16274,
        // },
    },
    data() {
        return {
            label: {
                follow: {label: "팔로우"},
            }
        }
    },
    methods: {
        onClickFollow(uid) {
            console.log("Click onClickFollow. id: " + uid);
        }
    },
}
</script>

<style scoped>
    .follow {
        color: aqua;
    }
    .clickable {
        cursor: pointer;
    }
</style>