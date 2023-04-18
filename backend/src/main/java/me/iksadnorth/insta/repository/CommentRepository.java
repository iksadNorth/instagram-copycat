package me.iksadnorth.insta.repository;

import me.iksadnorth.insta.model.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.util.Pair;

import javax.persistence.Tuple;
import java.util.*;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @EntityGraph(attributePaths = {"account"}, type= EntityGraph.EntityGraphType.LOAD)
    Page<Comment> findByArticle_IdAndParentIsNull(Long id, Pageable pageable);
    @EntityGraph(attributePaths = {"account", "parent"}, type= EntityGraph.EntityGraphType.LOAD)
    Page<Comment> findByParent_Id(Long id, Pageable pageable);

    Long countByArticle_Id(Long id);
    Long countByParent_Id(Long id);

    @Query("SELECT c FROM Comment c JOIN FETCH c.article WHERE c.id = :id")
    Optional<Comment> findWithArticleById(Long id);

    @Query(
            "SELECT c.article.id, COUNT(c) " +
                    "FROM Comment c " +
                    "GROUP BY c.article.id " +
                    "HAVING c.article.id IN :ids "
    )
    List<Tuple> countInBatchByArticle_Id(Collection<Long> ids);

    @Query(
            "SELECT c.parent.id, COUNT(c) " +
                    "FROM Comment c " +
                    "GROUP BY c.parent.id " +
                    "HAVING c.parent.id IN :ids "
    )
    List<Tuple> countInBatchByParent_Id(Set<Long> ids);

    @Query("SELECT c FROM Comment c JOIN FETCH c.account WHERE c.id = :id")
    Optional<Comment> findWithAccountById(Long id);
}
