package me.iksadnorth.insta.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.iksadnorth.insta.type.RoleType;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE account SET deleted_at = NOW() WHERE id=?")
@Where(clause = "deleted_at is NULL")
@Table(name = "account", indexes = @Index(columnList = "email"))
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Account extends BaseEntity {
    @Setter @Column(nullable = false, unique = true)
    private String email;
    @Setter @Column(nullable = false)
    private String userName;
    @Setter @Column(nullable = false)
    private String nickName;
    @Setter @Column(nullable = false)
    private String password;
    @Setter @Column(nullable = false)
    private LocalDateTime dateOfBirth;
//    @Setter @ColumnDefault("ROLE_USER") @Enumerated(EnumType.STRING)
    @Setter @Enumerated(EnumType.STRING)
    private RoleType role;

    @Setter
    private String introduction;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private Set<Article> articles = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "follower")
    private Set<Follow> followers = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "followee")
    private Set<Follow> followees = new HashSet<>();
}
