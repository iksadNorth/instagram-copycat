package me.iksadnorth.insta.utils.fileManager;

import me.iksadnorth.insta.config.properies.ImageDownloadProperties;
import me.iksadnorth.insta.exception.ErrorCode;
import me.iksadnorth.insta.exception.InstaApplicationException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class LocalFileManager implements FileManager<MultipartFile>{
    @Override
    public String saveBinaryFile(MultipartFile srcFile, String trgPath) {
        File trgFile = new File(ImageDownloadProperties.getDirFromStatic(trgPath));

        try {
            srcFile.transferTo(trgFile.getAbsoluteFile());
        } catch (IOException e) {
            e.printStackTrace();
            throw new InstaApplicationException(ErrorCode.IO_ERROR_WITH_IMAGE);
        }
        return trgPath;
    }
}
