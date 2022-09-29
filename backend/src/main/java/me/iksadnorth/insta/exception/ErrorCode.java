package me.iksadnorth.insta.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUNDED(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀 번호가 틀렸습니다."),
    DUPLICATED_USER_NAME(HttpStatus.INTERNAL_SERVER_ERROR, "이미 존재하는 유저입니다."),
    INVALID_ID(HttpStatus.NOT_FOUND, "아이디를 찾을 수 없습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "JWT Token 값이 적절하지 않습니다."),
    TOKEN_NOT_FOUNDED(HttpStatus.UNAUTHORIZED, "인증을 위한 토큰을 찾을 수 없습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰의 기한이 만료되었습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "접근 권한이 없습니다."),
    USER_ALREADY_EXISTED(HttpStatus.INTERNAL_SERVER_ERROR, "이미 해당 유저가 존재합니다.");

    private final HttpStatus status;
    private final String message;

}
