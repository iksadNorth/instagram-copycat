<template>  
    <p
        class="follow clickable"
        @click="onClickFollow(data.uid)"
    ><strong>{{ labelFollow }}</strong></p>
</template>

<script>
import * as Res from "@/dto/Response";

export default {
    props: {
        data: Object,
        // data: {
        //         uid: 16274,
        // },
    },
    data() {
        return {
            label: {
                follow: {isFollowing: "팔로우 중", notFollowing: "팔로우", state: false},
            },
        }
    },
    computed: {
        labelFollow() {return this.label.follow.state ? this.label.follow.isFollowing : this.label.follow.notFollowing},
    },
    methods: {
        setFollow(flag) {this.label.follow.state = flag;},
        filpFollow() {this.label.follow.state = !this.label.follow.state;},

        fetchFollow() {
            this.$axiosAuth({method: 'post', url: this.$to(`/accounts/follow/${this.data.uid}`),})
            .then(res => {
                console.log("팔로우 성공.");

                this.filpFollow();
            }).catch(res => {
                    const error = Res.ErrResponse.of(res);
                    console.log(error);
            });
        },
        fetchUnFollow() {
            this.$axiosAuth({method: 'delete', url: this.$to(`/accounts/follow/${this.data.uid}`),})
            .then(res => {
                console.log("언팔로우 성공.");
                
                this.filpFollow();
            }).catch(res => {
                    const error = Res.ErrResponse.of(res);
                    console.log(error);
            });
        },
        fetchIsFollowing() {
            this.$axiosAuth({method: 'get', url: this.$to(`/accounts/follow/${this.data.uid}`),})
            .then(res => {
                const isFollow = Res.FollowReadResponse.of(res).isFollow;
                this.setFollow(isFollow);
                console.log(`팔로우 중인가? ${isFollow}`);
            }).catch(res => {
                    const error = Res.ErrResponse.of(res);
                    console.log(error);
            });
        },

        onClickFollow(uid) {
            console.log("Click onClickFollow. id: " + uid);

            if(this.label.follow.state) {
                this.fetchUnFollow();
            } else {
                this.fetchFollow();
            }
        }
    },
    mounted() {
        this.fetchIsFollowing();
    },
}
</script>

<style scoped>
    .follow {
        color: aqua;
    }
    .clickable {
        cursor: pointer;
    }
</style>