package me.iksadnorth.insta.model.dto;

import lombok.*;
import me.iksadnorth.insta.model.entity.Article;

import java.time.LocalDateTime;
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

//        entity.setAccount(account.toEntity());
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

                .content(Optional.ofNullable(dto.getContent()).orElse(content))
                .image(Optional.ofNullable(dto.getImage()).orElse(image))
                .isHideLikesAndViews(Optional.ofNullable(dto.getIsHideLikesAndViews()).orElse(isHideLikesAndViews))
                .isAllowedComments(Optional.ofNullable(dto.getIsAllowedComments()).orElse(isAllowedComments))
                .build();
    }
}
