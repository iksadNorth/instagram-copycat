<template>
    <v-card
    variant="outlined"
    class="d-flex flex-row justify-center"
    >
        <p>{{ label.askHavingAccount.label }}</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p id="join" @click="onClickJoin">{{ label.join.label }}</p>
    </v-card>
</template>

<script>
export default {
    data() {
        return {
            label: {
                askHavingAccount: {label: "계정이 없으신가요?"},
                join: {label: "가입하기"},
            }
        }
    },
    methods: {
        onClickJoin() {
            this.$router.push("/accounts/emailsignup");
            console.log("Click onClickJoin");
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
    #join {
        color: blue;
        font-weight: bold;
        cursor: pointer;
    }
</style>
