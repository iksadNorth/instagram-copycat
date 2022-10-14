package me.iksadnorth.insta.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.iksadnorth.insta.config.EnableProjectSecurityConfig;
import me.iksadnorth.insta.exception.ErrorCode;
import me.iksadnorth.insta.exception.InstaApplicationException;
import me.iksadnorth.insta.fixture.Fixture;
import me.iksadnorth.insta.model.dto.AccountDto;
import me.iksadnorth.insta.model.request.AccountCreateRequest;
import me.iksadnorth.insta.model.request.AccountUpdateRequest;
import me.iksadnorth.insta.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Fixture.SetMockUser
@EnableProjectSecurityConfig
@Slf4j
@DisplayName("AccountController 테스트")
@ActiveProfiles("test")
@WebMvcTest(AccountController.class)
class AccountControllerTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;

    private final String prefix = "/api/v1";

    private final Fixture fixture = Fixture.getInstance();

    @InjectMocks AccountController controller;
    @MockBean AccountService service;

    @BeforeEach
    void setFixture() {
        // given
    }

    @DisplayName("[post][/accounts] 계정 생성 - 정상 작동")
    @Test
    @WithAnonymousUser
    void accountCreateTest1() throws Exception {
        // given

        // when & then
        mvc.perform(
                        post(prefix + "/accounts")
                                .content(mapper.writeValueAsBytes(AccountCreateRequest.from(fixture.getDtos(1))))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("[post][/accounts] 계정 생성 - 이미 존재하는 유저")
    @Test
    @WithAnonymousUser
    void accountCreateTest2() throws Exception {
        // given
        willThrow(new InstaApplicationException(ErrorCode.DUPLICATED_USER))
                .given(service).accountCreate(any(AccountDto.class));

        // when & then
        mvc.perform(
                        post(prefix + "/accounts")
                                .content(mapper.writeValueAsBytes(AccountCreateRequest.from(fixture.getDtos(1))))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().is(ErrorCode.DUPLICATED_USER.getStatus().value()));
    }

    @DisplayName("[get][/accounts/{id}] 계정 생성 - 정상 작동")
    @Test
    @WithAnonymousUser
    void accountReadTest1() throws Exception {
        // given
        given(service.loadById(eq(1L))).willReturn(fixture.getDtos(1));

        // when & then
        mvc.perform(get(prefix + "/accounts/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..%s", "nickName").exists())
                .andExpect(jsonPath("$..%s", "introduction").exists())
                .andExpect(jsonPath("$..%s", "articles").exists())
                .andExpect(jsonPath("$..%s", "followers").exists())
                .andExpect(jsonPath("$..%s", "followings").exists())
        ;
    }

    @DisplayName("[get][/accounts/{id}] 계정 생성 - 존재하지 않는 id")
    @Test
    @WithAnonymousUser
    void accountReadTest2() throws Exception {
        // given
        willThrow(new InstaApplicationException(ErrorCode.ID_NOT_FOUNDED))
                .given(service).loadById(eq(2L));

        // when & then
        mvc.perform(get(prefix + "/accounts/2"))
                .andDo(print())
                .andExpect(status().is(ErrorCode.ID_NOT_FOUNDED.getStatus().value()))
        ;
    }

    @DisplayName("[put][/accounts/{id}] 계정 수정 - 정상 작동")
    @Test
    void accountPutTest1() throws Exception {
        // given

        // when & then
        mvc.perform(
                put(prefix + "/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(AccountUpdateRequest.from(fixture.getDtos(2))))
                )
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @DisplayName("[put][/accounts/{id}] 계정 수정 - 존재하지 않는 id")
    @Test
    void accountPutTest2() throws Exception {
        // given
        willThrow(new InstaApplicationException(ErrorCode.ID_NOT_FOUNDED))
                .given(service).accountUpdate(any(Long.class), any(AccountDto.class), any(UserDetails.class));

        // when & then
        mvc.perform(
                put(prefix + "/accounts/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(AccountUpdateRequest.from(fixture.getDtos(2))))
                )
                .andDo(print())
                .andExpect(status().is(ErrorCode.ID_NOT_FOUNDED.getStatus().value()))
        ;
    }

    @DisplayName("[put][/accounts/{id}] 계정 수정 - 허용되지 않은 접근 권한 - 당사자가 아닌 경우")
    @Test
    @Fixture.SetMockOther
    void accountPutTest3() throws Exception {
        // given
        willThrow(new InstaApplicationException(ErrorCode.OWNERSHIP_NOT_FOUNDED))
                .given(service).accountUpdate(any(Long.class), any(AccountDto.class), any(UserDetails.class));

        // when & then
        mvc.perform(
                put(prefix + "/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(AccountUpdateRequest.from(fixture.getDtos(1))))
                )
                .andDo(print())
                .andExpect(status().is(ErrorCode.OWNERSHIP_NOT_FOUNDED.getStatus().value()))
        ;
    }

    @DisplayName("[put][/accounts/{id}] 계정 수정 - 정상 작동 - Admin 접근 권한")
    @Test
    @Fixture.SetMockAdmin
    void accountPutTest4() throws Exception {
        // given

        // when & then
        mvc.perform(
                put(prefix + "/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(AccountUpdateRequest.from(fixture.getDtos(2))))
                )
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @DisplayName("[delete][/accounts/{id}] 계정 삭제 - 정상 작동")
    @Test
    void accountDeleteTest1() throws Exception {
        // given

        // when & then
        mvc.perform(delete(prefix + "/accounts/1"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @DisplayName("[delete][/accounts/{id}] 계정 삭제 - 존재하지 않는 id")
    @Test
    void accountDeleteTest2() throws Exception {
        // given
        willThrow(new InstaApplicationException(ErrorCode.ID_NOT_FOUNDED))
                .given(service).accountDelete(any(Long.class), any(UserDetails.class));

        // when & then
        mvc.perform(delete(prefix + "/accounts/2"))
                .andDo(print())
                .andExpect(status().is(ErrorCode.ID_NOT_FOUNDED.getStatus().value()))
        ;
    }

    @DisplayName("[delete][/accounts/{id}] 계정 삭제 - 허용되지 않은 접근 권한 - 당사자가 아닌 경우")
    @Test
    @Fixture.SetMockOther
    void accountDeleteTest3() throws Exception {
        // given
        willThrow(new InstaApplicationException(ErrorCode.OWNERSHIP_NOT_FOUNDED))
                .given(service).accountDelete(any(Long.class), any(UserDetails.class));

        // when & then
        mvc.perform(delete(prefix + "/accounts/1"))
                .andDo(print())
                .andExpect(status().is(ErrorCode.OWNERSHIP_NOT_FOUNDED.getStatus().value()))
        ;
    }

    @DisplayName("[delete][/accounts/{id}] 계정 삭제 - 정상 작동 - Admin 접근 권한")
    @Test
    @Fixture.SetMockAdmin
    void accountDeleteTest4() throws Exception {
        // given

        // when & then
        mvc.perform(delete(prefix + "/accounts/1"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @DisplayName("[get][/accounts?NickName=:nickname] 유저 검색 - 정상 작동")
    @Test
    @WithAnonymousUser
    void accountSearchTest1() throws Exception {
        // given
        given(service.loadByNickName(any(), any())).willReturn(Page.empty());

        // when & then
        mvc.perform(
                get(prefix + "/accounts")
                        .param("nickName", "sad")
                )
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @DisplayName("[get][/accounts/{Id}/following/counts] 해당 유저를 팔로우한 유저 검색 - 정상 작동")
    @Test
    @WithAnonymousUser
    void accountFollowingTest1() throws Exception {
        // given

        // when & then
        mvc.perform(get(prefix + "/accounts/1/following/counts"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @DisplayName("[get][/accounts/{Id}/follower/counts] 해당 유저가 팔로우한 유저 검색 - 정상 작동")
    @Test
    @WithAnonymousUser
    void accountFollowerTest1() throws Exception {
        // given

        // when & then
        mvc.perform(get(prefix + "/accounts/1/follower/counts"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @DisplayName("[post][/accounts/follow/{follower_id}] follower_Id를 팔로우 - 정상 작동")
    @Test
    void accountFollowerTest2() throws Exception {
        // given

        // when & then
        mvc.perform(post(prefix + "/accounts/follow/6"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @DisplayName("[delete][/accounts/follow/{follower_id}] follower_Id를 언팔로우 - 정상 작동")
    @Test
    void accountFollowTest3() throws Exception {
        // given

        // when & then
        mvc.perform(delete(prefix + "/accounts/follow/6"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @DisplayName("[post][/accounts/follow/{followee_Id}] followee_Id를 팔로우 - 이미 팔로우되어 있음.")
    @Test
    void accountFollowTest4() throws Exception {
        // given
        willThrow(new InstaApplicationException(ErrorCode.DUPLICATED_FOLLOW))
                .given(service).doFollow(any(UserDetails.class), any(Long.class));

        // when & then
        mvc.perform(post(prefix + "/accounts/follow/6"))
                .andDo(print())
                .andExpect(status().is(ErrorCode.DUPLICATED_FOLLOW.getStatus().value()))
        ;
    }

    @DisplayName("[delete][/accounts/follow/{followee_Id}] followee_Id를 언팔로우 - 해당 팔로우 사실이 없음.")
    @Test
    void accountFollowTest5() throws Exception {
        // given
        willThrow(new InstaApplicationException(ErrorCode.FOLLOW_NOT_FOUNDED))
                .given(service).undoFollow(any(UserDetails.class), any(Long.class));

        // when & then
        mvc.perform(delete(prefix + "/accounts/1/follow/6"))
                .andDo(print())
                .andExpect(status().is(ErrorCode.FOLLOW_NOT_FOUNDED.getStatus().value()))
        ;
    }

    @DisplayName("[get][/accounts/{Id}/articles/counts] 유저가 작성한 글 갯수 조회 - 정상 작동")
    @Test
    @WithAnonymousUser
    void accountArticlesTest1() throws Exception {
        // given

        // when & then
        mvc.perform(get(prefix + "/accounts/1/articles/counts"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @DisplayName("[get][/accounts/{Id}/articles/follow] 유저가 팔로우한 유저들이 작성한 글 조회 - 정상 작동")
    @Test
    @WithAnonymousUser
    void accountArticlesTest2() throws Exception {
        // given
        given(service.loadFeedById(any(Long.class), any(Pageable.class))).willReturn(Page.empty());

        // when & then
        mvc.perform(get(prefix + "/accounts/1/articles/follow"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @DisplayName("[get][/accounts/{Id}/articles/recommended] 추천된 글 조회 - 정상 작동")
    @Test
    void accountArticlesTest3() throws Exception {
        // given
        given(service.loadExploreById(any(Long.class), any(Pageable.class), any(UserDetails.class))).willReturn(Page.empty());

        // when & then
        mvc.perform(get(prefix + "/accounts/1/articles/recommended"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @DisplayName("[get][/accounts/{Id}/articles/recommended] 추천된 글 조회 - 비로그인 유저")
    @Test
    @WithAnonymousUser
    void accountArticlesTest4() throws Exception {
        // given

        // when & then
        mvc.perform(get(prefix + "/accounts/1/articles/recommended"))
                .andDo(print())
                .andExpect(status().is(ErrorCode.OWNERSHIP_NOT_FOUNDED.getStatus().value()))
        ;
    }

    @DisplayName("[get][/accounts/{Id}/articles/recommended] 추천된 글 조회 - 권한 없는 유저의 접근")
    @Test
    @Fixture.SetMockOther
    void accountArticlesTest5() throws Exception {
        // given
        willThrow(new InstaApplicationException(ErrorCode.OWNERSHIP_NOT_FOUNDED)).given(service)
                .loadExploreById(any(Long.class), any(Pageable.class), any(UserDetails.class));

        // when & then
        mvc.perform(get(prefix + "/accounts/1/articles/recommended"))
                .andDo(print())
                .andExpect(status().is(ErrorCode.OWNERSHIP_NOT_FOUNDED.getStatus().value()))
        ;
    }

    @DisplayName("[get][/accounts/{Id}/articles/recommended] 추천된 글 조회 - 정상 작동 - Admin 계정.")
    @Test
    @Fixture.SetMockAdmin
    void accountArticlesTest6() throws Exception {
        // given
        given(service.loadExploreById(any(Long.class), any(Pageable.class), any(UserDetails.class)))
                .willReturn(Page.empty());

        // when & then
        mvc.perform(get(prefix + "/accounts/1/articles/recommended"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @DisplayName("[get][/accounts/{Id}/articles] 유저가 쓴 글들 조회. - 정상 작동")
    @Test
    @WithAnonymousUser
    void accountArticlesTest7() throws Exception {
        // given
        given(service.loadArticlesById(any(Long.class), any(Pageable.class)))
                .willReturn(Page.empty());

        // when & then
        mvc.perform(get(prefix + "/accounts/1/articles"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }
}