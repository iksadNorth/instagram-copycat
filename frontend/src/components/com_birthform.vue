<template>
    <v-card
    variant="outlined" rounded
    ><v-contain>
        <!-- 1 -->
        <v-row justify="center">
            <v-img
            max-width="150"
            src="@/assets/birthday-icon.png"
            ></v-img>
        </v-row>
        <v-spacer class="my-n8"/>
        <!-- 2 -->
        <v-row justify="center">
            <p><strong>
                {{ label.addBirthday.label }}
            </strong></p>
        </v-row>
        <!-- 3 -->
        <v-row justify="center">
            <p><strong>
                {{ label.notIncludeNotification.label }}
            </strong></p>
        </v-row>
        <!-- 4 -->
        <v-row>
            <v-select
            v-model="birth.month.selected"
            :items="birth.month.options"
            item-title="key"
            item-value="value"
            variant="outlined"
            return-object
            ></v-select>
            <v-select
            v-model="birth.day.selected"
            :items="birth.day.options"
            item-title="key"
            item-value="value"
            variant="outlined"
            return-object
            ></v-select>
            <v-select
            v-model="birth.year.selected"
            :items="birth.year.options"
            item-title="key"
            item-value="value"
            variant="outlined"
            return-object
            ></v-select>
        </v-row>
        <!-- 5 -->
        <v-row justify="center">
            <p>
                {{ label.mustFillBirth.label }}
            </p>
        </v-row>
        <!-- 6 -->
        <v-row justify="center">
            <p>
                {{ label.additionalExplanation.label }}
            </p>
        </v-row>
        <!-- 7 -->
        <v-row>
            <v-btn
            class="w-100" color="primary"
            @click="onClickNext"
            >{{ btn.next.label }}</v-btn>
        </v-row>
        <!-- 8 -->
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
                id: {label: "전화번호, 사용자 이름 또는 이메일", value: undefined},
                pw: {label: "비밀번호", value: undefined},
            },
            btn: {
                next: {label: "다음"},
            },
            label: {
                or: {label: "또는"},
                addBirthday: {label: "생일 추가"},
                notIncludeNotification: {label: "공개 프로필에 포함되지 않습니다."},
                mustFillBirth: {label: "태어난 날짜를 입력해야 합니다."},
                additionalExplanation: {label: "비즈니스나 반려동물 등을 위한 게정인 경우에도 회원님의 생일정보를 사용하세요."},
                backward: {label: "돌아가기"},
            },
            birth: {
                month: {
                    selected: {key: '1월', value: 1},
                    options: [
                        {key: '1월', value: 1},
                        {key: '2월', value: 2},
                        {key: '3월', value: 3},
                        {key: '4월', value: 4},
                        {key: '5월', value: 5},
                        {key: '6월', value: 6},
                        {key: '7월', value: 7},
                        {key: '8월', value: 8},
                        {key: '9월', value: 9},
                        {key: '10월', value: 10},
                        {key: '11월', value: 11},
                        {key: '12월', value: 12},
                    ]
                },
                day: {
                    selected: {key: '1', value: 1},
                    options: this.makeDayList(1)
                },
                year: {
                    selected: {key: '1950', value: 1950},
                    options: this.makeYearList()
                },
            },
            
        }
    },
    methods: {
        onClickNext() {
            this.$store.commit("setScreenState", "vertificationform");
            console.log("Click onClickNext");
        },
        onClickBackward() {
            this.$store.commit("setScreenState", "joinform");
            console.log("Click onClickBackward");
        },
        makeDayList(month) {
            const thrityMonth = [2, 4, 6, 9, 11];
            let result = [];
            if (thrityMonth.includes(month)) {
                let items = [...Array(30).keys()].map(x => x+1);
                for (const item of items) {
                    result.push({key: item, value: item})
                }
            } else {
                let items = [...Array(31).keys()].map(x => x+1);
                for (const item of items) {
                    result.push({key: item, value: item})
                }
            }
            return result;
        },
        makeYearList() {
            let result = [];
            const year = (new Date()).getFullYear();
            for (let item = year; item >= 1950; item--) {
                result.push({key: item, value: item})
            }
            return result;
        },
    },
    computed: {
        birthMonthSelected() {return this.birth.month.selected;}
    },
    watch: {
        birthMonthSelected() {
            this.birth.day.options = this.makeDayList(this.birth.month.selected.value);
        }
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
