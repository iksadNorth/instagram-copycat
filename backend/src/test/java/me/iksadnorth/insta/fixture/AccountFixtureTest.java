package me.iksadnorth.insta.fixture;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;

@Slf4j
@Profile("test")
public class AccountFixtureTest {
    @Test
    void printUser() {
        log.trace(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Test
    @AccountFixture.SetMockOther
    void printUserOther() {
        log.trace(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Test
    @AccountFixture.SetMockAdmin
    void printUserAdmin() {
        log.trace(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Test
    @WithAnonymousUser
    void printUserAnonymous() {
        log.trace(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
