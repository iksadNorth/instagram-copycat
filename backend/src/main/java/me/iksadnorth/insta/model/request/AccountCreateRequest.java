package me.iksadnorth.insta.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.iksadnorth.insta.model.dto.AccountDto;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class AccountCreateRequest {
    private String email;
    private String userName;
    private String nickName;
    private String password;
    @JsonFormat(pattern = "yyyyMMddHHmmss")
    private LocalDateTime dateOfBirth;

    public static AccountCreateRequest from(AccountDto dto) {
        AccountCreateRequest vo = new AccountCreateRequest();

        vo.setEmail(dto.getEmail());
        vo.setUserName(dto.getUserName());
        vo.setNickName(dto.getNickName());
        vo.setPassword(dto.getPassword());
        vo.setDateOfBirth(dto.getDateOfBirth());

        return vo;
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
