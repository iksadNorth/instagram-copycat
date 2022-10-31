package me.iksadnorth.insta.repository;

import me.iksadnorth.insta.model.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @EntityGraph(attributePaths = {"image", "account"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Article> findById(Long id);
    Long countByAccount_Id(Long accountId);

    @EntityGraph(attributePaths = {"image", "account"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query(
            "SELECT a FROM Article a " +
                    "LEFT JOIN a.account w " +
                    "LEFT JOIN w.followees fs " +
                    "WHERE fs.follower.id = :id"
    )
    Page<Article> findFeedListById(Long id, Pageable pageable);

    @Query("SELECT a FROM Article a JOIN FETCH a.image WHERE a.id IN :ids")
    List<Article> findRandListById(Collection<Long> ids, Pageable pageable);

    @EntityGraph(attributePaths = {"image"}, type = EntityGraph.EntityGraphType.LOAD)
    Page<Article> findByAccount_Id(Long id, Pageable pageable);

    boolean existsByIdAndAccount_Email(Long id, String username);

    Page<Article> findByHashtags_Name(String tag, Pageable pageable);
}
