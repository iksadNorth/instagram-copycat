<template>
<v-container>
    <v-row id="first--rows">
        {{screenState.toLowerCase()}}
        <com-joinform class="w-100" v-if="screenState.toLowerCase()=='joinform'"/>
        <com-birthform class="w-100" v-if="screenState.toLowerCase()=='birthform'"/>
        <com-vertificationform class="w-100" v-if="screenState.toLowerCase()=='vertificationform'"/>
        <com-termform class="w-100" v-if="screenState.toLowerCase()=='termform'"/>
    </v-row>
    <v-row id="second--rows"><com-loginquery class="w-100"/></v-row>
    <v-row id="third--rows"><com-app class="w-100"/></v-row>
</v-container>
</template>

<script>
export default {
    computed: {
        screenState() {
            return this.$store.state.emailsignup.screenState;
        },
    },
}
</script>

<style scoped>
    #first--rows {
        /* background-color: beige; */
    }
    #second--rows {
        /* background-color: rgb(156, 203, 153); */
    }
    #third--rows {
        /* background-color: blueviolet; */
    }
    .v-container {
        min-width: 350px;
        width: 30%;
    }
    .v-row {
        margin: 10px;
    }
</style>
