package me.iksadnorth.insta.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE follow SET deleted_at = NOW() WHERE id=?")
@Table(name = "follow")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Follow extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "follower_id", nullable = false)
    private Account follower;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "followee_id", nullable = false)
    private Account followee;
}
