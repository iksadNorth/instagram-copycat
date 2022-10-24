package me.iksadnorth.insta.model.dto;

import lombok.*;
import me.iksadnorth.insta.model.entity.Likes;

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
        return new LikeDto(
                entity.getId(),
                entity.getCreatedAt(),
                entity.getDeletedAt(),

                AccountDto.fromEntity(entity.getAccount()),
                ArticleDto.fromEntity(entity.getArticle()),
                CommentDto.fromEntity(entity.getComment())
        );
    }
}
