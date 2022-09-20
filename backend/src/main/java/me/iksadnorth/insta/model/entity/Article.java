package me.iksadnorth.insta.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.iksadnorth.insta.utils.BooleanToYNConverter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE article SET deleted_at = NOW() WHERE id=?")
@Table(name = "article")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Article extends BaseEntity {
    @CreatedBy
    private Long createdBy;

    @OneToOne(optional = false)
    @JoinColumn(name = "image_id", nullable = false)
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
}
