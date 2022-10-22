package me.iksadnorth.insta.model.dto;

import lombok.*;
import me.iksadnorth.insta.model.entity.View;

import java.time.LocalDateTime;

@ToString
@EqualsAndHashCode(of = {"id"})
@RequiredArgsConstructor
@Builder
@Getter
public class ViewDto {
    // BaseEntity 속성들.
    private final Long id;
    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;

    // Entity 속성들.
    private final AccountDto account;
    private final ArticleDto article;

    public View toEntity() {
        View entity = new View();
        entity.setId(id);
        entity.setCreatedAt(createdAt);
        entity.setDeletedAt(deletedAt);

//        entity.setAccount(account.toEntity());
        entity.setArticle(article.toEntity());

        return entity;
    }

    public static ViewDto fromEntity(View entity) {
        return new ViewDto(
                entity.getId(),
                entity.getCreatedAt(),
                entity.getDeletedAt(),

                AccountDto.fromEntity(entity.getAccount()),
                ArticleDto.fromEntity(entity.getArticle())
        );
    }
}
