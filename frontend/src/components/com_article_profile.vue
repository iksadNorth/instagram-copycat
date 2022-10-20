<template>
    <div class="d-flex flex-row align-center">
        <v-avatar 
            color="red" :size="size" variant="tonal"
            class="mr-4 clickable"
            @click="onClickProfile()"
        >{{ mkAvatar }}</v-avatar>
        <p
        @click="onClickProfile" class="clickable"
        ><strong>{{ nameProfile }}</strong></p>
    </div> 
</template>

<script>
export default {
    props: {
        data: Object,
        // data: {
        //         uid: 1231,
        //         writer: "kakao_career",
                // or
        //         tag: "#tag1"
        //     },
        size: {
            type: [Number, String],
            default: 36,
        },
        kind: {
            type: [String],
            default: "account",
            // default: "hashtag",
        },
    },
    methods: {
        onClickProfile() {
            if (this.kind.toLowerCase() == "account") {
                this.onClickProfileAccount();
            } else if (this.kind.toLowerCase() == "hashtag") {
                this.onClickProfileHashtag();
            }
        },
        onClickProfileAccount() {
            console.log("Click onClickProfile" + `uid: ${this.data.uid}`);
            
            this.$router.push(`/${this.data.uid}`);
        },
        onClickProfileHashtag() {
            console.log("Click onClickProfileHashtag" + `tag: ${this.data.tag}`);
            
            this.$router.push(`/explore/tags/${this.data.tag}`);
        },
    },
    computed: {
        nameProfile() {
            if(this.kind.toLowerCase() == "account") {
                return this.data.writer;
            } else if(this.kind.toLowerCase() == "hashtag") {
                return this.data.tag;
            }
        },
        mkAvatar() { 
            if(this.kind.toLowerCase() == "account") {
                return this.data.writer.slice(0, 2).toUpperCase();
            } else if(this.kind.toLowerCase() == "hashtag") {
                return `#${this.data.tag}`.slice(0, 2).toUpperCase();
            }
         },
    }
}
</script>

<style scoped>
    .clickable {
        cursor: pointer;
    }
</style>