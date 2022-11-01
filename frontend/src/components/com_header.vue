<template>
    <v-container>
        <v-row align="center" justify="center">
            <v-col>
                <!-- 로고 -->
                <v-img 
                class="py-4 clickable" @click="onClickHome"
                src="@/assets/instagramLogo.png" 
                />
            </v-col>
            <v-col class="vCenter">
                <!-- 검색창 -->
                <com-search />
            </v-col>
            <v-col>
                <!-- 단추들 -->
                <div class="xgaps tools vCenter">
                    <v-icon id="Home-activator" @click="onClickHome" >mdi-home</v-icon>
                    <v-icon id="Posting-activator" @click="onClickPosting" >mdi-plus-circle-outline</v-icon>
                    <v-icon id="Explore-activator" @click="onClickExplore" >mdi-compass</v-icon>
                    <v-icon id="Logout-activator" @click="onClickLogout" >mdi-logout</v-icon>
                </div>
                
                <!-- overlay 창들 -->
                <div>
                    <!-- plus 클릭 시 -->
                    <v-dialog activator="#Posting-activator" v-model="dialogFlag">
                        <!-- 게시글 창 -->
                        <com-posting isOpenDialog="dialogFlag" />
                    </v-dialog>
                </div>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    data() {
        return {
            state: {
                dialog: {
                    value: false,
                },
            },
        }
    },
    methods: {
        onClickHome() {
            console.log("Click onClickHome");
            this.$router.push("/");
        },
        onClickPosting() {console.log("Click onClickPosting");},
        onClickExplore() {
            console.log("Click onClickExplore");
            this.$router.push("/explore");
        },
        onClickLogout() {
            console.log("Click onClickLogout");
            this.$router.push("/");
            this.$store.commit('logoutAccount');
        },
    },
    computed: {
        dialogFlag: {
            get() {
                return this.$store.state.dialog.value;
            },
            set(val) {
                this.$store.commit('setDialog', val);
            },
        }
    },
}
</script>

<style scoped>
    .xgaps > * {
        margin-right: 5%;
    }
    .tools > * {
        cursor: pointer;
    }
    .v-img {
        width: 50%;
    }
    .vCenter {
        display: flex;
        align-items: center;
    }
    .clickable {
        cursor: pointer;
    }
</style>
