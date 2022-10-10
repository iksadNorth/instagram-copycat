<template>
    <v-card
    variant="outlined" rounded
    ><v-contain>
        <!-- 1 -->
        <v-row justify="center">
            <v-img
            width="200"
            src="@/assets/instagramLogo.png"
            ></v-img>
        </v-row>
        <v-spacer class="my-8"/>
        <!-- 2 -->
        <v-row justify="center">
            <p class="text-center"><strong>
                {{ label.joinExplanation.label }}
            </strong></p>
        </v-row>
        <!-- 3 -->
        <v-row>
            <v-btn
            class="w-100" color="primary"
            @click="onClickLoginWithFacebook"
            ><v-icon>mdi-facebook</v-icon>&nbsp;{{ btn.loginWithFacebook.label }}</v-btn>
        </v-row>
        <!-- 4 -->
        <v-row align="center">
            <v-col>
                <v-divider thickness="2"></v-divider>
            </v-col>
            <v-col cols="4">
                <p class="text-center"><strong>{{ label.or.label }}</strong></p>
            </v-col>
            <v-col>
                <v-divider thickness="2"></v-divider>
            </v-col>
        </v-row>
        <!-- 5 -->
        <v-row>
            <v-text-field
            variant="outlined" clearable density="compact"
            :label="inputs.email.label"
            v-model="inputs.email.value"
            ></v-text-field>
        </v-row>
        <!-- 6 -->
        <v-row>
            <v-text-field
            variant="outlined" clearable density="compact"
            :label="inputs.name.label"
            v-model="inputs.name.value"
            ></v-text-field>
        </v-row>
        <!-- 7 -->
        <v-row>
            <v-text-field
            variant="outlined" clearable density="compact"
            :label="inputs.nickname.label"
            v-model="inputs.nickname.value"
            ></v-text-field>
        </v-row>
        <!-- 8 -->
        <v-row>
            <v-text-field
            variant="outlined" clearable density="compact" type="password"
            :label="inputs.pw.label"
            v-model="inputs.pw.value"
            ></v-text-field>
        </v-row>
        <!-- 9 -->
        <v-row>
            <p class="text-center">
                {{ label.explanation.label }}
            </p>
        </v-row>
        <!-- 10 -->
        <v-row>
            <v-btn
            class="w-100" color="primary"
            @click="onClickNext"
            >{{ btn.next.label }}</v-btn>
        </v-row>
    </v-contain></v-card>
</template>

<script>
export default {
    data() {
        return {
            inputs: {
                email: {label: "휴대폰 번호 또는 이메일", value: undefined},
                name: {label: "성명", value: undefined},
                nickname: {label: "사용자 이름", value: undefined},
                pw: {label: "비밀번호", value: undefined},
            },
            btn: {
                loginWithFacebook: {label: "Facebook으로 로그인"},
                next: {label: "가입"},
            },
            label: {
                joinExplanation: {label: "친구들의 사진과 동영상을 보려면 가입하세요."},
                or: {label: "또는"},
                explanation: {label: "저희 서비스를 이용하는 사람이 회원님의 연락처 정보를 Instagram에 업로드했을 수도 있습니다."},
            },
        }
    },
    methods: {
        onClickFacebookLogin() {
            console.log("Click onClickFacebookLogin");
        },
        onClickNext() {
            this.$store.commit("setScreenState", "birthform");
            console.log("Click onClickNext");
        },
    },
}
</script>

<style scoped>
    .v-card {
        padding: 30px;
    }
    .v-container {
        background-color: black;
    }
    .v-row {
        margin: 3px;
    }
    #sector--facebookLogin {
        color: rgb(107, 107, 244);
        cursor: pointer;
    }
    #sector--findPassword {
        cursor: pointer;
    }
</style>
