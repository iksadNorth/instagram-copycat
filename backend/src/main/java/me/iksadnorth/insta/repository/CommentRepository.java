package me.iksadnorth.insta.repository;

import me.iksadnorth.insta.model.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @EntityGraph(attributePaths = {"account"}, type= EntityGraph.EntityGraphType.LOAD)
    Page<Comment> findByArticle_IdAndParentIsNull(Long id, Pageable pageable);
    @EntityGraph(attributePaths = {"account", "parent"}, type= EntityGraph.EntityGraphType.LOAD)
    Page<Comment> findByParent_Id(Long id, Pageable pageable);
    boolean existsByIdAndAccount_Email(Long id, String email);

    Long countByArticle_Id(Long id);
    Long countByParent_Id(Long id);

    @Query("SELECT c FROM Comment c JOIN FETCH c.article WHERE c.id = :id")
    Optional<Comment> findByIdWithArticle(Long id);
}
