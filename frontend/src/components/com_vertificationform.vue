<template>
    <v-card
    variant="outlined" rounded
    ><v-contain>
        <!-- 1 -->
        <v-row justify="center">
            <v-img
            max-width="150"
            src="@/assets/letter-icon.png"
            ></v-img>
        </v-row>
        <v-spacer class="my-n8"/>
        <!-- 2 -->
        <v-row justify="center">
            <p><strong>
                {{ label.insertVertificationCode.label }}
            </strong></p>
        </v-row>
        <!-- 3 -->
        <v-row justify="center">
            <p class="text-center"><strong>
                {{ label.sendCode.label }}
            </strong></p>
        </v-row>
        <!-- 4 -->
        <v-row>
            <v-text-field
            variant="outlined" clearable density="compact"
            :label="inputs.code.label"
            v-model="inputs.code.value"
            ></v-text-field>
        </v-row>
        <!-- 4 -->
        <v-row>
            <v-btn
            class="w-100" color="primary"
            @click="onClickNext"
            >{{ btn.next.label }}</v-btn>
        </v-row>
        <!-- 5 -->
        <v-row justify="center">
            <p
            id="label--backward"
            @click="onClickBackward"
            ><strong>
                {{ label.backward.label }}
            </strong></p>
        </v-row>
    </v-contain></v-card>
</template>

<script>
export default {
    data() {
        return {
            inputs: {
                code: {label: "인증 코드", value: undefined},
            },
            btn: {
                next: {label: "다음"},
            },
            label: {
                insertVertificationCode: {label: "인증 코드 입력"},
                sendCode: {label: "해당 이메일로 전송된 인증 코드를 입력하세요."},
                backward: {label: "돌아가기"},
            },
        }
    },
    methods: {
        onClickNext() {
            this.$store.commit("setScreenState", "termform");
            console.log("Click onClickNext");
        },
        onClickBackward() {
            this.$store.commit("setScreenState", "birthform");
            console.log("Click onClickBackward");
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
    #label--backward {
        color: dodgerblue;
        cursor: pointer;
    }
</style>
