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
        <v-row>
            <v-text-field
            variant="outlined" clearable density="compact"
            :label="inputs.id.label"
            v-model="inputs.id.value"
            ></v-text-field>
        </v-row>
        <!-- 3 -->
        <v-row>
            <v-text-field
            variant="outlined" clearable density="compact" type="password"
            :label="inputs.pw.label"
            v-model="inputs.pw.value"
            ></v-text-field>
        </v-row>
        <!-- 4 -->
        <v-row>
            <v-btn
            class="w-100" color="primary"
            @click="onClickLogin"
            >{{ btn.login.label }}</v-btn>
        </v-row>
        <!-- 5 -->
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
        <!-- 6 -->
        <v-row justify="center">
            <p
            id="sector--facebookLogin"
            @click="onClickFacebookLogin"
            ><strong>
                <v-icon class="mr-2">mdi-facebook</v-icon>{{ label.faceBookLogin.label }}
            </strong></p>
        </v-row>
        <!-- 7 -->
        <v-row justify="center">
            <p
            id="sector--findPassword"
            @click="onClickFindPassword"
            ><strong>{{ label.findPassword.label }}</strong></p>
        </v-row>
    </v-contain></v-card>
</template>

<script>
export default {
    data() {
        return {
            inputs: {
                id: {label: "전화번호, 사용자 이름 또는 이메일", value: undefined},
                pw: {label: "비밀번호", value: undefined},
            },
            btn: {
                login: {label: "로그인"},
            },
            label: {
                or: {label: "또는"},
                faceBookLogin: {label: "Facebook으로 로그인"},
                findPassword: {label: "비밀번호를 잊으셨나요?"},
            },
        }
    },
    methods: {
        onClickLogin() {
            console.log("Click onClickLogin");
        },
        onClickFacebookLogin() {
            console.log("Click onClickFacebookLogin");
        },
        onClickFindPassword() {
            console.log("Click onClickFindPassword");
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
        margin: 10px;
    }
    #sector--facebookLogin {
        color: rgb(107, 107, 244);
        cursor: pointer;
    }
    #sector--findPassword {
        cursor: pointer;
    }
</style>
