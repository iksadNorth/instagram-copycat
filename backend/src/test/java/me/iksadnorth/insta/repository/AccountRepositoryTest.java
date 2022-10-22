package me.iksadnorth.insta.repository;

import lombok.extern.slf4j.Slf4j;
import me.iksadnorth.insta.config.EnableProjectJpaConfig;
import me.iksadnorth.insta.model.dto.AccountDto;
import me.iksadnorth.insta.model.entity.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@Slf4j
@DisplayName("AccountRepo 테스트 - 직접 설정한 쿼리가 작동하는지 확인하는 테스트")
@EnableProjectJpaConfig
@ActiveProfiles("Test")
@DataJpaTest
class AccountRepositoryTest {
    @Autowired AccountRepository repo;

    @DisplayName("findByIdWithProfileTest 테스트 - 페치 조인 확인.")
    @Test
    void findByIdWithProfileTest() {
        Optional<Account> entity1 = repo.findById(1L);
        log.trace("Id: {}", entity1.get().getId());
        log.trace("UserName: {}", entity1.get().getUserName());
        log.trace("Email: {}", entity1.get().getEmail());
        log.trace("NickName: {}", entity1.get().getNickName());
        log.trace("Introduction: {}", entity1.get().getIntroduction());
        entity1.get().getArticles().stream()
                .forEach(x -> log.trace("Article_id: {}", x.getId()));
        entity1.get().getFollowers().stream()
                .forEach(x -> log.trace("Followers_id: {}", x.getId()));
        entity1.get().getFollowees().stream()
                .forEach(x -> log.trace("Followees_id: {}", x.getId()));
    }

    @Test
    void findByNickNameContaining() {
        repo.findByNickNameContainingIgnoreCase("a", PageRequest.of(0, 10))
                .map(AccountDto::fromEntity)
                .map(Object::toString)
                .forEach(log::trace);
    }
}