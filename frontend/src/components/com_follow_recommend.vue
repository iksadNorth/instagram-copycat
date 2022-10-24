<template>
    <p
        class="text-grey"
    ><strong>{{ label.rec4user.label }}</strong></p>
    <v-row align="center" class="ma-3">
        <com-profile :data="data"/>
        <v-spacer/>
        <com-btn-follow :data="data" />
    </v-row>
</template>

<script>
export default {
    props: {
        data: Object,
        // data: {
        //         uid: 16274,
        //         name: "kakao_career",
        // },
    },
    data() {
        return {
            label: {
                rec4user: {label: "회원님을 위한 추천"},
            }
        }
    }
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