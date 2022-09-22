package me.iksadnorth.insta.model.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.iksadnorth.insta.model.dto.AccountDto;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class AccountUpdateRequest {
    private final String email;
    private final String userName;
    private final String nickName;
    private final String password;
    private final LocalDateTime dataOfBirth;

    public static AccountUpdateRequest from(AccountDto dto) {
        return new AccountUpdateRequest(
                dto.getEmail(),
                dto.getUserName(),
                dto.getNickName(),
                dto.getPassword(),
                dto.getDataOfBirth()
        );
    }
}
