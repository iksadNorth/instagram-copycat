package me.iksadnorth.insta.fixture;

import me.iksadnorth.insta.model.dto.AccountDto;
import me.iksadnorth.insta.model.dto.ArticleDto;
import me.iksadnorth.insta.model.dto.FollowDto;
import me.iksadnorth.insta.type.RoleType;
import org.springframework.context.annotation.Profile;
import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Profile("test")
public class Fixture {
    private final List<AccountDto> userDtos;
    private final List<FollowDto> followDtos;
    private final List<ArticleDto> articleDtos;

    @WithMockUser(username = "iksad@gmail.com", roles = {"USER"})
    @Retention(RetentionPolicy.RUNTIME) @Target(ElementType.TYPE)
    public @interface SetMockUser {}

    @WithMockUser(username = "south@gmail.com", roles = {"USER"})
    @Retention(RetentionPolicy.RUNTIME) @Target(ElementType.METHOD)
    public @interface SetMockOther {}

    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    @Retention(RetentionPolicy.RUNTIME) @Target({ElementType.METHOD})
    public @interface SetMockAdmin {}

    private static class SingletonHelper{ private static final Fixture INSTANCE = new Fixture(); }
    public static Fixture getInstance() { return SingletonHelper.INSTANCE; }

    public Fixture() {
        this.userDtos = new ArrayList<>();
        // 0 - 주로 미리 가입된 사용자를 가정.
        // SetMockUser 계정.
        userDtos.add(AccountDto.builder()
                        .id(1L)
                        .email("iksad@gmail.com")
                        .userName("iksad")
                        .nickName("익섿")
                        .password("q1w2e3r4")
                        .dateOfBirth(LocalDateTime.now())
                        .role(RoleType.ROLE_USER)
                        .build());
        // 1 - 존재하지 않는 계정. 잘못된 정보 기입 상황을 가정.
        // - 혹은 계정 가입을 위한 정보 기입도 가정.
        // - 혹은 계정 수정을 위한 정보 기입도 가정.
        userDtos.add(AccountDto.builder()
                        .id(2L)
                        .email("north@gmail.com")   // 0과 email이 다름.
                        .userName("iksad")
                        .nickName("익섿")
                        .password("q1w2e3r4")
                        .dateOfBirth(LocalDateTime.now())
                        .build());
        // 2 - 1번에서 잘못된 비밀 번호로 접근 시. 잘못된 정보 기입 상황을 가정.
        userDtos.add(AccountDto.builder()
                        .id(3L)
                        .email("iksad@gmail.com")
                        .userName("iksad")
                        .nickName("익섿")
                        .password("1234")           // 0과 비밀번호가 다름.
                        .dateOfBirth(LocalDateTime.now())
                        .build());
        // 3 - 0번과 마찬가지로 이미 존재하지만 0번의 계정이 아닌 계정. 0번 계정으로의 접근을 불가능한 외부자의 역할을 맡을 예정.
        // - 혹은 1번 유저를 팔로우한 유저.
        // SetMockOther 계정.
        userDtos.add(AccountDto.builder()
                        .id(4L)
                        .email("south@gmail.com")
                        .userName("south")
                        .nickName("남쪽")
                        .password("1234")
                        .dateOfBirth(LocalDateTime.now())
                        .role(RoleType.ROLE_USER)
                        .build());
        // 4 - Admin 계정.
        // SetMockAdmin 계정.
        userDtos.add(AccountDto.builder()
                        .id(5L)
                        .email("admin@gmail.com")
                        .role(RoleType.ROLE_ADMIN)       // ADMIN권한을 가지고 있음.
                        .build());
        // 5 - 0번과 마찬가지로 이미 존재하지만 0번의 계정이 아닌 계정. 0번 계정으로의 접근을 불가능한 외부자의 역할을 맡을 예정.
        // - 혹은 1번 유저가 팔로우한 유저.
        userDtos.add(AccountDto.builder()
                        .id(6L)
                        .email("west@gmail.com")
                        .userName("west")
                        .nickName("서쪽")
                        .password("1234")
                        .dateOfBirth(LocalDateTime.now())
                        .role(RoleType.ROLE_USER)
                        .build());

        this.followDtos = new ArrayList<>();
        followDtos.add(FollowDto.builder()
                        .id(1L)
                        .follower(userDtos.get(0))
                        .followee(userDtos.get(5))
                        .build());
        followDtos.add(FollowDto.builder()
                        .id(2L)
                        .follower(userDtos.get(3))
                        .followee(userDtos.get(0))
                        .build());
        this.articleDtos = new ArrayList<>();
        // 0 - 미리 저장되어 있는 계시글 가정.
        // SetMockUser 계정.
        articleDtos.add(ArticleDto.builder()
                        .id(1L)
                        .account(userDtos.get(0))
                        .content("1번째 게시물 글 내용.")
                        .isAllowedComments(true)
                        .isHideLikesAndViews(true)
                        .build());
        // 1 - 수정 시, 사용될 게시하기 직전의 계시글 가정.
        // SetMockUser 계정.
        articleDtos.add(ArticleDto.builder()
                .id(1L)
                .account(userDtos.get(0))
                .content("1번째 게시물 글 내용 수정.")
                .build());
        // 2 - 수정 시, 사용될 게시하기 직전의 계시글 가정. 단, 작성자가 요청한 것이 아닌 경우.
        // SetMockOther 계정.
        articleDtos.add(ArticleDto.builder()
                .id(1L)
                .account(userDtos.get(3))
                .content("1번째 게시물 글 내용 수정.")
                .build());
        // 3 - 수정 시, 사용될 게시하기 직전의 계시글 가정. 단, 운영자가 요청한 것인 경우.
        // SetMockAdmin 계정.
        articleDtos.add(ArticleDto.builder()
                .id(1L)
                .account(userDtos.get(4))
                .content("1번째 게시물 글 내용 수정.")
                .build());
    }

    public AccountDto getDtos(int idx) { return userDtos.get(idx); }
    public FollowDto getFollowDtos(int idx) { return followDtos.get(idx); }
    public ArticleDto getArticleDtos(int idx) { return articleDtos.get(idx); }
}
