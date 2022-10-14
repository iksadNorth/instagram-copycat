package me.iksadnorth.insta.model.dto;

import lombok.*;
import me.iksadnorth.insta.model.entity.Account;
import me.iksadnorth.insta.model.entity.Follow;
import me.iksadnorth.insta.type.RoleType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
        return new FollowDto(
                entity.getId(),
                entity.getCreatedAt(),
                entity.getDeletedAt(),

                AccountDto.fromEntity(entity.getFollower()),
                AccountDto.fromEntity(entity.getFollowee())
        );
    }
}
