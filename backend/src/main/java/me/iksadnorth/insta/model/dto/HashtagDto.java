package me.iksadnorth.insta.model.dto;

import lombok.*;
import me.iksadnorth.insta.model.entity.Hashtag;
import me.iksadnorth.insta.model.entity.View;
import me.iksadnorth.insta.model.mappinginterface.HashtagNameMapping;

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
        return new HashtagDto(
                entity.getId(),
                entity.getCreatedAt(),
                entity.getDeletedAt(),

                entity.getName(),
                ArticleDto.fromEntity(entity.getArticle())
        );
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
