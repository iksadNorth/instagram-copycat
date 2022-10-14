package me.iksadnorth.insta.model.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.iksadnorth.insta.model.dto.ArticleDto;
import me.iksadnorth.insta.model.dto.CommentDto;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CommentReadResponse {
    private final LocalDateTime createdAt;
    private final Long createdBy;
    private final ArticleDto article;
    private final CommentDto parent;
    private final String content;

    public static CommentReadResponse from(CommentDto dto) {
        return new CommentReadResponse(
                dto.getCreatedAt(),
                dto.getCreatedBy(),
                dto.getArticle(),
                dto.getParent(),
                dto.getContent()
        );
    }
}
