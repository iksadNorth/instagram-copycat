package me.iksadnorth.insta.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.iksadnorth.insta.model.dto.AccountDto;
import me.iksadnorth.insta.model.dto.ArticleDto;
import me.iksadnorth.insta.model.dto.ImageDto;

@Setter
@Getter
@NoArgsConstructor
public class ArticleUpdateRequest {
    private ImageDto image;
    private String content;
    private Boolean isHideLikesAndViews;
    private Boolean isAllowedComments;

    public static ArticleUpdateRequest from(ArticleDto dto) {
        ArticleUpdateRequest articleUpdateRequest = new ArticleUpdateRequest();
        articleUpdateRequest.setImage(dto.getImage());
        articleUpdateRequest.setContent(dto.getContent());
        articleUpdateRequest.setIsHideLikesAndViews(dto.getIsHideLikesAndViews());
        articleUpdateRequest.setIsAllowedComments(dto.getIsAllowedComments());
        return articleUpdateRequest;
    }

    public ArticleDto toDto() {
        return ArticleDto.builder()
                .image(image)
                .content(content)
                .isHideLikesAndViews(isHideLikesAndViews)
                .isAllowedComments(isAllowedComments)
                .build();
    }
}
