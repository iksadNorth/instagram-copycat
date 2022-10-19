package me.iksadnorth.insta.service;

import lombok.RequiredArgsConstructor;
import me.iksadnorth.insta.config.properies.ImageDownloadProperties;
import me.iksadnorth.insta.exception.ErrorCode;
import me.iksadnorth.insta.exception.InstaApplicationException;
import me.iksadnorth.insta.model.dto.ImageDto;
import me.iksadnorth.insta.model.entity.Image;
import me.iksadnorth.insta.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository repo;
    private final ImageDownloadProperties properties;
    private String mkFileName(MultipartFile file) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String current_date = simpleDateFormat.format(new Date());
        String name = file.getOriginalFilename();

        File trgFile = new File(properties.getTrgDir());
        trgFile.mkdirs();

        return String.format("%s/%s_%s.png", trgFile.getAbsoluteFile(), current_date, name);
    }

    public ImageDto create(List<MultipartFile> files) {
        if(files.size() != 1) {
            throw new InstaApplicationException(
                    ErrorCode.TOO_MANY_IMAGES,
                    String.format("실제 들어온 파일 갯수: %s", files.size())
            );
        }

        MultipartFile srcFile = files.get(0);
        String path = mkFileName(srcFile);
        File trgFile = new File(path);

        try {
            srcFile.transferTo(trgFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new InstaApplicationException(ErrorCode.IO_ERROR_WITH_IMAGE);
        }

        ImageDto dto = ImageDto.of(path);
        Image entity = repo.save(dto.toEntity());

        return ImageDto.fromEntity(entity);
    }
}
