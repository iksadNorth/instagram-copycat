package me.iksadnorth.insta.model.dto;

import lombok.extern.slf4j.Slf4j;
import me.iksadnorth.insta.fixture.Fixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
@DisplayName("AccountDto 속에 존재하는 각 메소드 확인 테스트")
class AccountDtoTest {
    private final Fixture fixture = new Fixture();

    @Test
    void overWriteWith() {
        // given
        AccountDto dtosTo = fixture.getDtos(0);
        AccountDto dtosFrom = fixture.getDtos(1);

        // when
        AccountDto dtoOverwritten = dtosTo.overWriteWith(dtosFrom);

        // then
        log.debug(dtoOverwritten.toString());

    }
}