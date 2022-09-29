package me.iksadnorth.insta.fixture;

import lombok.extern.slf4j.Slf4j;
import me.iksadnorth.insta.config.EnableProjectConfig;
import me.iksadnorth.insta.controller.InstaController;
import me.iksadnorth.insta.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ActiveProfiles;

@AccountFixture.SetMockUser
@EnableProjectConfig
@Slf4j
@ActiveProfiles("test")
@WebMvcTest
public class AccountFixtureTest {
    @MockBean
    AccountService service;
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
