<template>
    <div class="d-flex flex-row align-center">
        <v-avatar 
            color="red" :size="size" variant="tonal"
            class="mr-4 clickable"
            @click="onClickProfile"
        >{{ mkAvatar(data.writer) }}</v-avatar>
        <p
        @click="onClickProfile" class="clickable"
        ><strong>{{data.writer}}</strong></p>
    </div> 
</template>

<script>
export default {
    props: {
        data: Object,
        // data: {
        //         writer: "kakao_career",
        //     },
        size: {
            type: [Number, String],
            default: 36,
        }
    },
    methods: {
        mkAvatar(nickname) { return nickname.slice(0, 2).toUpperCase(); },

        onClickProfile() {console.log("Click onClickProfile")},
    },
}
</script>

<style scoped>
    .clickable {
        cursor: pointer;
    }
</style>