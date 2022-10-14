package me.iksadnorth.insta.repository;

import me.iksadnorth.insta.model.dto.CommentDto;
import me.iksadnorth.insta.model.entity.Article;
import me.iksadnorth.insta.model.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByParent_Id(Long id, Pageable pageable);
    boolean existsByIdAndAccount_Email(Long id, String email);
}
