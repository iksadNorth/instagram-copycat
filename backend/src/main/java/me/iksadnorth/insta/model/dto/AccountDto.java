package me.iksadnorth.insta.model.dto;

import lombok.*;
import me.iksadnorth.insta.model.entity.Account;
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
public class AccountDto implements UserDetails {
    // BaseEntity 속성들.
    private final Long id;
    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;

    // Entity 속성들.
    private final String email;
    private final String userName;
    private final String nickName;
    private final String password;
    private final LocalDateTime dateOfBirth;
    private final String introduction;
    private final RoleType role;

    // 서비스를 위한 특별 속성들.
    private final Long articles;
    private final Long followers;
    private final Long followings;

    public Account toEntity() {
        Account entity = new Account();
        entity.setId(id);
        entity.setCreatedAt(createdAt);
        entity.setDeletedAt(deletedAt);

        entity.setEmail(email);
        entity.setUserName(userName);
        entity.setNickName(nickName);
        entity.setPassword(password);
        entity.setDateOfBirth(dateOfBirth);
        entity.setIntroduction(introduction);

        return entity;
    }

    public static AccountDto fromEntity(Account entity) {
        return fromEntity(entity, null, null, null);
    }

    public static AccountDto fromEntity(
            Account entity,
            Long articles,
            Long followers,
            Long followings
            ) {
        return new AccountDto(
                entity.getId(),
                entity.getCreatedAt(),
                entity.getDeletedAt(),
                entity.getEmail(),
                entity.getUserName(),
                entity.getNickName(),
                entity.getPassword(),
                entity.getDateOfBirth(),
                entity.getIntroduction(),
                entity.getRole(),

                articles, followers, followings
        );
    }

    public AccountDto overWriteWith(AccountDto dto) {
        return AccountDto.builder()
                .id(id)
                .createdAt(createdAt)
                .deletedAt(Optional.ofNullable(dto.getDeletedAt()).orElse(deletedAt))

                .email(Optional.ofNullable(dto.getEmail()).orElse(email))
                .userName(Optional.ofNullable(dto.getUserName()).orElse(userName))
                .nickName(Optional.ofNullable(dto.getNickName()).orElse(nickName))
                .password(Optional.ofNullable(dto.getPassword()).orElse(password))
                .dateOfBirth(Optional.ofNullable(dto.getDateOfBirth()).orElse(dateOfBirth))
                .introduction(Optional.ofNullable(dto.getIntroduction()).orElse(introduction))
                .role(Optional.ofNullable(dto.getRole()).orElse(role))

                .build();
    }

    public String getUserName() { return userName; }
    private GrantedAuthority transRole(RoleType role) { return new SimpleGrantedAuthority(role.name()); }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(transRole(role));
    }
    @Override public String getUsername() { return email; }
    @Override public String getPassword() { return password; }
    @Override public boolean isAccountNonExpired() { return deletedAt == null; }
    @Override public boolean isAccountNonLocked() { return deletedAt == null; }
    @Override public boolean isCredentialsNonExpired() { return deletedAt == null; }

    @Override public boolean isEnabled() { return deletedAt == null; }
}
