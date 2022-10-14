package me.iksadnorth.insta.repository;

import lombok.extern.slf4j.Slf4j;
import me.iksadnorth.insta.config.EnableProjectJpaConfig;
import me.iksadnorth.insta.model.entity.Follow;
import me.iksadnorth.insta.model.mappinginterface.HashtagNameMapping;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DisplayName("HashtagRepo 테스트 - 직접 설정한 쿼리가 작동하는지 확인하는 테스트")
@EnableProjectJpaConfig
@ActiveProfiles("Test")
@DataJpaTest
class HashtagRepositoryTest {
    @Autowired HashtagRepository repo;
    @Test
    void findDistinctNames() {
        // given
        Long id = 1L;
        Long followeeId = 2L;

        Pageable pageable = PageRequest.of(0, 10);

        // when & then
        Page<HashtagNameMapping> distinctNames = repo.findDistinctNameBy(pageable);
        distinctNames.forEach(hashtagNameMapping -> log.trace(hashtagNameMapping.getName()));
    }
}