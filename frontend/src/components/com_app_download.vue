<template>
    <v-container>
        <v-row justify="center">
            <strong>{{ label.downloadApp.label }}</strong>
        </v-row>
        <v-row>
            <v-col>
                <v-img
                src="@/assets/downloadWithAppstore.png"
                @click="onClickAppstore"
                ></v-img>
            </v-col>
            <v-col>
                <v-img
                src="@/assets/downloadWithGooglestore.png"
                @click="onClickGooglestore"
                ></v-img>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    data() {
        return {
            label: {
                downloadApp: {label: "앱을 다운로드하세요."},
            },
        }
    },
    methods: {
        onClickAppstore() {
            console.log("Click onClickAppstore");
        },
        onClickGooglestore() {
            console.log("Click onClickGooglestore");
        },
    },
    watch: {
        
    },
}
</script>

<style scoped>
    .v-row {
        margin: 1px;
    }
    .v-img {
        cursor: pointer;
    }
</style>
