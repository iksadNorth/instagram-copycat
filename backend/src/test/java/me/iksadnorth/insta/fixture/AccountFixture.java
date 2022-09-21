package me.iksadnorth.insta.fixture;

import me.iksadnorth.insta.model.dto.AccountDto;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@TestConfiguration
@Profile("test")
public class AccountFixture {
    private final List<AccountDto> dtos;

    public AccountFixture() {
        this.dtos = new ArrayList<>();
        // 1
        dtos.add(AccountDto.builder()
                        .id(1L)
                        .email("iksad@gmail.com")
                        .userName("iksad")
                        .nickName("익섿")
                        .password("q1w2e3r4")
                        .dataOfBirth(LocalDateTime.now())
                        .build());
        // 2
        dtos.add(AccountDto.builder()
                        .id(2L)
                        .email("north@gmail.com")   // 1과 email이 다름.
                        .userName("iksad")
                        .nickName("익섿")
                        .password("q1w2e3r4")
                        .dataOfBirth(LocalDateTime.now())
                        .build());
        // 3
        dtos.add(AccountDto.builder()
                        .id(3L)
                        .email("iksad@gmail.com")
                        .userName("iksad")
                        .nickName("익섿")
                        .password("1234")           // 1과 비밀번호가 다름.
                        .dataOfBirth(LocalDateTime.now())
                        .build());
        // 4
        dtos.add(AccountDto.builder()
                        .id(4L)
                        .email("iksad@gmail.com")
                        .userName("iksad")
                        .nickName("익섿")
                        .password("1234")           // 1과 비밀번호가 다름.
                        .dataOfBirth(LocalDateTime.now())
                        .build());
    }

    @Bean public List<AccountDto> dtos() { return dtos; }
}
