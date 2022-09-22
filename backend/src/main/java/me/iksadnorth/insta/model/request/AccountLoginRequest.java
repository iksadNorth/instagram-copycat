package me.iksadnorth.insta.model.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.iksadnorth.insta.model.dto.AccountDto;

@Getter
@RequiredArgsConstructor
public class AccountLoginRequest {
    private final String email;
    private final String password;

    public static AccountLoginRequest from(AccountDto dto) {
        return new AccountLoginRequest(
                dto.getEmail(), dto.getPassword()
        );
    }
}
