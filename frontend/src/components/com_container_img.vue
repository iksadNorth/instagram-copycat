<template>
    <v-container>
        <v-row
            v-for="i in range(quotient + 1)" :key="i"
        >
        <v-col
            v-for="j in cntCol(i)" :key="j"
        >
            <v-card v-if="len > j">
                <com-img-info 
                class="square" 
                :data="album[j]"
                />
            </v-card>
        </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    props: {
        album: Array,
        // album: [
        //     {
        //         pid: 90384,
        //         imgSrc: "https://img.freepik.com/premium-photo/abstract-blue-background-with-smooth-lines_476363-5999.jpg?w=900",
        //         likes: 42,
        //         comments: 21,
        //     }, ... 
        // ]
    },
    data() {
        return {
            cfg: {
                num_row: 3,
            },
        }
    }, 
    methods: {
        range(val) {
            let result = [];
            for (let i=0; i<val; i++) {
                result.push(i);
            }
            return result;
        },
        cntCol(row) {
            let result = [];
            for (let i=0; i<this.cfg.num_row; i++) {
                result.push(i + this.cfg.num_row * row);
            }
            return result;
        },

    },
    computed: {
        len() {return this.album.length;},
        quotient() {return parseInt(this.album.length / 3);},
        remainder() {return this.album.length % 3;},
    },
}
</script>

<style scoped>
    .v-container {
        /* background-color: red; */
    }
    .v-row {
        /* background-color: green; */
    }
    .v-col {
        /* background-color: blue; */
    }
    .v-card {
        /* background-color: grey; */
    }
    .square {
        width: 100%
    }
    .square:after {
        content: "";
        display: block;
        padding-bottom: 100%;
    }
</style>