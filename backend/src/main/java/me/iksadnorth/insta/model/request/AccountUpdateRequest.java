package me.iksadnorth.insta.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.iksadnorth.insta.model.dto.AccountDto;
import me.iksadnorth.insta.model.entity.Account;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class AccountUpdateRequest {
    private String email;
    private String userName;
    private String nickName;
    private String password;
    private LocalDateTime dateOfBirth;

    public static AccountUpdateRequest from(AccountDto dto) {
        AccountUpdateRequest accountUpdateRequest = new AccountUpdateRequest();
        accountUpdateRequest.setEmail(dto.getEmail());
        accountUpdateRequest.setUserName(dto.getUserName());
        accountUpdateRequest.setNickName(dto.getNickName());
        accountUpdateRequest.setPassword(dto.getPassword());
        accountUpdateRequest.setDateOfBirth(dto.getDateOfBirth());
        return accountUpdateRequest;
    }

    public AccountDto toDto() {
        return AccountDto.builder()
                .email(email)
                .userName(userName)
                .nickName(nickName)
                .password(password)
                .dateOfBirth(dateOfBirth)
                .build();
    }
}
