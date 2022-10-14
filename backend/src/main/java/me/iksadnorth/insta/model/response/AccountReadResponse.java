package me.iksadnorth.insta.model.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.iksadnorth.insta.model.dto.AccountDto;
import me.iksadnorth.insta.model.dto.ImageDto;
import me.iksadnorth.insta.model.entity.Image;

@Getter
@RequiredArgsConstructor
public class AccountReadResponse {
    private final String nickName;
    private final String introduction;
    private final Long articles;
    private final Long followers;
    private final Long followings;

    public static AccountReadResponse from(AccountDto dto) {
        return new AccountReadResponse(
                dto.getNickName(),
                dto.getIntroduction(),
                dto.getArticles(),
                dto.getFollowers(),
                dto.getFollowings()
        );
    }
}
