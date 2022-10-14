package me.iksadnorth.insta.repository;

import me.iksadnorth.insta.model.entity.Account;
import me.iksadnorth.insta.model.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Long countByAccount_Id(Long accountId);

    @Query(
            "SELECT a FROM Article a " +
                    "LEFT JOIN a.account w " +
                    "LEFT JOIN w.followees fs " +
                    "WHERE fs.follower.id = :id"
    )
    Page<Article> findFeedListById(Long id, Pageable pageable);

    @Query(value = "SELECT * FROM article ORDER BY RAND()", nativeQuery = true)
    Page<Article> findRandListById(Long id, Pageable pageable);

    Page<Article> findByAccount_Id(Long id, Pageable pageable);

    boolean existsByIdAndAccount_Email(Long id, String username);

    Page<Article> findByHashtags_Name(String tag, Pageable pageable);
}
