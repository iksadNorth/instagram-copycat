package me.iksadnorth.insta.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE view SET deleted_at = NOW() WHERE id=?")
@Table(name = "view")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class View extends BaseEntity {
    @CreatedBy
    private Long createdBy;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "article_id", nullable = false)
    private Article article;
}
