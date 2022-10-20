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
            <!-- 계정 검색 결과 -->
            <v-row>
                <h2>{{ label.result_of_searching_account.label }}</h2>
            </v-row>
            <v-divider />
            <v-row v-if="label.result_of_searching_account.value">
                <h5>{{ label.result_of_searching_account.no_result }}</h5>
            </v-row>
            <v-row v-for="profile of label.result_of_searching_account.value" :key="profile" >
                <com-profile :data="profile" />
            </v-row>

            <v-spacer class="my-8" />
            
            <!-- 해시태그 검색 결과 -->
            <v-row>
                <h2>{{ label.result_of_searching_hashtag.label }}</h2>
            </v-row>
            <v-divider />
            <v-row v-if="label.result_of_searching_hashtag.value">
                <h5>{{ label.result_of_searching_hashtag.no_result }}</h5>
            </v-row>
            <v-row v-for="profile of label.result_of_searching_hashtag.value" :key="profile" >
                <com-profile :data="profile" kind="hashtag" />
            </v-row>
        </v-card>
    </v-menu>
</template>

<script>
import * as Req from "@/dto/Request";
import * as Res from "@/dto/Response";

export default {
    data() {
        return {
            inputs: {
                search: {icon: "mdi-magnify", value: undefined},
            },
            label: {
                result_of_searching_account: {label: "계정 검색 결과", no_result: "검색 결과가 없습니다.", value: []},
                result_of_searching_hashtag: {label: "해시태그 검색결과", no_result: "검색 결과가 없습니다.", value: []},
            },
        }
    },
    methods: {
        doSearch: async function (keyword) {
            keyword = keyword.trim();
            console.log("Click doSearch. keyword: " + keyword);
        },
        doPreSearch: async function (keyword) {
            keyword = keyword.trim();
            console.log("Click doPreSearch. keyword: " + keyword);

            this.label.result_of_searching_account.value = await this.fetchAccount(keyword);
            this.label.result_of_searching_hashtag.value = await this.fetchHashtag(keyword);
        },
        fetchAccount: async function (keyword) {
            try {
                const res_account = await this.$axios({
                    method: 'get', url: this.$to("/accounts"),
                    params: Req.AccountKeyWordRequest.of(keyword).param,
                });
                
                return Res.AccountKeywordResponse.of(res_account).content;

            } catch(res) {
                const error = Res.ErrResponse.of(res);
                console.log(error);

                return [];
            }
        },
        fetchHashtag: async function (keyword) {
            try {
                const res_hashtag = await this.$axios({
                    method: 'get', url: this.$to("/hashtag"),
                    params: Req.HashtagKeyWordRequest.of(keyword).param,
                });
                
                return Res.HashtagKeywordResponse.of(res_hashtag).content;

            } catch(res) {
                const error = Res.ErrResponse.of(res);
                console.log(error);

                return [];
            }
        },
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
