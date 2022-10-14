package me.iksadnorth.insta.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUNDED(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    ID_NOT_FOUNDED(HttpStatus.NOT_FOUND, "해당 ID 값을 찾을 수 없습니다."),
    FOLLOW_NOT_FOUNDED(HttpStatus.NOT_FOUND, "해당 팔로우를 찾을 수 없습니다."),
    TAG_NOT_FOUNDED(HttpStatus.NOT_FOUND, "해당 태그를 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀 번호가 틀렸습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "JWT Token 값이 적절하지 않습니다."),
    TOKEN_NOT_FOUNDED(HttpStatus.UNAUTHORIZED, "인증을 위한 토큰을 찾을 수 없습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰의 기한이 만료되었습니다."),
    OWNERSHIP_NOT_FOUNDED(HttpStatus.UNAUTHORIZED, "해당 객체에 대한 권한이 없습니다."),
    DUPLICATED_USER(HttpStatus.INTERNAL_SERVER_ERROR, "이미 존재하는 유저입니다."),
    DUPLICATED_FOLLOW(HttpStatus.INTERNAL_SERVER_ERROR, "이미 팔로우되어 있습니다.");

    private final HttpStatus status;
    private final String message;

}
