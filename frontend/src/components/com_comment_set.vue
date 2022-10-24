<template>
    <v-row>
    <v-col cols="auto" v-if="isExistAvatar">
        <!-- 아바타 -->
        <v-avatar 
            color="red" :size="size" variant="tonal"
            class="clickable"
            @click="onClickProfile"
        >{{ mkAvatar(data.nickname) }}</v-avatar>
    </v-col>
    <v-col>
        <!-- 내용 -->
        <p
        @click="onClickProfile" class="clickable" 
        >
            <strong>{{ data.nickname }}</strong>
            &nbsp;&nbsp;
            {{ data.content }}
        </p>

        <div class="d-flex flex-row align-center">
            <!-- 작성 일자 -->
            <com-createdAt :data="data"/>

            <!-- 좋아요 -->
            <com-likes :data="data" class="ml-4" size="15" color="grey" />

            <!-- 답글 달기 -->
            <p
            @click="onClickPostComment" class="clickable text-grey ml-4"
            >답글 달기<strong></strong></p>
        </div>
    </v-col>
    </v-row>
</template>

<script>
export default {
    props: {
        data: Object,
        // data: {
        //         name: "게시글 작성자 NickName",
        //         likes: 83,
        //         content: "게시글 내용",
        //     },
        isExistAvatar: {
            type: Boolean,
            default: true
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
    com-likes {
        font-size: small;
    }
</style>