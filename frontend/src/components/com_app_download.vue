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
            window.open("https://itunes.apple.com/app/instagram/id389801252?pt=428156&ct=igweb.loginPage.badge&mt=8&vt=lo");
        },
        onClickGooglestore() {
            console.log("Click onClickGooglestore");
            window.open("https://play.google.com/store/apps/details?id=com.instagram.android&referrer=utm_source%3Dinstagramweb%26utm_campaign%3DloginPage%26ig_mid%3D9E86B717-457B-47AE-942C-394B923A7049%26utm_content%3Dlo%26utm_medium%3Dbadge");
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
