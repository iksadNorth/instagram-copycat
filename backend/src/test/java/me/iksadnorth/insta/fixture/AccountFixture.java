package me.iksadnorth.insta.fixture;

import me.iksadnorth.insta.model.dto.AccountDto;
import me.iksadnorth.insta.type.RoleType;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@TestConfiguration
@Profile("test")
public class AccountFixture {
    private final List<AccountDto> dtos;

    @WithMockUser(username = "iksad@gmail.com", roles = {"USER"})
    @Retention(RetentionPolicy.RUNTIME) @Target(ElementType.TYPE)
    public @interface SetMockUser {}

    @WithMockUser(username = "south@gmail.com", roles = {"USER"})
    @Retention(RetentionPolicy.RUNTIME) @Target(ElementType.METHOD)
    public @interface SetMockOther {}

    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    @Retention(RetentionPolicy.RUNTIME) @Target({ElementType.METHOD})
    public @interface SetMockAdmin {}

    public AccountFixture() {
        this.dtos = new ArrayList<>();
        // 1 - 주로 미리 가입된 사용자를 가정.
        dtos.add(AccountDto.builder()
                        .id(1L)
                        .email("iksad@gmail.com")
                        .userName("iksad")
                        .nickName("익섿")
                        .password("q1w2e3r4")
                        .dataOfBirth(LocalDateTime.now())
                        .role(RoleType.USER)
                        .build());
        // 2 - 존재하지 않는 계정. 잘못된 정보 기입 상황을 가정.
        // - 혹은 계정 가입을 위한 정보 기입도 가정.
        // - 혹은 계정 수정을 위한 정보 기입도 가정.
        dtos.add(AccountDto.builder()
                        .id(2L)
                        .email("north@gmail.com")   // 1과 email이 다름.
                        .userName("iksad")
                        .nickName("익섿")
                        .password("q1w2e3r4")
                        .dataOfBirth(LocalDateTime.now())
                        .build());
        // 3 - 1번에서 잘못된 비밀 번호로 접근 시. 잘못된 정보 기입 상황을 가정.
        dtos.add(AccountDto.builder()
                        .id(3L)
                        .email("iksad@gmail.com")
                        .userName("iksad")
                        .nickName("익섿")
                        .password("1234")           // 1과 비밀번호가 다름.
                        .dataOfBirth(LocalDateTime.now())
                        .build());
        // 4 - 1번과 마찬가지로 이미 존재하지만 1번의 계정이 아닌 계정. 1번 계정으로의 접근을 불가능한 외부자의 역할을 맡을 예정.
        // - 혹은 1번 유저를 팔로우한 유저.
        dtos.add(AccountDto.builder()
                        .id(4L)
                        .email("south@gmail.com")
                        .userName("south")
                        .nickName("남쪽")
                        .password("1234")
                        .dataOfBirth(LocalDateTime.now())
                        .role(RoleType.USER)
                        .build());
        // 5 - Admin 계정.
        dtos.add(AccountDto.builder()
                        .id(5L)
                        .email("admin@gmail.com")
                        .role(RoleType.ADMIN)       // ADMIN권한을 가지고 있음.
                        .build());
        // 6 - 1번과 마찬가지로 이미 존재하지만 1번의 계정이 아닌 계정. 1번 계정으로의 접근을 불가능한 외부자의 역할을 맡을 예정.
        // - 혹은 1번 유저가 팔로우한 유저.
        dtos.add(AccountDto.builder()
                        .id(6L)
                        .email("west@gmail.com")
                        .userName("west")
                        .nickName("서쪽")
                        .password("1234")
                        .dataOfBirth(LocalDateTime.now())
                        .role(RoleType.USER)
                        .build());
    }

    @Bean public List<AccountDto> dtos() { return dtos; }
}
