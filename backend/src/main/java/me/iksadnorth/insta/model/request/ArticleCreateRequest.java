package me.iksadnorth.insta.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.iksadnorth.insta.model.dto.AccountDto;
import me.iksadnorth.insta.model.dto.ArticleDto;
import me.iksadnorth.insta.model.dto.ImageDto;

@Setter
@Getter
@NoArgsConstructor
public class ArticleCreateRequest {
    private AccountDto account;
    private ImageDto image;
    private String content;
    private Boolean isHideLikesAndViews;
    private Boolean isAllowedComments;

    public static ArticleCreateRequest from(ArticleDto dto) {
        ArticleCreateRequest articleCreateRequest = new ArticleCreateRequest();
        articleCreateRequest.setAccount(dto.getAccount());
        articleCreateRequest.setImage(dto.getImage());
        articleCreateRequest.setContent(dto.getContent());
        articleCreateRequest.setIsHideLikesAndViews(dto.getIsHideLikesAndViews());
        articleCreateRequest.setIsAllowedComments(dto.getIsAllowedComments());
        return articleCreateRequest;
    }

    public ArticleDto toDto() {
        return ArticleDto.builder()
                .account(account)
                .image(image)
                .content(content)
                .isHideLikesAndViews(isHideLikesAndViews)
                .isAllowedComments(isAllowedComments)
                .build();
    }
}
