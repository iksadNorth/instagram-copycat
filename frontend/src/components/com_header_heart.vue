<template>
    <v-card class="ygaps px-5 window">
        <com-profile 
            v-for="profile of profilesSearched" :key="profile" 
            :data="profile" 
        />
    </v-card>
</template>

<script>
export default {
    data() {
        return {
            profilesSearched: [
                { name: "kakao_career", },
                { name: "kakao_career", },
                { name: "kakao_career", },
                { name: "kakao_career", },
                { name: "kakao_career", },
                { name: "kakao_career", },
                { name: "kakao_career", },
                { name: "kakao_career", },
            ],
        }
    }
}
</script>

<style scoped>
    .ygaps > * {
        margin-top: 5%;
        margin-bottom: 5%;
    }
    .window {
        min-height: 15vh;
        max-height: 30vh;
    }
</style>