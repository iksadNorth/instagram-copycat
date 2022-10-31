package me.iksadnorth.insta.model.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.iksadnorth.insta.model.dto.AccountDto;
import me.iksadnorth.insta.model.dto.ArticleDto;
import me.iksadnorth.insta.model.dto.CommentDto;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CommentReadResponse {
    private final Long id;
    private final AccountDto account;
    private final LocalDateTime createdAt;
    private final ArticleDto article;
    private final CommentDto parent;
    private final String content;
    private final Long numLikes;
    private final Long numChildren;

    public static CommentReadResponse from(CommentDto dto) {
        return new CommentReadResponse(
                dto.getId(),
                dto.getAccount(),
                dto.getCreatedAt(),
                dto.getArticle(),
                dto.getParent(),
                dto.getContent(),
                dto.getNumLikes(),
                dto.getNumChildren()
        );
    }
}
