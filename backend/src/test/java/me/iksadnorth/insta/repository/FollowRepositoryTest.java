package me.iksadnorth.insta.repository;

import lombok.extern.slf4j.Slf4j;
import me.iksadnorth.insta.config.EnableProjectJpaConfig;
import me.iksadnorth.insta.model.entity.Follow;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@DisplayName("FollowRepo 테스트 - 직접 설정한 쿼리가 작동하는지 확인하는 테스트")
@EnableProjectJpaConfig
@ActiveProfiles("test")
@DataJpaTest
class FollowRepositoryTest {
    @Autowired
    FollowRepository repo;

    @Test
    void countByFollower_Id() {
        // given
        Long id = 1L;

        // when & then
        Long articles = repo.countByFollower_Id(id);
        log.trace("Id {}의 작성 목록 갯수: {}", id, articles);
    }

    @Test
    void countByFollowee_Id() {
        // given
        Long id = 1L;

        // when & then
        Long articles = repo.countByFollowee_Id(id);
        log.trace("Id {}의 작성 목록 갯수: {}", id, articles);
    }

    @Test
    void existsByIdFollower_IdAndFollowee_Id() {
        // given
        Long id = 1L;
        Long followeeId = 2L;

        // when & then
        Boolean result1 = repo.existsByFollower_IdAndFollowee_Id(id, followeeId);
        log.trace("Id {}는 Id {}를 팔로우 했나? {}", id, followeeId, result1);
        Boolean result2 = repo.existsByFollower_IdAndFollowee_Id(followeeId, id);
        log.trace("Id {}는 Id {}를 팔로우 했나? {}", followeeId, id, result2);
    }

    @Test
    void findByFollower_IdAndFollowee_Id() {
        // given
        Long id = 1L;
        Long followeeId = 2L;

        // when & then
        Follow follow = repo.findByFollower_IdAndFollowee_Id(id, followeeId).get();
        log.trace("팔로우 엔티티 주소 : {}", follow);
    }
}