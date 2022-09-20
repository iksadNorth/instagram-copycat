package me.iksadnorth.insta.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE account SET deleted_at = NOW() WHERE id=?")
@Table(name = "account")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Account extends BaseEntity {
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String nickName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private LocalDateTime dateOfBirth;

    private String introduction;
}
