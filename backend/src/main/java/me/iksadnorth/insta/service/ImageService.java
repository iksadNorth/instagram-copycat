package me.iksadnorth.insta.service;

import lombok.RequiredArgsConstructor;
import me.iksadnorth.insta.exception.ErrorCode;
import me.iksadnorth.insta.exception.InstaApplicationException;
import me.iksadnorth.insta.model.dto.ImageDto;
import me.iksadnorth.insta.model.entity.Image;
import me.iksadnorth.insta.repository.ImageRepository;
import me.iksadnorth.insta.utils.NameGenerator.NameGenerator;
import me.iksadnorth.insta.utils.fileManager.FileManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository repo;
    private final NameGenerator<MultipartFile> nameGenerator;
    private final FileManager<MultipartFile> fileManager;

    public ImageDto create(List<MultipartFile> files) {
        if(files.size() != 1) {
            throw new InstaApplicationException(
                    ErrorCode.TOO_MANY_IMAGES,
                    String.format("실제 들어온 파일 갯수: %s", files.size())
            );
        }
        MultipartFile srcFile = files.get(0);

        String path = nameGenerator.genFileName(srcFile);
        String pathAllocated = null;

        try {
            pathAllocated = fileManager.saveBinaryFile(srcFile, path);
        } catch (IOException e) {
            throw new InstaApplicationException(ErrorCode.IO_ERROR_WITH_IMAGE, e.getMessage());
        }

        ImageDto dto = ImageDto.of(pathAllocated);
        Image entity = repo.save(dto.toEntity());

        return ImageDto.fromEntity(entity);
    }
}
