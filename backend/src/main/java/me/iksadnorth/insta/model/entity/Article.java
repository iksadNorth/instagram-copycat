package me.iksadnorth.insta.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.iksadnorth.insta.utils.BooleanToYNConverter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE article SET deleted_at = NOW() WHERE id=?")
@Where(clause = "deleted_at is NULL")
@Table(name = "article")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Article extends BaseEntity {
    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    private String content;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(length = 1)
    @ColumnDefault(value = BooleanToYNConverter.False)
    private Boolean isHideLikesAndViews;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(length = 1)
    @ColumnDefault(value = BooleanToYNConverter.False)
    private Boolean isAllowedComments;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
    private List<Likes> likes;
}
