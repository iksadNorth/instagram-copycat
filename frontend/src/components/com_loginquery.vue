<template>
    <v-card
    variant="outlined"
    class="d-flex flex-row justify-center"
    >
        <p>{{ label.askHavingAccount.label }}</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p id="login" @click="onClickLogin">{{ label.login.label }}</p>
    </v-card>
</template>

<script>
export default {
    data() {
        return {
            label: {
                askHavingAccount: {label: "계정이 있으신가요?"},
                login: {label: "로그인"},
            }
        }
    },
    methods: {
        onClickLogin() {
            this.$router.push("/");
            console.log("Click onClickLogin");
        }
    },
    watch: {
        
    },
}
</script>

<style scoped>
    .v-card {
        padding: 20px;
    }
    #login {
        color: blue;
        font-weight: bold;
        cursor: pointer;
    }
</style>
