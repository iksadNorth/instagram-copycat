package me.iksadnorth.insta.service;

import lombok.extern.slf4j.Slf4j;
import me.iksadnorth.insta.config.properies.JwtProperties;
import me.iksadnorth.insta.exception.ErrorCode;
import me.iksadnorth.insta.exception.InstaApplicationException;
import me.iksadnorth.insta.fixture.Fixture;
import me.iksadnorth.insta.model.dto.AccountDto;
import me.iksadnorth.insta.model.entity.Article;
import me.iksadnorth.insta.repository.AccountRepository;
import me.iksadnorth.insta.repository.ArticleRepository;
import me.iksadnorth.insta.repository.FollowRepository;
import me.iksadnorth.insta.utils.JwtTokenUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mockStatic;

@Slf4j
@DisplayName("AccountService 관련 테스트")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AccountServiceTest {
    @Mock AccountRepository repo;
    @Mock ArticleRepository articleRepo;
    @Mock FollowRepository followRepo;
    @Mock JwtProperties jwtProperties;
    private static MockedStatic<JwtTokenUtils> mockJwtTokenUtils;
    private final Fixture fixture = new Fixture();
    @InjectMocks AccountService service;
    @Mock ArticleService articleService;

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
        given(repo.findByEmail(eq(fixture.getDtos(0).getEmail())))
                .willReturn(Optional.of(fixture.getDtos(0).toEntity()));
        given(repo.findByEmail(eq(fixture.getDtos(3).getEmail())))
                .willReturn(Optional.of(fixture.getDtos(3).toEntity()));
        given(repo.findByEmail(eq(fixture.getDtos(5).getEmail())))
                .willReturn(Optional.of(fixture.getDtos(5).toEntity()));

        given(repo.findById(eq(fixture.getDtos(0).getId())))
                .willReturn(Optional.of(fixture.getDtos(0).toEntity()));
        given(repo.findById(eq(fixture.getDtos(3).getId())))
                .willReturn(Optional.of(fixture.getDtos(3).toEntity()));
        given(repo.findById(eq(fixture.getDtos(5).getId())))
                .willReturn(Optional.of(fixture.getDtos(5).toEntity()));

        given(JwtTokenUtils.getAccessToken(any(String.class), any(String.class), any(Long.class)))
                .willReturn("token");
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

    @DisplayName("계정 생성 테스트 - 정상 작동.")
    @Test
    void accountCreateTest1() {
        // given
        AccountDto accountDto = fixture.getDtos(1);

        // when
        service.accountCreate(accountDto);

        // then
        then(repo).should().save(accountDto.toEntity());

    }

    @DisplayName("계정 생성 테스트 - 해당 계정이 이미 존재함.")
    @Test
    void accountCreateTest2() {
        // given
        AccountDto accountDto = fixture.getDtos(0);
        log.debug(accountDto.getEmail());

        // when & then
        InstaApplicationException e = assertThrows(
                InstaApplicationException.class,
                () -> service.accountCreate(accountDto)
        );

        assertThat(e.getErrorCode()).isEqualTo(ErrorCode.DUPLICATED_USER);

    }

    @DisplayName("단일 유저 검색 테스트 - 정상 작동.")
    @Test
    void loadByIdTest1() {
        // given
        AccountDto accountDto = fixture.getDtos(0);
        Long id = accountDto.getId();

        // when & then
        assertDoesNotThrow(() -> service.loadById(id));

    }

    @DisplayName("단일 유저 검색 테스트 - 해당 Id값이 존재하지 않음.")
    @Test
    void loadByIdTest2() {
        // given
        AccountDto accountDto = fixture.getDtos(1);
        Long id = accountDto.getId();

        // when & then
        InstaApplicationException e = assertThrows(
                InstaApplicationException.class,
                () -> service.loadById(id)
        );

        assertThat(e.getErrorCode()).isEqualTo(ErrorCode.USER_NOT_FOUNDED);

    }

    @DisplayName("계정 수정 테스트 - 정상 작동.")
    @Test
    void accountUpdateTest1() {
        // given
        AccountDto userLogged = fixture.getDtos(0);
        AccountDto oldDto = fixture.getDtos(0);
        AccountDto newDto = fixture.getDtos(1);
        Long id = oldDto.getId();

        // when & then
        assertDoesNotThrow(() -> service.accountUpdate(id, newDto, userLogged));

    }

    @DisplayName("계정 수정 테스트 - 존재하지 않는 id.")
    @Test
    void accountUpdateTest2() {
        // given
        AccountDto userLogged = fixture.getDtos(0);
        AccountDto oldDto = fixture.getDtos(0);
        AccountDto newDto = fixture.getDtos(1);
        Long id = newDto.getId();

        // when & then
        InstaApplicationException e = assertThrows(
                InstaApplicationException.class,
                () -> service.accountUpdate(id, newDto, userLogged)
        );

        assertThat(e.getErrorCode()).isEqualTo(ErrorCode.USER_NOT_FOUNDED);

    }

    @DisplayName("계정 수정 테스트 - 인가받지 못한 유저.")
    @Test
    void accountUpdateTest3() {
        // given
        AccountDto userLogged = fixture.getDtos(3);
        AccountDto oldDto = fixture.getDtos(0);
        AccountDto newDto = fixture.getDtos(1);
        Long id = oldDto.getId();

        // when & then
        InstaApplicationException e = assertThrows(
                InstaApplicationException.class,
                () -> service.accountUpdate(id, newDto, userLogged)
        );

        assertThat(e.getErrorCode()).isEqualTo(ErrorCode.NOT_BELONGING_TO_YOU);

    }

    @DisplayName("계정 수정 테스트 - 정상 작동 - ADMIN 계정.")
    @Test
    void accountUpdateTest4() {
        // given
        AccountDto userLogged = fixture.getDtos(4);
        AccountDto oldDto = fixture.getDtos(0);
        AccountDto newDto = fixture.getDtos(1);
        Long id = oldDto.getId();

        // when & then
        assertDoesNotThrow(() -> service.accountUpdate(id, newDto, userLogged));
    }

    @DisplayName("계정 수정 테스트 - 정상 작동.")
    @Test
    void accountDeleteTest1() {
        // given
        AccountDto userLogged = fixture.getDtos(0);
        AccountDto trgDto = fixture.getDtos(0);
        Long id = trgDto.getId();

        // when & then
        assertDoesNotThrow(() -> service.accountDelete(id, userLogged));

    }

    @DisplayName("계정 삭제 테스트 - 존재하지 않는 id.")
    @Test
    void accountDeleteTest2() {
        // given
        AccountDto userLogged = fixture.getDtos(0);
        AccountDto trgDto = fixture.getDtos(1);
        Long id = trgDto.getId();

        // when & then
        InstaApplicationException e = assertThrows(
                InstaApplicationException.class,
                () -> service.accountDelete(id, userLogged)
        );

        assertThat(e.getErrorCode()).isEqualTo(ErrorCode.USER_NOT_FOUNDED);

    }

    @DisplayName("계정 삭제 테스트 - 인가받지 못한 유저.")
    @Test
    void accountDeleteTest3() {
        // given
        AccountDto userLogged = fixture.getDtos(3);
        AccountDto trgDto = fixture.getDtos(0);
        Long id = trgDto.getId();

        // when & then
        InstaApplicationException e = assertThrows(
                InstaApplicationException.class,
                () -> service.accountDelete(id, userLogged)
        );

        assertThat(e.getErrorCode()).isEqualTo(ErrorCode.NOT_BELONGING_TO_YOU);

    }

    @DisplayName("계정 삭제 테스트 - 정상 작동 - ADMIN 계정.")
    @Test
    void accountDeleteTest4() {
        // given
        AccountDto userLogged = fixture.getDtos(4);
        AccountDto trgDto = fixture.getDtos(0);
        Long id = trgDto.getId();

        // when & then
        assertDoesNotThrow(() -> service.accountDelete(id, userLogged));
    }

    @DisplayName("팔로우 생성 테스트 - 정상 작동")
    @Test
    void doFollowTest1() {
        // given
        AccountDto userLogged = fixture.getDtos(0);
        AccountDto trgDto = fixture.getDtos(5);
        Long id = trgDto.getId();

        // when & then
        assertDoesNotThrow(() -> service.doFollow(userLogged, id));
    }

    @DisplayName("추천 게시글 목록 로드 테스트 - 정상 작동.")
    @Test
    void loadExploreByIdTest1() {
        // given
        AccountDto userLogged = fixture.getDtos(0);
        AccountDto trgDto = fixture.getDtos(0);
        Long id = trgDto.getId();

        Pageable pageable = PageRequest.of(0, 10);
        given(articleRepo.findRandListById(any(), any())).willReturn(List.of());
        given(articleService.countsWith((Stream<Article>) any(), any())).willReturn(Page.empty());

        // when & then
        assertDoesNotThrow(() -> service.loadExploreById(id, pageable, userLogged));

    }

    @DisplayName("추천 게시글 목록 로드 테스트 - 인가받지 못한 유저.")
    @Test
    void loadExploreByIdTest3() {
        // given
        AccountDto userLogged = fixture.getDtos(3);
        AccountDto trgDto = fixture.getDtos(0);
        Long id = trgDto.getId();

        Pageable pageable = PageRequest.of(0, 10);
        given(articleRepo.findRandListById(any(), any())).willReturn(List.of());

        // when & then
        InstaApplicationException e = assertThrows(
                InstaApplicationException.class,
                () -> service.accountDelete(id, userLogged)
        );

        assertThat(e.getErrorCode()).isEqualTo(ErrorCode.NOT_BELONGING_TO_YOU);

    }
}