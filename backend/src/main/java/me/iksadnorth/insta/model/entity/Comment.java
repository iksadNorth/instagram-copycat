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
@SQLDelete(sql = "UPDATE comment SET deleted_at = NOW() WHERE id=?")
@Table(name = "comment")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Comment extends BaseEntity {
    @CreatedBy
    private Long createdBy;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "parent_id")
    private Comment parent;

    private String content;
}
