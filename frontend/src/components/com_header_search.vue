<template>
    <!-- 검색창 -->
    <v-text-field
        id="search-activator"
        density="compact" variant="outlined" clearable hide-details="auto" class="search" bg-color="#F0F0F0"
        :prepend-inner-icon="inputs.search.icon"
        @click:prepend-inner="doSearch(inputs.search.value)"
        @keyup.enter="doSearch(inputs.search.value)"
        v-model="inputs.search.value"
    />
    <!-- 포커싱 되었을 때 띄울 창 -->
    <v-menu activator="#search-activator">
        <v-card class="ygaps px-5 window">
            <com-profile 
                v-for="profile of profilesSearched" :key="profile" 
                :data="profile" 
            />
        </v-card>
    </v-menu>
</template>

<script>
export default {
    data() {
        return {
            inputs: {
                search: {icon: "mdi-magnify", value: undefined},
            },
            profilesSearched: [
                { writer: "kakao_career", },
                { writer: "kakao_career", },
                { writer: "kakao_career", },
                { writer: "kakao_career", },
                { writer: "kakao_career", },
                { writer: "kakao_career", },
                { writer: "kakao_career", },
                { writer: "kakao_career", },
            ],
        }
    },
    methods: {
        doSearch(keyword) {console.log("Click doSearch. keyword: " + keyword);},
        doPreSearch(keyword) {console.log("Click doPreSearch. keyword: " + keyword);},
    },
    computed: {
        search() { return this.inputs.search.value; },
    },
    watch: {
        search(newVal) { this.doPreSearch(newVal); },
    },
}
</script>

<style scoped>
    .ygaps > * {
        margin-top: 5%;
        margin-bottom: 5%;
    }
    .search {
        padding-left: 7%;
        padding-right: 7%;
    }
    .window {
        min-height: 15vh;
        max-height: 30vh;
    }
</style>
