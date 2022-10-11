<template>
    <v-container>
        <v-row>
            <com-container-img :album="album"/>
        </v-row>
    </v-container>
</template>

<script>
export default {
    data() {
        return {
            album: [
                {
                    pid: 90384,
                    imgSrc: "https://img.freepik.com/premium-photo/abstract-blue-background-with-smooth-lines_476363-5999.jpg?w=900",
                    likes: 42,
                    comments: 21,
                },
                {
                    pid: 66367,
                    imgSrc: "https://img.freepik.com/premium-photo/city-landscape-with-group-building-sky-sunlight_254791-864.jpg?w=900",
                    likes: 15,
                    comments: 14,
                },
                {
                    pid: 8734,
                    imgSrc: "https://img.freepik.com/premium-photo/background-empty-red-dark-podium-with-lights-tile-floor-3d-rendering_314485-400.jpg?w=900",
                    likes: 72,
                    comments: 16,
                },
                {
                    pid: 34987,
                    imgSrc: "https://img.freepik.com/free-photo/water-texture-background-pastel-blue-design_53876-146663.jpg?w=900&t=st=1665476193~exp=1665476793~hmac=42a4b7f81eee85146011a5ca0d710b4c063080bc440e7f2c3ec9ecf205f4ff83",
                    likes: 36,
                    comments: 8,
                },
            ],
        }
    },
}
</script>

<style>

</style>