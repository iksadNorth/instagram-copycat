package me.iksadnorth.insta.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.iksadnorth.insta.model.dto.AccountDto;
import me.iksadnorth.insta.model.dto.ArticleDto;
import me.iksadnorth.insta.model.dto.ImageDto;
import me.iksadnorth.insta.model.entity.Account;
import me.iksadnorth.insta.model.entity.Image;
import me.iksadnorth.insta.utils.BooleanToYNConverter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class ArticleCreateRequest {
    private Long createdBy;
    private AccountDto account;
    private ImageDto image;
    private String content;
    private Boolean isHideLikesAndViews;
    private Boolean isAllowedComments;

    public static ArticleCreateRequest from(ArticleDto dto) {
        ArticleCreateRequest articleCreateRequest = new ArticleCreateRequest();
        articleCreateRequest.setCreatedBy(dto.getCreatedBy());
        articleCreateRequest.setAccount(dto.getAccount());
        articleCreateRequest.setImage(dto.getImage());
        articleCreateRequest.setContent(dto.getContent());
        articleCreateRequest.setIsHideLikesAndViews(dto.getIsHideLikesAndViews());
        articleCreateRequest.setIsAllowedComments(dto.getIsAllowedComments());
        return articleCreateRequest;
    }

    public ArticleDto toDto() {
        return ArticleDto.builder()
                .createdBy(createdBy)
                .account(account)
                .image(image)
                .content(content)
                .isHideLikesAndViews(isHideLikesAndViews)
                .isAllowedComments(isAllowedComments)
                .build();
    }
}