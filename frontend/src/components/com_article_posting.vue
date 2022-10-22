<template>
    <v-card 
        class="dialog overflow-y-hidden" rounded="xl"
        v-if="state.dialog.stage.toLowerCase() == 'setimage'"
    >
        <!-- 이미지 선택창 -->
        <v-container>
            <!-- 상단 창 이름 -->
            <v-row class="justify-center align-center mb-2">
                <p
                ><strong>{{ label.newPost.label }}</strong></p>
                <v-divider class="my-2"/>
            </v-row>
            <!-- 본문 내용 -->
            <v-row class="flex-column justify-center align-center selectWindow">
                <v-icon size="100">mdi-image-outline</v-icon>
                <p><strong>{{ label.description.label }}</strong></p>
                <v-btn color="primary">
                    <label for="ex_file" class="clickable">
                        {{ btn.selectInLocal.label }}
                    </label>
                </v-btn>
                <v-file-input id="ex_file" class="invisible" v-model="inputs.file.value" 
                    multiple accept="image/*" 
                />
            </v-row>
        </v-container>
    </v-card>
    <!-- 게시글 작성 -->
    <v-card 
        class="dialog overflow-y-hidden" rounded="xl"
        v-if="state.dialog.stage.toLowerCase() == 'setcontent'"
    >
        <!-- 게시글 작성창 -->
        <v-container>
            <v-row class="justify-center align-center mb-2">
                <!-- 상단 창 이름 -->
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
            <!-- 본문 내용 -->
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
</template>

<script>
import * as Req from "@/dto/Request";
import * as Res from "@/dto/Response";

export default {
    props: {
        isOpenDialog: Boolean,
    },
    data() {
        return {
            inputs: {
                file: {value: undefined, src: undefined, warehouse: undefined},

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
        }
    },
    methods: {
        onClickMakePost: async function() {
            console.log("Click onClickMakePost");
            const files = this.inputs.file.value;
            const response = await this.sendImageToServer(files);

            if(response.id) {this.mkArticle(
                response.id,
                response.url,
                this.inputs.content.value, 
                this.inputs.toggleHide.value,
                this.inputs.toggleComment.value
            );}
            this.$store.commit('setDialog', false);
            this.$router.go();
        },

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
        sendImageToServer: async function(files) {
            const formData = new FormData();
            for(const file of files) {
                formData.append("files", file);
            }

            try {
                const res = await this.$axiosAuth.post(this.$to("/images"), formData, {"Content-Type": 'multipart/form-data'});
                const response = Res.ImageCreateResponse.of(res);
                return response;
            } catch (res) {
                const error = Res.ErrResponse.of(res);
                console.log(error);

                alert(error.isIt("ERROR_WHILE_SAVING"));
                return Res.ImageCreateResponse.of();
            }
        },
        mkArticle(id, url, content, toggleHide, toggleComment) {
            this.$axiosAuth({
                    method: 'post', 
                    url: this.$to("/articles"),
                    data: Req.ArticleCreateRequest.of(id, url,content, toggleHide, toggleComment).param,
                })
            .then(res => {
                    console.log("게시글 생성 성공.");
            })
            .catch(res => {
                    const error = Res.ErrResponse.of(res);
                    console.log(error);
            });
        },
    },
    computed: {
        file() { return this.inputs.file.value; },
    },
    watch: {
        file(newVal) { this.handleFile(newVal); },
        isOpenDialog(newVal) { this.deleteFile(newVal); },
    },
}
</script>

<style scoped>
    .hCenter {
        display: flex;
        justify-content: center;
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