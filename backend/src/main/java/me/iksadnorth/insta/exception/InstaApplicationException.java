package me.iksadnorth.insta.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InstaApplicationException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;

    public InstaApplicationException(ErrorCode errorCode) {
        this(errorCode, null);
    }

    @Override
    public String toString() {
        return message != null
                ? String.format("\n%s \n%s", errorCode.getMessage(), message)
                : errorCode.getMessage();
    }
}
