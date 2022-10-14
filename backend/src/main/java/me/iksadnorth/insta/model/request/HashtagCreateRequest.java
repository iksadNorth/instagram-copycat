package me.iksadnorth.insta.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.iksadnorth.insta.model.dto.ArticleDto;
import me.iksadnorth.insta.model.dto.HashtagDto;

@Setter
@Getter
@NoArgsConstructor
public class HashtagCreateRequest {
    private String name;
    private ArticleDto article;

    public static HashtagCreateRequest from(HashtagDto dto) {
        HashtagCreateRequest hashtagCreateRequest = new HashtagCreateRequest();
        hashtagCreateRequest.setName(dto.getName());
        hashtagCreateRequest.setArticle(dto.getArticle());
        return hashtagCreateRequest;
    }
}
