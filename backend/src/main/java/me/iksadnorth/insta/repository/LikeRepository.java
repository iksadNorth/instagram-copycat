package me.iksadnorth.insta.repository;

import me.iksadnorth.insta.model.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Tuple;
import java.util.*;

public interface LikeRepository extends JpaRepository<Likes, Long> {

    Long countByArticle_Id(Long id);
    Long countByComment_Id(Long id);

    boolean existsByArticle_IdAndAccount_Email(Long id, String username);
    boolean existsByAccount_IdAndComment_Id(Long uid, Long cid);

    Optional<Likes> findByArticle_IdAndAccount_Email(Long id, String username);

    Optional<Likes> findByAccount_IdAndComment_Id(Long uid, Long cid);

    @Query(
            "SELECT l.article.id, COUNT(l) " +
                    "FROM Likes l " +
                    "GROUP BY l.article.id " +
                    "HAVING l.article.id IN :ids "
    )
    List<Tuple> countInBatchByArticle_Id(Collection<Long> ids);

    @Query(
            "SELECT l.comment.id, COUNT(l) " +
                    "FROM Likes l " +
                    "GROUP BY l.comment.id " +
                    "HAVING l.comment.id IN :ids "
    )
    List<Tuple> countInBatchByComment_Id(Collection<Long> ids);
}
