package me.iksadnorth.insta.model.dto;

import lombok.*;
import me.iksadnorth.insta.model.entity.Follow;
import me.iksadnorth.insta.utils.ProxyHandler;

import java.time.LocalDateTime;

@ToString
@EqualsAndHashCode(of = {"id"})
@RequiredArgsConstructor
@Builder
@Getter
public class FollowDto {
    // BaseEntity 속성들.
    private final Long id;
    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;

    // Entity 속성들.
    private final AccountDto follower;
    private final AccountDto followee;

    public Follow toEntity() {
        Follow entity = new Follow();
        entity.setId(id);
        entity.setCreatedAt(createdAt);
        entity.setDeletedAt(deletedAt);

        entity.setFollower(follower.toEntity());
        entity.setFollowee(followee.toEntity());

        return entity;
    }

    public static FollowDto fromEntity(Follow entity) {
        return FollowDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .deletedAt(entity.getDeletedAt())

                .follower(ProxyHandler.of(entity.getFollower()).map(AccountDto::fromEntity).orElse(null))
                .followee(ProxyHandler.of(entity.getFollowee()).map(AccountDto::fromEntity).orElse(null))
                .build();
    }
}
