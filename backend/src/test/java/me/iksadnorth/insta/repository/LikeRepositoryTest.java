package me.iksadnorth.insta.repository;

import lombok.extern.slf4j.Slf4j;
import me.iksadnorth.insta.config.EnableProjectJpaConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ActiveProfiles("test")
@EnableProjectJpaConfig
@DataJpaTest
class LikeRepositoryTest {
    @Autowired LikeRepository repo;

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