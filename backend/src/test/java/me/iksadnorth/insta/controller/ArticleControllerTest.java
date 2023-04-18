package me.iksadnorth.insta.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.iksadnorth.insta.config.EnableProjectSecurityConfig;
import me.iksadnorth.insta.exception.ErrorCode;
import me.iksadnorth.insta.fixture.Fixture;
import me.iksadnorth.insta.model.request.ArticleCreateRequest;
import me.iksadnorth.insta.model.request.ArticleUpdateRequest;
import me.iksadnorth.insta.service.AccountService;
import me.iksadnorth.insta.service.ArticleService;
import me.iksadnorth.insta.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Fixture.SetMockUser
@EnableProjectSecurityConfig
@Slf4j
@DisplayName("ArticleController 테스트")
@ActiveProfiles("test")
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;

    @MockBean AccountService accountService;
    @MockBean CommentService commentService;
    @MockBean ArticleService service;
    @InjectMocks ArticleController controller;

    private final String prefix = "/api/v1";
    private final Fixture fixture = Fixture.getInstance();

    @DisplayName("[post][/articles] 게시글 생성 - 정상 작동")
    @Test
    void articleCreateTest1() throws Exception {
        // given

        // when & then
        mvc.perform(
                        post(prefix + "/articles")
                                .content(mapper.writeValueAsBytes(ArticleCreateRequest.from(fixture.getArticleDtos(1))))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("[post][/articles] 게시글 생성 - 로그인 하지 않은 유저.")
    @Test
    @WithAnonymousUser
    void articleCreateTest2() throws Exception {
        // given

        // when & then
        mvc.perform(
                        post(prefix + "/articles")
                                .content(mapper.writeValueAsBytes(ArticleCreateRequest.from(fixture.getArticleDtos(1))))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().is(ErrorCode.NOT_BELONGING_TO_YOU.getStatus().value()));
    }

    @DisplayName("[get][/articles/{id}] 게시글 조회 - 정상작동")
    @Test
    @WithAnonymousUser
    void articleReadTest1() throws Exception {
        // given
        given(service.articleReadWithInfo(any(Long.class))).willReturn(fixture.getArticleDtos(0));

        // when & then
        mvc.perform(
                        get(prefix + "/articles/1")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("[put][/articles/{id}] 게시글 조회 - 정상작동")
    @Test
    void articleReadTest2() throws Exception {
        // given

        // when & then
        mvc.perform(
                        put(prefix + "/articles/1")
                                .content(mapper.writeValueAsBytes(ArticleUpdateRequest.from(fixture.getArticleDtos(1))))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());
    }
}