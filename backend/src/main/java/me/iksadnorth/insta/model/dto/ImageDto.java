package me.iksadnorth.insta.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.iksadnorth.insta.model.entity.Image;

@Getter
@RequiredArgsConstructor
public class ImageDto {
    private final String path;

    public static ImageDto fromEntity(Image entity) {
        return new ImageDto(
                entity.getPath()
        );
    }

    public Image toEntity() {
        Image image = new Image();
        image.setPath(path);
        return image;
    }
}
