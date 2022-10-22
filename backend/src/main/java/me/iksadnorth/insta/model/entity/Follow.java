package me.iksadnorth.insta.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE follow SET deleted_at = NOW() WHERE id=?")
@Where(clause = "deleted_at is NULL")
@Table(name = "follow")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Follow extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "follower_id", nullable = false)
    private Account follower;       // 팔로우 주체: 팔로우 행위를 하는 사람.
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "followee_id", nullable = false)
    private Account followee;       // 팔로우 객체: 팔로우 행위를 당하는 사람.
}
