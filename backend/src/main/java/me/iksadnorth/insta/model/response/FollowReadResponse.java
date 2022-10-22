package me.iksadnorth.insta.model.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FollowReadResponse {
    private final Boolean isFollow;

    public static FollowReadResponse of(Boolean isFollow) {
        return new FollowReadResponse(isFollow);
    }
}
