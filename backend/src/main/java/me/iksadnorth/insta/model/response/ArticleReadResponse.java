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

    private final Boolean isHideLikesAndViews;
    private final Boolean isAllowedComments;

    private final Long numComments;
    private final Long numLikes;

    public static ArticleReadResponse from(ArticleDto dto) {
        return new ArticleReadResponse(
                dto.getId(),
                dto.getCreatedAt(),

                dto.getAccount(),
                dto.getImage(),
                dto.getContent(),

                dto.getIsHideLikesAndViews(),
                dto.getIsAllowedComments(),

                dto.getNumComments(),
                dto.getNumLikes()
        );
    }
}
