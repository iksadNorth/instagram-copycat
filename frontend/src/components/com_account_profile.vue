<template>
    <v-row>
        <v-col cols="4">
            <v-avatar 
                color="red" :size="size" variant="tonal"
                class="mr-4 clickable"
                @click="onClickProfile"
            >{{ mkAvatar(user.name) }}</v-avatar>
        </v-col>
        <v-col class="ygaps">
            <v-row class="xgaps">
                <p
                    class="clickable title"
                ><strong>{{user.name}}</strong></p>
                <v-btn
                    flat density="compact"
                    @click="onClickFollow" class="btn--follow"
                ><strong>{{ btnFollow }}</strong></v-btn>
            </v-row>
            <v-row class="xgaps">
                <p
                    class="clickable metaInfo" @click="onClickArticles"
                >{{ label.articles.label }}&nbsp;<strong>{{ user.articles }}</strong></p>
                <p
                    class="clickable metaInfo" @click="onClickFollowers"
                >{{ label.followers.label }}&nbsp;<strong>{{ user.followers }}</strong></p>
                <p
                    class="clickable metaInfo" @click="onClickFollowings"
                >{{ label.followings.label }}&nbsp;<strong>{{ user.followings }}</strong></p>
            </v-row>
            <v-row class="intro">
                <p
                ><strong>{{ user.nickname }}</strong></p>
                <p
                >{{ user.introduction }}</p>
            </v-row>
        </v-col>
    </v-row>
</template>

<script>
import * as Res from "@/dto/Response";

export default {
    props: {
        uid: Number,
        size: {
            type: [Number, String],
            default: 125,
        }
    },
    data() {
        return {
            label: {
                articles: {label: "게시물"},
                followers: {label: "팔로워"},
                followings: {label: "팔로우"},
            },
            btn: {
                follow: {isFollowing: "팔로우 중", notFollowing: "팔로우", state: false},
            },
            user: {
                uid: 0,
                name: "",
                nickname: "",
                articles: 0,
                followers: 0,
                followings: 0,
                introduction: "",
            },
        }
    },
    computed: {
        btnFollow() {return this.btn.follow.state ? this.btn.follow.isFollowing : this.btn.follow.notFollowing},
    },
    methods: {
        mkAvatar(nickname) { return nickname.slice(0, 2).toUpperCase(); },
        filpFollow() {this.btn.follow.state = !this.btn.follow.state;},
        setFollow(flag) {this.btn.follow.state = flag;},

        fetchProfile() {
            // 기본 정보 fetch
            console.log("call fetchProfile")
            this.$axios({
                method: 'get', url: this.$to(`/accounts/${this.uid}`),
            }).then(res => {
                this.user = Object.assign(
                    this.user, 
                    Res.AccountReadByIdResponse.of(res).content
                    );
            }).catch(res => {
                const error = Res.ErrResponse.of(res);
                console.log(error);
            });
        },
        fetchFollow() {
            this.$axiosAuth({method: 'post', url: this.$to(`/accounts/follow/${this.uid}`),})
            .then(res => {
                console.log("팔로우 성공.");

                this.filpFollow();
                this.fetchProfile();
            }).catch(res => {
                    const error = Res.ErrResponse.of(res);
                    console.log(error);
            });
        },
        fetchUnFollow() {
            this.$axiosAuth({method: 'delete', url: this.$to(`/accounts/follow/${this.uid}`),})
            .then(res => {
                console.log("언팔로우 성공.");
                
                this.filpFollow();
                this.fetchProfile();
            }).catch(res => {
                    const error = Res.ErrResponse.of(res);
                    console.log(error);
            });
        },
        fetchIsFollowing() {
            this.$axiosAuth({method: 'get', url: this.$to(`/accounts/follow/${this.uid}`),})
            .then(res => {
                const isFollow = Res.FollowReadResponse.of(res).isFollow;
                this.setFollow(isFollow);
                console.log(`팔로우 중인가? ${isFollow}`);
            }).catch(res => {
                    const error = Res.ErrResponse.of(res);
                    console.log(error);
            });
        },

        onClickArticles() {console.log("Click onClickArticles")},
        onClickFollowers() {console.log("Click onClickFollowers")},
        onClickFollowings() {console.log("Click onClickFollowings")},

        onClickFollow() {
            console.log("Click onClickFollow");

            if(this.btn.follow.state) {
                this.fetchUnFollow();
            } else {
                this.fetchFollow();
            }
        },
    },
    mounted() {
        this.fetchProfile();
        this.fetchIsFollowing();
    },
}
</script>

<style scoped>
    .clickable {
        cursor: pointer;
    }
    .ygaps > * {
        margin-bottom: 2%;
        margin-top: 2%;
    }
    .xgaps > * {
        margin-right: 8%;
    }
    .title {
        font-size: larger;
    }
    .metaInfo {
        font-size: medium;
    }
    .btn--follow {
        background-color: dodgerblue;
        color: white;
        padding-left: 4%;
        padding-right: 4%;
    }
    .intro {
        display: block;
    }
</style>