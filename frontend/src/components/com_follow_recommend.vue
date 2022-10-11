<template>
    <p
        class="text-grey"
    ><strong>{{ label.rec4user.label }}</strong></p>
    <v-row align="center">
        <com-profile :data="data"/>
        <v-spacer/>
        <p
            class="follow clickable"
            @click="onClickFollow(data.uid)"
        ><strong>{{ label.follow.label }}</strong></p>
    </v-row>
</template>

<script>
export default {
    props: {
        data: Object,
        // data: {
        //         uid: 16274,
        //         writer: "kakao_career",
        // },
    },
    data() {
        return {
            label: {
                rec4user: {label: "회원님을 위한 추천"},
                follow: {label: "팔로우"},
            }
        }
    }
}
</script>

<style>
    .follow {
        color: aqua;
    }
    .clickable {
        cursor: pointer;
    }
</style>