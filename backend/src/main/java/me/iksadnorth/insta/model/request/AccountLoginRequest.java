package me.iksadnorth.insta.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.iksadnorth.insta.model.dto.AccountDto;

@Setter
@Getter
@NoArgsConstructor
public class AccountLoginRequest {
    private String email;
    private String password;

    public static AccountLoginRequest from(AccountDto dto) {
        AccountLoginRequest accountLoginRequest = new AccountLoginRequest();
        accountLoginRequest.setEmail(dto.getEmail());
        accountLoginRequest.setPassword(dto.getPassword());
        return accountLoginRequest;
    }

    public AccountDto toDto() {
        return AccountDto.builder()
                .email(email)
                .password(password)
                .build();
    }
}
