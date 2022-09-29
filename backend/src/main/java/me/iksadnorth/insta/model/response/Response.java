package me.iksadnorth.insta.model.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.iksadnorth.insta.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
public class Response<T> {
    private final String status;
    private final T data;

    public static <T> Response<T> success(T data) {
        return new Response<>("SUCCESS", data);
    }

    public static Response<Void> error(ErrorCode errorCode) {
        return new Response<>(errorCode.getMessage(), null);
    }

    @Override
    public String toString() {
        return data != null
                ? "Response{" +
                "status='" + status + '\'' +
                ", data=" + data +
                '}'
                : "Response{" +
                "status='" + status + '\'' +
                ", data= null }";
    }
}
