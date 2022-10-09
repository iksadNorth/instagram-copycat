<template>
<v-container><v-row justify="center">
    <!-- 좌측 열 -->
    <v-col id="left--sector" 
    justify="center"
    cols="3"
    ><v-row justify="center">
        <v-img
        src="@/assets/homepageLeft.png"
        ></v-img>
    </v-row></v-col>
    <!-- 우측 열 -->
    <v-col id="right--sector"
    cols="3"
    >

    </v-col>
</v-row></v-container>
</template>

<script>
export default {
    data() {
        return {
            
        }
    },
    methods: {

    },
    watch: {
        
    },
}
</script>

<style>
    #left--sector {
        background-color: beige;
    }
    #right--sector {
        background-color: rgb(156, 203, 153);
    }
    .v-col {
        padding: 30px;
    }
</style>
