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

@Setter
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE image SET deleted_at = NOW() WHERE id=?")
@Table(name = "image")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Image extends BaseEntity {
    @Column(nullable = false)
    private String path;
}
