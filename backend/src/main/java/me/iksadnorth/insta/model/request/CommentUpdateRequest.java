package me.iksadnorth.insta.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.iksadnorth.insta.model.dto.CommentDto;

@Setter
@Getter
@NoArgsConstructor
public class CommentUpdateRequest {
    private String content;

    public CommentDto toDto() {
        return CommentDto.builder()
                .content(content)
                .build();
    }
}
