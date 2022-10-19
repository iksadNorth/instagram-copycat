<template>
    <v-card
    variant="outlined" rounded
    ><v-contain>
        <!-- 1 -->
        <v-row justify="center">
            <p><strong>
                {{ label.agreeWithTerm.label }}
            </strong></p>
        </v-row>
        <!-- 2 -->
        <v-row justify="center">
            <p class="text-center">
                {{ label.agreeWithTermExplanation.label }}
            </p>
        </v-row>
        <!-- 3 -->
        <v-row>
            <v-checkbox
            density class="mb-n6"
            v-model="checkbox.agreeWithAll.value"
            :label="checkbox.agreeWithAll.label"
            />
        </v-row>
        <!-- 4 -->
        <v-row>
            <v-divider></v-divider>
        </v-row>
        <!-- 5 -->
        <v-row>
            <v-checkbox
            density class="mb-n6"
            v-model="checkbox.agreeWithUseTerm.value"
            :label="checkbox.agreeWithUseTerm.label"
            />
        </v-row>
        <!-- 6 -->
        <v-row>
            <v-checkbox
            density class="mb-n6"
            v-model="checkbox.agreeWithDataTerm.value"
            :label="checkbox.agreeWithDataTerm.label"
            />
        </v-row>
        <!-- 7 -->
        <v-row>
            <v-checkbox
            density class="mb-n6"
            v-model="checkbox.agreeWithLocationTerm.value"
            :label="checkbox.agreeWithLocationTerm.label"
            />
        </v-row>
        <!-- 8 -->
        <v-row>
            <v-btn
            class="w-100" color="primary"
            @click="onClickNext"
            >{{ btn.next.label }}</v-btn>
        </v-row>
        <!-- 9 -->
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
import * as Res from "@/dto/Response";

export default {
    data() {
        return {
            btn: {
                next: {label: "가입"},
            },
            checkbox: {
                agreeWithAll: {label: "이용약관 3개에 모두 동의", value: false},

                agreeWithUseTerm: {label: "이용 약관(필수)", value: false},
                agreeWithDataTerm: {label: "데이터 정책(필수)", value: false},
                agreeWithLocationTerm: {label: "위치 기반 기능(필수)", value: false},
            },
            label: {
                agreeWithTerm: {label: "이용 약관에 동의"},
                agreeWithTermExplanation: {label: "Instagram은 회원님의 개인 정보를 안전하게 보호합니다. 새 계정을 만들려면 모든 약관에 동의하세요."},
                sendCode: {label: "해당 이메일로 전송된 인증 코드를 입력하세요."},
                backward: {label: "돌아가기"},
            },
        }
    },
    methods: {
        onClickNext() {
            if (!this.funcIsAgreeWithAll()) {
                alert("모든 약관에 동의하셔야 합니다.");
                return ;
            }
            this.$axios({
                    method: 'post', url: this.$to("/accounts"),
                    data: this.$store.state.account4Creating.param,
                })
            .then(
                res => {
                    console.log("가입 성공.");
                    console.log(res);
                    
                    this.$router.push("/");
                    this.$store.commit("clearAccount4Creating");
                }
            )
            .catch(
                res => {
                    const error = Res.ErrResponse.of(res);
                    console.log(error);

                    const errorMessage = error.isIt("DUPLICATED_USER");
                    if (errorMessage) {alert(errorMessage);}
                }
            );

            this.$store.commit("setScreenState", "joinform");
            console.log("Click onClickNext");
        },
        onClickBackward() {
            this.$store.commit("setScreenState", "vertificationform");
            console.log("Click onClickBackward");
        },
        funcIsAgreeWithAll() {
            return this.checkbox.agreeWithUseTerm.value 
            && this.checkbox.agreeWithDataTerm.value 
            && this.checkbox.agreeWithLocationTerm.value
            ;
        }
    },
    computed: {
        isAgreeWithAll() {return this.checkbox.agreeWithAll.value;},
        isAgreeWithAllOption() {return this.funcIsAgreeWithAll();},
    },
    watch: {
        isAgreeWithAll(newVal) {
            if (newVal) {
                this.checkbox.agreeWithUseTerm.value = true;
                this.checkbox.agreeWithDataTerm.value = true;
                this.checkbox.agreeWithLocationTerm.value = true;
            }
        },
        isAgreeWithAllOption(newVal) {
            if (!newVal) {
                this.checkbox.agreeWithAll.value = false;
            }
        },
    }
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
