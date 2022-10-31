package me.iksadnorth.insta.model.dto;

import lombok.*;
import me.iksadnorth.insta.model.entity.Article;
import me.iksadnorth.insta.utils.ProxyHandler;

import java.time.LocalDateTime;
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
    private final AccountDto account;
    private final ImageDto image;
    private final String content;
    private final Boolean isHideLikesAndViews;
    private final Boolean isAllowedComments;
    private final List<CommentDto> comments;

    // Service를 위한 속성들.
    private final Long numComments;
    private final Long numLikes;
    private final Long numViews;

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
        return ArticleDto.fromEntity(entity, null, null);
    }

    public static ArticleDto fromEntity(Article entity, Long numComments, Long numLikes) {
        return ArticleDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .deletedAt(entity.getDeletedAt())

                .account(ProxyHandler.of(entity.getAccount()).map(AccountDto::fromEntity).orElse(null))
                .image(ProxyHandler.of(entity.getImage()).map(ImageDto::fromEntity).orElse(null))
                .content(entity.getContent())
                .isHideLikesAndViews(entity.getIsHideLikesAndViews())
                .isAllowedComments(entity.getIsAllowedComments())

                .numComments(numComments)
                .numLikes(numLikes)
                .numViews(numViews)

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
