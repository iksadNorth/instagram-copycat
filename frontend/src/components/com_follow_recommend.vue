<template>
    <p
        class="text-grey"
    ><strong>{{ label.rec4user.label }}</strong></p>
    <v-row 
        align="center" class="ma-3"
        v-for="entity of data" :key="entity"
    >
        <com-profile :data="entity"/>
        <v-spacer/>
        <com-btn-follow :data="entity" />
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