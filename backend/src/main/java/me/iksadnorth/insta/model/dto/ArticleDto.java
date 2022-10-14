package me.iksadnorth.insta.model.dto;

import lombok.*;
import me.iksadnorth.insta.model.entity.Account;
import me.iksadnorth.insta.model.entity.Article;
import me.iksadnorth.insta.model.entity.Image;
import me.iksadnorth.insta.type.RoleType;
import me.iksadnorth.insta.utils.BooleanToYNConverter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@ToString
@EqualsAndHashCode(of = {"id"})
@RequiredArgsConstructor
@Builder
@Getter
public class ArticleDto {
    // BaseEntity 속성들.
    private final Long id;
    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;

    // Entity 속성들.
    private final Long createdBy;
    private final AccountDto account;
    private final ImageDto image;
    private final String content;
    private final Boolean isHideLikesAndViews;
    private final Boolean isAllowedComments;

    public Article toEntity() {
        Article entity = new Article();
        entity.setId(id);
        entity.setCreatedAt(createdAt);
        entity.setDeletedAt(deletedAt);

        entity.setCreatedBy(createdBy);
        entity.setAccount(account.toEntity());
        entity.setImage(image.toEntity());
        entity.setContent(content);
        entity.setIsHideLikesAndViews(isHideLikesAndViews);
        entity.setIsAllowedComments(isAllowedComments);

        return entity;
    }

    public static ArticleDto fromEntity(Article entity) {
        return ArticleDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .deletedAt(entity.getDeletedAt())

                .createdBy(entity.getCreatedBy())
                .account(AccountDto.fromEntity(entity.getAccount()))
                .image(ImageDto.fromEntity(entity.getImage()))
                .content(entity.getContent())
                .isHideLikesAndViews(entity.getIsHideLikesAndViews())
                .isAllowedComments(entity.getIsAllowedComments())

                .build();
    }

    public ArticleDto overWriteWith(ArticleDto dto) {
        return ArticleDto.builder()
                .id(id)
                .createdAt(createdAt)
                .deletedAt(Optional.ofNullable(dto.getDeletedAt()).orElse(deletedAt))

                .createdBy(createdBy)
                .content(Optional.ofNullable(dto.getContent()).orElse(content))
                .image(Optional.ofNullable(dto.getImage()).orElse(image))
                .isHideLikesAndViews(Optional.ofNullable(dto.getIsHideLikesAndViews()).orElse(isHideLikesAndViews))
                .isAllowedComments(Optional.ofNullable(dto.getIsAllowedComments()).orElse(isAllowedComments))
                .build();
    }
}
