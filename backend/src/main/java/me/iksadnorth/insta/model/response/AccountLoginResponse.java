package me.iksadnorth.insta.model.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.iksadnorth.insta.model.dto.AccountDto;

@Getter
@RequiredArgsConstructor
public class AccountLoginResponse {
    private final String token;

    public static AccountLoginResponse from(String token) {
        return new AccountLoginResponse(token);
    }
}
