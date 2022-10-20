package me.iksadnorth.insta.model.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.iksadnorth.insta.model.dto.AccountDto;

@Getter
@RequiredArgsConstructor
public class AccountSearchResponse {
    private final Long id;
    private final String nickName;
    private final String userName;

    public static AccountSearchResponse from(AccountDto dto) {
        return new AccountSearchResponse(
                dto.getId(),
                dto.getNickName(),
                dto.getUserName()
        );
    }
}
