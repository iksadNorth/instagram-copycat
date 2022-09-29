package me.iksadnorth.insta.service;

import lombok.extern.slf4j.Slf4j;
import me.iksadnorth.insta.config.properies.JwtProperties;
import me.iksadnorth.insta.exception.ErrorCode;
import me.iksadnorth.insta.exception.InstaApplicationException;
import me.iksadnorth.insta.fixture.AccountFixture;
import me.iksadnorth.insta.model.dto.AccountDto;
import me.iksadnorth.insta.repository.AccountRepository;
import me.iksadnorth.insta.utils.JwtTokenUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;

@Slf4j
@DisplayName("AccountService 관련 테스트")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AccountServiceTest {
    @Mock AccountRepository repo;
    @Mock JwtProperties jwtProperties;
    private static MockedStatic<JwtTokenUtils> mockJwtTokenUtils;
    private final AccountFixture fixture = new AccountFixture();
    @InjectMocks AccountService service;

    @BeforeAll
    public static void beforeALl() {
        mockJwtTokenUtils = mockStatic(JwtTokenUtils.class);
    }

    @AfterAll
    public static void afterAll() {
        mockJwtTokenUtils.close();
    }

    @BeforeEach
    void setFixture() {
        given(repo.findByEmail(eq(fixture.getDtos(0).getEmail()))).willReturn(Optional.of(fixture.getDtos(0).toEntity()));
        given(repo.findByEmail(eq(fixture.getDtos(3).getEmail()))).willReturn(Optional.of(fixture.getDtos(3).toEntity()));
        given(repo.findByEmail(eq(fixture.getDtos(5).getEmail()))).willReturn(Optional.of(fixture.getDtos(5).toEntity()));

        given(JwtTokenUtils.getAccessToken(any(String.class), any(String.class), any(Long.class))).willReturn("token");
    }

    @DisplayName("로그인 테스트 - 정상 작동.")
    @Test
    void loginTest1() {
        // given
        AccountDto accountDto = fixture.getDtos(0);

        // when & then
        assertDoesNotThrow(() -> service.login(accountDto.getEmail(), accountDto.getPassword()));

    }

    @DisplayName("로그인 테스트 - User Not Founded.")
    @Test
    void loginTest2() {
        // given
        AccountDto accountDto = fixture.getDtos(1);

        // when & then
        InstaApplicationException e = assertThrows(InstaApplicationException.class,
                () -> service.login(accountDto.getEmail(), accountDto.getPassword())
        );
        assertThat(e.getErrorCode()).isEqualTo(ErrorCode.USER_NOT_FOUNDED);

    }

    @DisplayName("로그인 테스트 - Invalid password.")
    @Test
    void loginTest3() {
        // given
        AccountDto accountDto = fixture.getDtos(0);

        // when & then
        InstaApplicationException e = assertThrows(InstaApplicationException.class,
                () -> service.login(accountDto.getEmail(), "blah blah")
        );
        assertThat(e.getErrorCode()).isEqualTo(ErrorCode.INVALID_PASSWORD);

    }
}