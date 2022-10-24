package me.iksadnorth.insta.model.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LikeReadResponse {
    private final Boolean isLike;

    public static LikeReadResponse of(Boolean isLike) {
        return new LikeReadResponse(isLike);
    }
}
