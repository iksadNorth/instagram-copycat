package me.iksadnorth.insta.model.dto;

import lombok.*;
import me.iksadnorth.insta.model.entity.Hashtag;
import me.iksadnorth.insta.model.mappinginterface.HashtagNameMapping;
import me.iksadnorth.insta.utils.ProxyHandler;

import java.time.LocalDateTime;

@ToString
@EqualsAndHashCode(of = {"id"})
@RequiredArgsConstructor
@Builder
@Getter
public class HashtagDto {
    // BaseEntity 속성들.
    private final Long id;
    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;

    // Entity 속성들.
    private final String name;
    private final ArticleDto article;

    public Hashtag toEntity() {
        Hashtag entity = new Hashtag();
        entity.setId(id);
        entity.setCreatedAt(createdAt);
        entity.setDeletedAt(deletedAt);

        entity.setName(name);
        entity.setArticle(article.toEntity());

        return entity;
    }

    public static HashtagDto fromEntity(Hashtag entity) {
        return HashtagDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .deletedAt(entity.getDeletedAt())
                .name(entity.getName())
                .article(ProxyHandler.of(entity.getArticle()).map(ArticleDto::fromEntity).orElse(null))
                .build();
    }

    public static HashtagDto fromNameMapping(HashtagNameMapping hashtagNameMapping) {
        return new HashtagDto(
                null,
                null,
                null,

                hashtagNameMapping.getName(),
                null
        );
    }
}
