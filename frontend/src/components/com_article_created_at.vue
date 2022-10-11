<template>
    <div class="d-flex flex-row align-center my-3">
        <p class="date">{{ data.createdAt }}</p>
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
}
</script>

<style scoped>
    .date {
        color: grey;
        font-size: 13px;
    }
</style>