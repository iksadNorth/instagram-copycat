package me.iksadnorth.insta.repository;

import lombok.extern.slf4j.Slf4j;
import me.iksadnorth.insta.config.EnableProjectJpaConfig;
import me.iksadnorth.insta.model.entity.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@DisplayName("ArticleRepo 테스트 - 직접 설정한 쿼리가 작동하는지 확인하는 테스트")
@EnableProjectJpaConfig
@ActiveProfiles("Test")
@DataJpaTest
class ArticleRepositoryTest {
    @Autowired ArticleRepository repo;

    @Test
    void countByAccount_IdTest() {
        // given
        Long id = 1L;

        // when & then
        Long articles = repo.countByAccount_Id(id);
        log.trace("Id {}의 작성 목록 갯수: {}", id, articles);
    }

    @Test
    void findFeedListById() {
        // given
        Long id = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        // when & then
        Page<Article> articles = repo.findFeedListById(id, pageable);
        articles.forEach(article -> {
            log.trace(article.getContent());
        });
    }

    @Test
    void findRandListById() {
        // given
        Long id = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        // when & then
        Page<Article> articles = repo.findRandListById(id, pageable);
        articles.forEach(article -> {
            log.trace(article.getContent());
        });
    }

    @Test
    void findByAccount_Id() {
        // given
        Long id = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        // when & then
        Page<Article> articles = repo.findByAccount_Id(id, pageable);
        articles.forEach(article -> {
            log.trace(article.getContent());
        });
    }

    @Test
    void findByHashtag_Name() {
        // given
        String tag = "#tag3";
        Pageable pageable = PageRequest.of(0, 10);

        // when & then
        Page<Article> articles = repo.findByHashtags_Name(tag, pageable);
        articles.forEach(article -> {
            log.trace(article.getContent());
        });
    }
}