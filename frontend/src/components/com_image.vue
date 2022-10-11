<template>
    <div>
        <v-img
        min-height="300"
        :src="require(`@/assets/${data.imgSrc}`)"
        ></v-img>
    </div>
</template>

<script>
export default {
    props: {
        data: Object,
        // data: {
        //     imgSrc: "article-img-example.jpg",
        // },
    },
}
</script>

<style>

</style>