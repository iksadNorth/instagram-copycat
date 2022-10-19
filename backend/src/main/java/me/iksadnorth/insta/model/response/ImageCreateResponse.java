package me.iksadnorth.insta.model.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.iksadnorth.insta.model.dto.ImageDto;

@Getter
@RequiredArgsConstructor
public class ImageCreateResponse {
    private final Long id;
    private final String path;

    public static ImageCreateResponse of(String path) {
        return new ImageCreateResponse(null, path);
    }
    public static ImageCreateResponse of(Long id, String path) {
        return new ImageCreateResponse(id, path);
    }

    public static ImageCreateResponse from(ImageDto imageDto) {
        return ImageCreateResponse.of(imageDto.getId(), imageDto.getPath());
    }
}
