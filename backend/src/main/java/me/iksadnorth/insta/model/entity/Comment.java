package me.iksadnorth.insta.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE comments SET deleted_at = NOW() WHERE id=?")
@Where(clause = "deleted_at is NULL")
@Table(name = "comments")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Comment extends BaseEntity {
    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "parent_id")
    private Comment parent;

    private String content;

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY)
    private List<Likes> likes;
}
