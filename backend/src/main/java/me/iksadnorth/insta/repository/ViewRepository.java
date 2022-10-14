package me.iksadnorth.insta.repository;

import me.iksadnorth.insta.model.entity.Comment;
import me.iksadnorth.insta.model.entity.View;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ViewRepository extends JpaRepository<View, Long> {

    Long countByArticle_Id(Long id);

    boolean existsByArticle_IdAndAccount_Email(Long id, String username);

    Optional<View> findByArticle_IdAndAccount_Email(Long id, String username);
}
