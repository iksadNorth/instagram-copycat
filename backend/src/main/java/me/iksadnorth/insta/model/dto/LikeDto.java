package me.iksadnorth.insta.model.dto;

import lombok.*;
import me.iksadnorth.insta.model.entity.Likes;
import me.iksadnorth.insta.utils.ProxyHandler;

import java.time.LocalDateTime;
import java.util.Optional;

@ToString
@EqualsAndHashCode(of = {"id"})
@RequiredArgsConstructor
@Builder
@Getter
public class LikeDto {
    // BaseEntity 속성들.
    private final Long id;
    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;

    // Entity 속성들.
    private final AccountDto account;
    private final ArticleDto article;
    private final CommentDto comment;

    public Likes toEntity() {
        Likes entity = new Likes();
        entity.setId(id);
        entity.setCreatedAt(createdAt);
        entity.setDeletedAt(deletedAt);

        entity.setAccount(Optional.ofNullable(account).map(AccountDto::toEntity).orElse(null));
        entity.setArticle(Optional.ofNullable(article).map(ArticleDto::toEntity).orElse(null));
        entity.setComment(Optional.ofNullable(comment).map(CommentDto::toEntity).orElse(null));

        return entity;
    }

    public static LikeDto fromEntity(Likes entity) {
        return LikeDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .deletedAt(entity.getDeletedAt())
                .account(ProxyHandler.of(entity.getAccount()).map(AccountDto::fromEntity).orElse(null))
                .article(ProxyHandler.of(entity.getArticle()).map(ArticleDto::fromEntity).orElse(null))
                .comment(ProxyHandler.of(entity.getComment()).map(CommentDto::fromEntity).orElse(null))
                .build();
    }

    public static LikeDto of(AccountDto account, ArticleDto article) {
        return LikeDto.builder()
                .account(account)
                .article(article)
                .build();
    }

    public static LikeDto of(AccountDto account, CommentDto comment) {
        return LikeDto.builder()
                .account(account)
                .comment(comment)
                .build();
    }
}
