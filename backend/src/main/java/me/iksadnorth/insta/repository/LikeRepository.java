package me.iksadnorth.insta.repository;

import me.iksadnorth.insta.model.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {

    Long countByArticle_Id(Long id);
    Long countByComment_Id(Long id);

    boolean existsByArticle_IdAndAccount_Email(Long id, String username);
    boolean existsByAccount_IdAndComment_Id(Long uid, Long cid);

    Optional<Likes> findByArticle_IdAndAccount_Email(Long id, String username);

    Optional<Likes> findByAccount_IdAndComment_Id(Long uid, Long cid);
}
