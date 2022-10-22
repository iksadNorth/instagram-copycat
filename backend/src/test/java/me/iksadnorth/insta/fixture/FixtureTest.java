package me.iksadnorth.insta.fixture;

import lombok.extern.slf4j.Slf4j;
import me.iksadnorth.insta.config.EnableProjectSecurityConfig;
import me.iksadnorth.insta.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

@Fixture.SetMockUser
@EnableProjectSecurityConfig
@Slf4j
@ActiveProfiles("test")
@WebMvcTest
public class FixtureTest {
    @MockBean
    AccountService service;
    @MockBean
    ArticleService articleService;
    @MockBean
    CommentService commentService;
    @MockBean
    HashtagService hashtagService;
    @MockBean
    ImageService imageService;
    @Autowired
    ApplicationContext applicationContext;
    @Test
    void printBeans() {
        Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(log::trace);
    }
    @Test
    void printUser() {
        log.trace(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Test
    @Fixture.SetMockOther
    void printUserOther() {
        log.trace(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Test
    @Fixture.SetMockAdmin
    void printUserAdmin() {
        log.trace(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Test
    @WithAnonymousUser
    void printUserAnonymous() {
        log.trace(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
