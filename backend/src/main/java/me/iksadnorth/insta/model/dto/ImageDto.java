package me.iksadnorth.insta.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import me.iksadnorth.insta.model.entity.Image;

@ToString
@Getter
@RequiredArgsConstructor
public class ImageDto {
    private final Long id;
    private final String path;

    public static ImageDto fromEntity(Image entity) {
        return new ImageDto(
                entity.getId(),
                entity.getPath()
        );
    }

    public static ImageDto of(String path) { return new ImageDto(null, path); }
    public static ImageDto of(Long id, String path) { return new ImageDto(id, path); }

    public Image toEntity() {
        Image image = new Image();
        image.setId(id);
        image.setPath(path);
        return image;
    }
}
