package me.iksadnorth.insta.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.iksadnorth.insta.type.ColumnType;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE likes SET deleted_at = NOW() WHERE id=?")
@Table(name = "likes")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Likes extends BaseEntity {
    @CreatedBy
    private Long createdBy;

    @Enumerated(value = EnumType.STRING) @Column(nullable = false)
    private ColumnType type;

    @Column(nullable = false)
    private Long targetId;
}
