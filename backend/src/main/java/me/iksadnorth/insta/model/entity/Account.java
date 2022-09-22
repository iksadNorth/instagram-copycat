package me.iksadnorth.insta.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.iksadnorth.insta.type.RoleType;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE account SET deleted_at = NOW() WHERE id=?")
@Table(name = "account", indexes = @Index(columnList = "email"))
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Account extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String nickName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private LocalDateTime dateOfBirth;
    @Column(nullable = false) @Enumerated(EnumType.STRING)
    private RoleType role;

    private String introduction;
}
