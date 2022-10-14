package me.iksadnorth.insta.model.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CountsResponse {
    private final Long counts;

    public static CountsResponse of(Long counts) {
        return new CountsResponse(counts);
    }
}
