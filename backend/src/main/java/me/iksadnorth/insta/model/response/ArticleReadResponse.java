package me.iksadnorth.insta.model.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.iksadnorth.insta.model.dto.AccountDto;
import me.iksadnorth.insta.model.dto.ArticleDto;
import me.iksadnorth.insta.model.dto.ImageDto;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ArticleReadResponse {
    private final Long id;
    private final LocalDateTime createdAt;

    private final AccountDto account;
    private final ImageDto image;
    private final String content;

    // TODO: 아래 칼럼들에 대해서도 작업하기
    private final Long numComments;
    private final Long numLikes;
    private final Long numViews;

    public static ArticleReadResponse from(ArticleDto dto) {
        return new ArticleReadResponse(
                dto.getId(),
                dto.getCreatedAt(),

                dto.getAccount(),
                dto.getImage(),
                dto.getContent(),

                dto.getNumComments(),
                dto.getNumLikes(),
                dto.getNumViews()
        );
    }
}
