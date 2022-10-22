package me.iksadnorth.insta.model.dto;

import lombok.*;
import me.iksadnorth.insta.model.entity.Article;
import me.iksadnorth.insta.model.entity.Comment;

import java.time.LocalDateTime;
import java.util.Optional;

@ToString
@EqualsAndHashCode(of = {"id"})
@RequiredArgsConstructor
@Builder
@Getter
public class CommentDto {
    // BaseEntity 속성들.
    private final Long id;
    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;

    // Entity 속성들.
    private final AccountDto account;
    private final ArticleDto article;
    private final CommentDto parent;
    private final String content;

    public Comment toEntity() {
        Comment entity = new Comment();
        entity.setId(id);
        entity.setCreatedAt(createdAt);
        entity.setDeletedAt(deletedAt);

        entity.setArticle(article.toEntity());
        entity.setParent(parent.toEntity());
        entity.setContent(content);

        return entity;
    }

    public static CommentDto fromEntity(Comment entity) {
        if(entity == null) { return null; }
        return CommentDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .deletedAt(entity.getDeletedAt())

                .account(AccountDto.fromEntity(entity.getAccount()))
                .article(ArticleDto.fromEntity(entity.getArticle()))
                .parent(CommentDto.fromEntity(entity.getParent()))
                .content(entity.getContent())

                .build();
    }

    public CommentDto overWriteWith(CommentDto dto) {
        return CommentDto.builder()
                .id(id)
                .createdAt(createdAt)
                .deletedAt(Optional.ofNullable(dto.getDeletedAt()).orElse(deletedAt))

                .article(article)
                .parent(parent)
                .content(Optional.ofNullable(dto.getContent()).orElse(content))
                .build();
    }
}
