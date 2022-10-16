const dictErrorCode = {
    "USER_NOT_FOUNDED": "해당 유저를 찾을 수 없습니다.",
    "INVALID_PASSWORD": "비밀 번호가 틀렸습니다."
};

export default function(status, errorCode) {
    return (status==errorCode) ? dictErrorCode[errorCode] : "";
}