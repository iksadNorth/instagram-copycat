<template>
    <v-card
    variant="outlined"
    class="d-flex flex-row justify-center"
    >
        <p>{{ label.askHavingAccount.label }}</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <router-link to="label.join.linkTo">{{ label.join.label }}</router-link>
    </v-card>
</template>

<script>
export default {
    data() {
        return {
            label: {
                askHavingAccount: {label: "계정이 없으신가요?"},
                join: {label: "가입하기", linkTo: "/join"}, // TODO: linkTo에 대해 좀더 생각하기.
            }
        }
    },
    methods: {

    },
    watch: {
        
    },
}
</script>

<style scoped>
    .v-card {
        padding: 30px;
    }
    a {
        color: blue;
        font-weight: bold;
        text-decoration: none;
    }
</style>
