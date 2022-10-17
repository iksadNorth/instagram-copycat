const dictErrorCode = {
    "USER_NOT_FOUNDED": "해당 유저를 찾을 수 없습니다.",
    "INVALID_PASSWORD": "비밀 번호가 틀렸습니다.",
    "DUPLICATED_USER": "해당 이메일은 이미 존재합니다.",
    "ERROR_WHILE_SAVING": "이미지 저장 중 문제가 발생했습니다.",
};

export default function(status, errorCode) {
    return (status==errorCode) ? dictErrorCode[errorCode] : "";
}