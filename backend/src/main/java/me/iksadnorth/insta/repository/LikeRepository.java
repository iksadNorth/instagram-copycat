package me.iksadnorth.insta.repository;

import me.iksadnorth.insta.model.entity.Likes;
import me.iksadnorth.insta.model.entity.View;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {

    Long countByArticle_Id(Long id);

    boolean existsByArticle_IdAndAccount_Email(Long id, String username);

    Optional<Likes> findByArticle_IdAndAccount_Email(Long id, String username);
}
