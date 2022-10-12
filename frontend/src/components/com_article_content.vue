<template>
    <v-row>
    <v-col cols="auto" v-if="isExistAvatar">
        <!-- 아바타 -->
        <v-avatar 
            color="red" :size="size" variant="tonal"
            class="clickable"
            @click="onClickProfile"
        >{{ mkAvatar(data.writer) }}</v-avatar>
    </v-col>
    <v-col>
        <!-- 내용 -->
        <p
        @click="onClickProfile" class="clickable" 
        >
            <strong>{{ data.writer }}</strong>
            &nbsp;&nbsp;
            {{ data.content }}
        </p>

        <!-- 작성 일자 -->
        <com-createdAt :data="data"/>
    </v-col>
    </v-row>
</template>

<script>
export default {
    props: {
        data: Object,
        // data: {
        //         writer: "게시글 작성자 NickName",
        //         content: "게시글 내용",
        //     },
        isExistAvatar: {
            type: Boolean,
            default: false
        },
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
    .v-col {
        /* background-color: rgb(118, 17, 17);
        opacity: 1; */
        margin: 1px;
    }
</style>