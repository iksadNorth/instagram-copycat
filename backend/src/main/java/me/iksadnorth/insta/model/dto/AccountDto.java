package me.iksadnorth.insta.model.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.iksadnorth.insta.model.entity.Account;
import me.iksadnorth.insta.type.RoleType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(of = {"id"})
@RequiredArgsConstructor
@Builder
@Getter
public class AccountDto implements UserDetails {
    private final Long id;
    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;

    private final String email;
    private final String userName;
    private final String nickName;
    private final String password;
    private final LocalDateTime dataOfBirth;
    private final String introduction;
    private final RoleType role;

    public Account toEntity() {
        Account entity = new Account();
        entity.setId(id);
        entity.setCreatedAt(createdAt);
        entity.setDeletedAt(deletedAt);

        entity.setEmail(email);
        entity.setUserName(userName);
        entity.setNickName(nickName);
        entity.setPassword(password);
        entity.setDateOfBirth(dataOfBirth);
        entity.setIntroduction(introduction);

        return entity;
    }

    public static AccountDto fromEntity(Account entity) {
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
                entity.getRole()
        );
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
