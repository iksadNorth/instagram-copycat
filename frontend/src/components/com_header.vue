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
                <v-text-field
                    id="search-activator"
                    density="compact" variant="outlined" clearable hide-details="auto" class="search" bg-color="#F0F0F0"
                    :prepend-inner-icon="inputs.search.icon"
                    @click:prepend-inner="doSearch(inputs.search.value)"
                    @keyup.enter="doSearch(inputs.search.value)"
                    v-model="inputs.search.value"
                />
                <!-- 포커싱 되었을 때 띄울 창 -->
                <v-menu activator="#search-activator">
                    <v-card class="ygaps px-5 window">
                        <com-profile 
                            v-for="profile of profilesSearched" :key="profile" 
                            :data="profile" 
                        />
                    </v-card>
                </v-menu>
            </v-col>
            <v-col>
                <!-- 단추들 -->
                <div class="xgaps tools vCenter">
                    <v-icon id="Home-activator" @click="onClickHome" >mdi-home</v-icon>
                    <v-icon id="Posting-activator" @click="onClickPosting" >mdi-plus-circle-outline</v-icon>
                    <v-icon id="Explore-activator" @click="onClickExplore" >mdi-compass</v-icon>
                    <v-icon id="Alarm-activator" @click="onClickAlarm" >mdi-heart</v-icon>
                </div>
                
                <!-- overlay 창들 -->
                <div>
                    <!-- plus 클릭 시 -->
                    <v-dialog activator="#Posting-activator" v-model="state.dialog.value">
                        <!-- 이미지 등록 -->
                        <v-card 
                            class="dialog overflow-y-hidden" rounded="xl"
                            v-if="state.dialog.stage.toLowerCase() == 'setimage'"
                        >
                            <v-container>
                                <v-row class="justify-center align-center mb-2">
                                    <p
                                    ><strong>{{ label.newPost.label }}</strong></p>
                                    <v-divider class="my-2"/>
                                </v-row>
                                <v-row class="flex-column justify-center align-center selectWindow">
                                    <v-icon size="100">mdi-image-outline</v-icon>
                                    <p><strong>{{ label.description.label }}</strong></p>
                                    <v-btn color="primary">
                                        <label for="ex_file" class="clickable">
                                            {{ btn.selectInLocal.label }}
                                        </label>
                                    </v-btn>
                                    <v-file-input id="ex_file" class="invisible" v-model="inputs.file.value" />
                                </v-row>
                            </v-container>
                        </v-card>
                        <!-- 게시글 작성 -->
                        <v-card 
                            class="dialog overflow-y-hidden" rounded="xl"
                            v-if="state.dialog.stage.toLowerCase() == 'setcontent'"
                        >
                            <v-container>
                                <v-row class="justify-center align-center mb-2">
                                    <v-col></v-col>
                                    <v-col>
                                        <p class="text-center"
                                        ><strong>{{ label.newPost.label }}</strong></p>
                                    </v-col>
                                    <v-col>
                                        <p
                                            class="text-right mx-2 clickable text-grey"
                                            @click="onClickMakePost"
                                        ><strong>{{ btn.posting.label }}</strong></p>
                                    </v-col>
                                </v-row>
                                <v-divider class="my-2"/>
                                <v-row align="center">
                                    <v-col class="hCenter">
                                        <!-- 사진 -->
                                        <img class="image" :src="inputs.file.src" >
                                    </v-col>
                                    <v-divider class="mx-2" vertical />
                                    <v-col cols="auto">
                                        <v-row class="flex-column py-2 stretch" >
                                            <!-- 게시물 입력 -->
                                            <p><strong>{{ label.descContent.label }}</strong></p>
                                            <v-textarea 
                                                no-resize hide-details
                                                :placeholder="inputs.content.label" variant="underlined"
                                                v-model="inputs.content.value"
                                            ></v-textarea>
                                        </v-row>
                                        <v-row class="flex-column py-2 up">
                                            <!-- 설정 하기 -->
                                            <p><strong>{{ label.descCfg.label }}</strong></p>
                                            <v-switch
                                                v-model="inputs.toggleHide.value"
                                                :label="inputs.toggleHide.label" color="primary"
                                                hide-details inset
                                            ></v-switch>
                                            <v-switch
                                                v-model="inputs.toggleComment.value"
                                                :label="inputs.toggleComment.label" color="primary"
                                                hide-details inset
                                            ></v-switch>
                                        </v-row>
                                    </v-col>
                                </v-row>
                                <v-divider class="mt-5"/>
                            </v-container>
                        </v-card>
                    </v-dialog>

                    <!-- 하트 클릭 시 -->
                    <v-menu activator="#Alarm-activator">
                        <v-card class="ygaps px-5 window">
                            <com-profile 
                                v-for="profile of profilesSearched" :key="profile" 
                                :data="profile" 
                            />
                        </v-card>
                    </v-menu>
                </div>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    data() {
        return {
            inputs: {
                search: {icon: "mdi-magnify", value: undefined},
                file: {value: undefined, src: undefined},

                content: {label: "문구 입력...", value: undefined},

                toggleHide: {label: "이 게시물의 좋아요 수 및 조회수 숨기기", value: false},
                toggleComment: {label: "댓글 기능 해제", value: false},
            },
            label: {
                newPost: {label: "새 게시물 만들기"},
                description: {label: "사진 선택"},

                descContent: {label: "게시글 작성"},
                descCfg: {label: "고급 설정"},
            },
            btn: {
                selectInLocal: {label: "컴퓨터에서 선택"},

                posting: {label: "게시하기"},
            },
            state: {
                dialog: {
                    stage: "setImage",
                    // stage: "setContent",
                    value: false,
                },
            },
            profilesSearched: [
                { writer: "kakao_career", },
                { writer: "kakao_career", },
                { writer: "kakao_career", },
                { writer: "kakao_career", },
                { writer: "kakao_career", },
                { writer: "kakao_career", },
                { writer: "kakao_career", },
                { writer: "kakao_career", },
            ],
        }
    },
    methods: {
        doSearch(keyword) {console.log("Click doSearch. keyword: " + keyword);},
        doPreSearch(keyword) {console.log("Click doPreSearch. keyword: " + keyword);},

        onClickHome() {
            console.log("Click onClickHome");
            this.$router.push("/");
        },
        onClickPosting() {console.log("Click onClickPosting");},
        onClickExplore() {
            console.log("Click onClickExplore");
            this.$router.push("/explore");
        },
        onClickAlarm() {console.log("Click onClickAlarm");},

        handleFile(file) {
            console.log("Click handleFile");
            if(file == null) { return; }
            this.inputs.file.src = URL.createObjectURL(file[0]);
            if(file != null) { this.state.dialog.stage = "setContent"; }
        },
        deleteFile(newVal) {
            console.log("Click handleFile");
            if(!newVal) { 
                this.inputs.file.value = undefined;
                this.inputs.file.src = undefined;
                this.state.dialog.stage = "setImage";
            }
        },

        onClickMakePost() {console.log("Click onClickMakePost");},
    },
    computed: {
        search() { return this.inputs.search.value; },
        file() { return this.inputs.file.value; },
        isOpenDialog() { return this.state.dialog.value; },
    },
    watch: {
        search(newVal) { this.doPreSearch(newVal); },
        file(newVal) { this.handleFile(newVal); },
        isOpenDialog(newVal) { this.deleteFile(newVal); },
    },
}
</script>

<style scoped>
    .xgaps > * {
        margin-right: 5%;
    }
    .ygaps > * {
        margin-top: 5%;
        margin-bottom: 5%;
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
    .hCenter {
        display: flex;
        justify-content: center;
    }
    .search {
        padding-left: 7%;
        padding-right: 7%;
    }
    .window {
        min-height: 15vh;
        max-height: 30vh;
    }
    .dialog {
        margin: auto;

        height: 80vh;
        width: 60vw;
    }
    .selectWindow {
        padding-top: 18%;
        padding-bottom: 18%;
    }
    .selectWindow > * {
        margin-top: 1%;
        margin-bottom: 1%;
    }
    .invisible {
        visibility: hidden;
    }
    .clickable {
        cursor: pointer;
    }
    .stretch {
        height: 75vh;
    }
    .up {
        margin-top: -25vh;
    }
    .image {
        max-height: 60vh;
        max-width: 100%;
        height: 100%;
        display: block;
        border: 1px solid;
    }
</style>
