package me.iksadnorth.insta.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.iksadnorth.insta.exception.ErrorCode;
import me.iksadnorth.insta.fixture.AccountFixture;
import me.iksadnorth.insta.fixture.FixturePackageScan;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AccountFixture.SetMockUser
@FixturePackageScan
@Slf4j
@DisplayName("AccountController 테스트")
@ActiveProfiles("test")
@WebMvcTest(AccountController.class)
class AccountControllerTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;

    private final String prefix = "/api/v1";

    @Resource
    List<AccountDto> dtos;

    @InjectMocks AccountController controller;
    @MockBean AccountService service;

    @BeforeEach
    void setFixture() {
        // given
        // 접근할 계정. - 당사자
        given(service.loadUserByUsername(eq(dtos.get(1).getEmail()))).willReturn(dtos.get(1));
        given(service.getId(eq(dtos.get(1).getId()))).willReturn(dtos.get(1));
    }

    @DisplayName("[post][/accounts] 계정 생성 - 정상 작동")
    @Test
    @WithAnonymousUser
    void accountCreateTest1() throws Exception {
        // given

        // when & then
        mvc.perform(
                        post(prefix)
                                .content(mapper.writeValueAsBytes(AccountCreateRequest.from(dtos.get(2))))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("[post][/accounts] 계정 생성 - 이미 존재하는 유저")
    @Test
    @WithAnonymousUser
    void accountCreateTest2() throws Exception {
        // given

        // when & then
        mvc.perform(
                        post(prefix)
                                .content(mapper.writeValueAsBytes(AccountCreateRequest.from(dtos.get(1))))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().is(ErrorCode.DUPLICATED_USER_NAME.getStatus().value()));
    }

    @DisplayName("[get][/accounts/{id}] 계정 생성 - 정상 작동")
    @Test
    @WithAnonymousUser
    void accountReadTest1() throws Exception {
        // given

        // when & then
        mvc.perform(post(prefix + "/accounts/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..%s", "nickName").exists())
                .andExpect(jsonPath("$..%s", "introduction").exists())
                .andExpect(jsonPath("$..%s", "views").exists())
                .andExpect(jsonPath("$..%s", "followers").exists())
                .andExpect(jsonPath("$..%s", "followings").exists())
                .andExpect(jsonPath("$..%s", "images").exists())
        ;
    }

    @DisplayName("[get][/accounts/{id}] 계정 생성 - 존재하지 않는 id")
    @Test
    @WithAnonymousUser
    void accountReadTest2() throws Exception {
        // given

        // when & then
        mvc.perform(post(prefix + "/accounts/1"))
                .andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_ID.getStatus().value()))
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
                        .content(mapper.writeValueAsBytes(AccountUpdateRequest.from(dtos.get(2))))
                )
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @DisplayName("[put][/accounts/{id}] 계정 수정 - 존재하지 않는 id")
    @Test
    void accountPutTest2() throws Exception {
        // given

        // when & then
        mvc.perform(
                put(prefix + "/accounts/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(AccountUpdateRequest.from(dtos.get(2))))
                )
                .andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_ID.getStatus().value()))
        ;
    }

    @DisplayName("[put][/accounts/{id}] 계정 수정 - 허용되지 않은 접근 권한 - 당사자가 아닌 경우")
    @Test
    @AccountFixture.SetMockOther
    void accountPutTest3() throws Exception {
        // given

        // when & then
        mvc.perform(
                put(prefix + "/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(AccountUpdateRequest.from(dtos.get(2))))
                )
                .andDo(print())
                .andExpect(status().is(ErrorCode.UNAUTHORIZED.getStatus().value()))
        ;
    }

    @DisplayName("[put][/accounts/{id}] 계정 수정 - 정상 작동 - Admin 접근 권한")
    @Test
    @AccountFixture.SetMockAdmin
    void accountPutTest4() throws Exception {
        // given

        // when & then
        mvc.perform(
                put(prefix + "/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(AccountUpdateRequest.from(dtos.get(2))))
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

        // when & then
        mvc.perform(delete(prefix + "/accounts/2"))
                .andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_ID.getStatus().value()))
        ;
    }

    @DisplayName("[delete][/accounts/{id}] 계정 삭제 - 허용되지 않은 접근 권한 - 당사자가 아닌 경우")
    @Test
    @AccountFixture.SetMockOther
    void accountDeleteTest3() throws Exception {
        // given

        // when & then
        mvc.perform(delete(prefix + "/accounts/1"))
                .andDo(print())
                .andExpect(status().is(ErrorCode.UNAUTHORIZED.getStatus().value()))
        ;
    }

    @DisplayName("[delete][/accounts/{id}] 계정 삭제 - 정상 작동 - Admin 접근 권한")
    @Test
    @AccountFixture.SetMockAdmin
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

        // when & then
        mvc.perform(
                get(prefix + "/accounts")
                        .param("NickName", "sad")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..%s", "nickName").exists())
        ;
    }

    @DisplayName("[get][/accounts/{Id}/follower/counts] 해당 유저를 팔로우한 유저 검색 - 정상 작동")
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

    @DisplayName("[get][/accounts/{Id}/follower?follower=:nickname] 해당 유저를 팔로우한 특정 유저 검색 - 정상 작동")
    @Test
    @WithAnonymousUser
    void accountFollowerTest2() throws Exception {
        // given

        // when & then
        mvc.perform(
                        get(prefix + "/accounts/1/follower")
                                .param("NickName", "쪽")
                )
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @DisplayName("[get][/accounts/{Id}/followee/counts] 해당 유저가 팔로우한 유저 검색 - 정상 작동")
    @Test
    @WithAnonymousUser
    void accountFolloweeTest1() throws Exception {
        // given

        // when & then
        mvc.perform(get(prefix + "/accounts/1/followee/counts"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @DisplayName("[get][/accounts/{Id}/followee?followee=:nickname] 해당 유저를 팔로우한 특정 유저 검색 - 정상 작동")
    @Test
    @WithAnonymousUser
    void accountFolloweeTest2() throws Exception {
        // given

        // when & then
        mvc.perform(
                        get(prefix + "/accounts/1/followee")
                                .param("NickName", "쪽")
                )
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @DisplayName("[post][/accounts/{Id}/followee/{followee_Id}] followee_Id를 팔로우 - 정상 작동")
    @Test
    void accountFolloweeTest3() throws Exception {
        // given

        // when & then
        mvc.perform(post(prefix + "/accounts/1/followee/6"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @DisplayName("[delete][/accounts/{Id}/followee/{followee_Id}] followee_Id를 언팔로우 - 정상 작동")
    @Test
    void accountFolloweeTest4() throws Exception {
        // given

        // when & then
        mvc.perform(delete(prefix + "/accounts/1/followee/6"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @DisplayName("[post][/accounts/{Id}/followee/{followee_Id}] followee_Id를 팔로우 - 로그인 되지 않음.")
    @Test
    @WithAnonymousUser
    void accountFolloweeTest5() throws Exception {
        // given

        // when & then
        mvc.perform(post(prefix + "/accounts/1/followee/6"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @DisplayName("[delete][/accounts/{Id}/followee/{followee_Id}] followee_Id를 언팔로우 - 로그인 되지 않음.")
    @Test
    @WithAnonymousUser
    void accountFolloweeTest6() throws Exception {
        // given

        // when & then
        mvc.perform(delete(prefix + "/accounts/1/followee/6"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @DisplayName("[post][/accounts/{Id}/followee/{followee_Id}] followee_Id를 팔로우 - 권한 없음.")
    @Test
    @AccountFixture.SetMockOther
    void accountFolloweeTest7() throws Exception {
        // given

        // when & then
        mvc.perform(post(prefix + "/accounts/1/followee/6"))
                .andDo(print())
                .andExpect(status().is(ErrorCode.UNAUTHORIZED.getStatus().value()))
        ;
    }

    @DisplayName("[delete][/accounts/{Id}/followee/{followee_Id}] followee_Id를 언팔로우 - 권한 없음.")
    @Test
    @AccountFixture.SetMockOther
    void accountFolloweeTest8() throws Exception {
        // given

        // when & then
        mvc.perform(delete(prefix + "/accounts/1/followee/6"))
                .andDo(print())
                .andExpect(status().is(ErrorCode.UNAUTHORIZED.getStatus().value()))
        ;
    }

    @DisplayName("[post][/accounts/{Id}/followee/{followee_Id}] followee_Id를 팔로우 - 정상 작동")
    @Test
    @AccountFixture.SetMockAdmin
    void accountFolloweeTest9() throws Exception {
        // given

        // when & then
        mvc.perform(post(prefix + "/accounts/1/followee/6"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @DisplayName("[delete][/accounts/{Id}/followee/{followee_Id}] followee_Id를 언팔로우 - 정상 작동")
    @Test
    @AccountFixture.SetMockAdmin
    void accountFolloweeTest10() throws Exception {
        // given

        // when & then
        mvc.perform(delete(prefix + "/accounts/1/followee/6"))
                .andDo(print())
                .andExpect(status().isOk())
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

    @DisplayName("[get][/accounts/{Id}/articles/follow] 유저가 팔로우한 유저들이 작성한 글 갯수 조회 - 정상 작동")
    @Test
    @WithAnonymousUser
    void accountArticlesTest2() throws Exception {
        // given

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
                .andExpect(status().is(ErrorCode.UNAUTHORIZED.getStatus().value()))
        ;
    }

    @DisplayName("[get][/accounts/{Id}/articles/recommended] 추천된 글 조회 - 권한 없는 유저의 접근")
    @Test
    @AccountFixture.SetMockOther
    void accountArticlesTest5() throws Exception {
        // given

        // when & then
        mvc.perform(get(prefix + "/accounts/1/articles/recommended"))
                .andDo(print())
                .andExpect(status().is(ErrorCode.UNAUTHORIZED.getStatus().value()))
        ;
    }

    @DisplayName("[get][/accounts/{Id}/articles/recommended] 추천된 글 조회 - 정상 작동 - Admin 계정.")
    @Test
    @AccountFixture.SetMockAdmin
    void accountArticlesTest6() throws Exception {
        // given

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

        // when & then
        mvc.perform(get(prefix + "/accounts/1/articles"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }
}