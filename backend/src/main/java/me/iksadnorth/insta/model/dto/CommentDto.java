package me.iksadnorth.insta.model.dto;

import lombok.*;
import me.iksadnorth.insta.model.entity.Comment;
import me.iksadnorth.insta.utils.ProxyHandler;

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

    // Service를 위한 속성들.
    private  final Long numLikes;
    private  final Long numChildren;

    public Comment toEntity() {
        Comment entity = new Comment();
        entity.setId(id);
        entity.setCreatedAt(createdAt);
        entity.setDeletedAt(deletedAt);

        entity.setArticle(Optional.ofNullable(article).map(ArticleDto::toEntity).orElse(null));
        entity.setParent(Optional.ofNullable(parent).map(CommentDto::toEntity).orElse(null));
        entity.setContent(content);

        return entity;
    }

    public static CommentDto fromEntity(Comment entity) {
        return CommentDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .deletedAt(entity.getDeletedAt())

                .account(ProxyHandler.of(entity.getAccount()).map(AccountDto::fromEntity).orElse(null))
                .article(ProxyHandler.of(entity.getArticle()).map(ArticleDto::fromEntity).orElse(null))
                .parent(ProxyHandler.of(entity.getParent()).map(CommentDto::fromEntity).orElse(null))
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

    public CommentDto withNum(Long numLikes, Long numChildren) {
        return CommentDto.builder()
                .id(id)
                .createdAt(createdAt)
                .deletedAt(deletedAt)

                .account(account)
                .article(article)
                .parent(parent)
                .content(content)

                .numLikes(numLikes)
                .numChildren(numChildren)
                .build();
    }

    public static CommentDto of(String content, AccountDto accountDto, ArticleDto articleDto, CommentDto parentDto) {
        return CommentDto.builder()
                .account(accountDto)
                .article(articleDto)
                .parent(parentDto)
                .content(content)
                .build();
    }

    public static CommentDto of(String content, AccountDto accountDto, ArticleDto articleDto) {
        return CommentDto.of(content, accountDto, articleDto, null);
    }
}
