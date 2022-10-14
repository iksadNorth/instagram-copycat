package me.iksadnorth.insta.model.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.iksadnorth.insta.model.dto.ArticleDto;
import me.iksadnorth.insta.model.dto.CommentDto;
import me.iksadnorth.insta.model.dto.HashtagDto;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class HashtagReadResponse {
    private final String name;

    public static HashtagReadResponse from(HashtagDto dto) {
        return new HashtagReadResponse(
                dto.getName()
        );
    }
}
