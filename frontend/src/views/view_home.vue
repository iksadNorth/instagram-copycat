<template>
<v-container><v-row id="body">
    <!-- 좌측 열 -->
    <v-col id="left--sector" 
    cols="5"
    ><v-row>
        <v-img
        src="@/assets/homepageLeft.png"
        ></v-img>
    </v-row></v-col>

    <!-- 우측 열 -->
    <v-col id="right--sector"
    cols="5"
    >
        <v-row class="right--rows"><com-login class="w-100"/></v-row>
        <v-row class="right--rows"><com-join class="w-100"/></v-row>
        <v-row class="right--rows"><com-app class="w-100"/></v-row>
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

<style scoped>
    #left--sector {
        background-color: beige;
    }
    #right--sector {
        background-color: rgb(156, 203, 153);
    }
    #body {
        background-color: blueviolet;
    }
    .right--rows {
        background-color: aqua;
        
        margin: 1px;
    }
    .v-row {
        justify-content: center;
        align-items: center;
    }
    .v-col {
        padding: 30px;

        justify-content: center;
        align-items: center;
    }
</style>
