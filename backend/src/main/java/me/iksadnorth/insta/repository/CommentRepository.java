package me.iksadnorth.insta.repository;

import me.iksadnorth.insta.model.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByParent_Id(Long id, Pageable pageable);
    boolean existsByIdAndAccount_Email(Long id, String email);

    Long countByArticle_Id(Long id);
}
