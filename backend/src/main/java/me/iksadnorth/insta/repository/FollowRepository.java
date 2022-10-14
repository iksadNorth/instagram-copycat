package me.iksadnorth.insta.repository;

import me.iksadnorth.insta.model.entity.Article;
import me.iksadnorth.insta.model.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Long countByFollower_Id(Long accountId);
    Long countByFollowee_Id(Long accountId);
    Boolean existsByFollower_IdAndFollowee_Id(Long followerId, Long followeeId);
    Optional<Follow> findByFollower_IdAndFollowee_Id(Long id, Long id1);
}
