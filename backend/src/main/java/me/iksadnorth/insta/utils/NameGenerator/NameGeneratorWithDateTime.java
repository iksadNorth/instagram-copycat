package me.iksadnorth.insta.utils.NameGenerator;

import lombok.RequiredArgsConstructor;
import me.iksadnorth.insta.config.properies.ImageDownloadProperties;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@RequiredArgsConstructor
public class NameGeneratorWithDateTime implements NameGenerator<MultipartFile> {
    private final ImageDownloadProperties properties;

    @Override
    public String genFileName(MultipartFile file) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String current_date = simpleDateFormat.format(new Date());

        File trgFile = new File(properties.getTrgDirFromStatic());
        trgFile.mkdirs();

        return String.format("%s/%s.png", properties.getTrgDir(), current_date);
    }
}
