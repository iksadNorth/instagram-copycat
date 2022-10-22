<template>
    <div>
        <v-hover v-slot="{ isHovering, props }">
            <img
                v-bind="props" class="fill" 
                :src="imgSrc"
            />
            <div
                v-bind="props" class="fill transparent center clickable"
                @click="onClickPost"
                v-if="isHovering"
            >
                <p class="gap"><v-icon>mdi-heart</v-icon>&nbsp;{{ likes }}</p>
                <p class="gap"><v-icon>mdi-comment</v-icon>&nbsp;{{ comments }}</p>
            </div>
        </v-hover>
    </div>
</template>

<script>
export default {
    props: {
        data: Object,
        // data: {
        //     pid: 90384,
        //     imgSrc: "https://img.freepik.com/premium-photo/abstract-blue-background-with-smooth-lines_476363-5999.jpg?w=900",
        //     likes: 42,
        //     comments: 21,
        // },
    },
    methods: {
        onClickPost() {
            console.log("Click onClickPost");
            this.$router.push(`/p/${this.pid}`)
        },
    },
    computed: {
        pid() {return this.data.pid},
        imgSrc() {
            if(this.data.imgSrc) {
                return this.$staticResource(this.data.imgSrc)
            } else {
                return "https://t4.ftcdn.net/jpg/04/70/29/97/360_F_470299797_UD0eoVMMSUbHCcNJCdv2t8B2g1GVqYgs.jpg"
            }
        },
        likes() {return this.data.likes || 0},
        comments() {return this.data.comments || 0},
    },
}
</script>

<style scoped>
    .fill {
        position: absolute;
        height: 100%;
        width: 100%;
    }
    .transparent {
        background-color: black;
        color: white;
        opacity: 0.3;
    }
    .center {
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .gap {
        margin-left : 5%;
        margin-right: 5%;
    }
    .clickable {
        cursor: pointer;
    }
</style>