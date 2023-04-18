package me.iksadnorth.insta.repository;

import lombok.extern.slf4j.Slf4j;
import me.iksadnorth.insta.config.EnableProjectJpaConfig;
import me.iksadnorth.insta.model.dto.CommentDto;
import me.iksadnorth.insta.model.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.Tuple;
import java.util.*;

@Slf4j
@DisplayName("CommentRepo 테스트 - 직접 설정한 쿼리가 작동하는지 확인하는 테스트")
@EnableProjectJpaConfig
@ActiveProfiles("Test")
@DataJpaTest
class CommentRepositoryTest {
    @Autowired CommentRepository repo;

    @Test
    void findByArticle_IdAndParentIsNull() {
        // given
        Long id = 6L;
        Pageable pageable = PageRequest.of(0, 10);

        // when & then
        Page<Comment> articles = repo.findByArticle_IdAndParentIsNull(id, pageable);
        log.trace("총 {}개", articles.stream().count());

        articles.map(CommentDto::fromEntity).forEach(article -> log.trace(article.toString()));
    }

    @Test
    void countInBatchByArticle_Id() {
        // given
        Set<Long> ids = new HashSet<>();
        ids.add(1L);
        ids.add(2L);
        ids.add(3L);
        ids.add(4L);
        ids.add(5L);
        ids.add(6L);

        // when & then
        List<Tuple> cnts = repo.countInBatchByArticle_Id(ids);
        log.trace("총 {}개 검색결과", cnts.size());
        cnts.stream().map(tuple -> String.format("%s: %s", tuple.get(0), tuple.get(1))).forEach(log::trace);
    }
}