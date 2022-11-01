<template>
    <div class="d-flex flex-row align-center">
        <p class="date">{{ createdAt }}</p>
    </div>
</template>

<script>
export default {
    props: {
        data: Object,
        // data: {
        //         createdAt: "8월 5",
        // },
    },
    computed: {
        createdAt() {return this.data.createdAt.split('T')[0]}
    },
}
</script>

<style scoped>
    .date {
        color: grey;
        font-size: 13px;
    }
</style>