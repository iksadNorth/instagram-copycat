package me.iksadnorth.insta.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.iksadnorth.insta.exception.ErrorCode;
import me.iksadnorth.insta.fixture.FixturePackageScan;
import me.iksadnorth.insta.model.dto.AccountDto;
import me.iksadnorth.insta.model.request.AccountLoginRequest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@DisplayName("딱히 특정 소속에 속하지 않은 controller 테스트")
@ActiveProfiles("test")
@FixturePackageScan
@WebMvcTest(InstaController.class)
class InstaControllerTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;
    
    private final String prefix = "/api/v1";

    @Resource List<AccountDto> dtos;

    @InjectMocks InstaController controller;
    @MockBean AccountService service;

    @BeforeEach
    void setFixture() {
        // given
        given(service.loadUserByUsername(eq(dtos.get(1).getEmail()))).willReturn(dtos.get(1));
    }

    @DisplayName("[post][/login] 로그인 테스트 - 정상 작동")
    @Test
    @WithAnonymousUser
    void loginTest1() throws Exception {
        // given

        // when & then
        mvc.perform(
                post(prefix + "/login")
                        .content(mapper.writeValueAsBytes(AccountLoginRequest.from(dtos.get(1))))
                        .contentType(MediaType.APPLICATION_JSON)
                        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..token").exists());
    }

    @DisplayName("[post][/login] 로그인 테스트 - User Not Founded")
    @Test
    @WithAnonymousUser
    void loginTest2() throws Exception {
        // given

        // when & then
        mvc.perform(
                post(prefix + "/login")
                        .content(mapper.writeValueAsBytes(AccountLoginRequest.from(dtos.get(2))))
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().is(ErrorCode.USER_NOT_FOUNDED.getStatus().value()));
    }

    @DisplayName("[post][/login] 로그인 테스트 - Invaild Password")
    @Test
    @WithAnonymousUser
    void loginTest3() throws Exception {
        // given

        // when & then
        mvc.perform(
                post(prefix + "/login")
                        .content(mapper.writeValueAsBytes(AccountLoginRequest.from(dtos.get(3))))
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_PASSWORD.getStatus().value()));
    }
}