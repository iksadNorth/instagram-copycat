package me.iksadnorth.insta.config;

import com.amazonaws.services.s3.AmazonS3Client;
import lombok.RequiredArgsConstructor;
import me.iksadnorth.insta.config.properies.ImageDownloadProperties;
import me.iksadnorth.insta.config.properies.S3Properties;
import me.iksadnorth.insta.utils.fileManager.FileManager;
import me.iksadnorth.insta.utils.NameGenerator.NameGenerator;
import me.iksadnorth.insta.utils.NameGenerator.NameGeneratorWithDateTime;
import me.iksadnorth.insta.utils.fileManager.S3FileManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

@Configuration
@RequiredArgsConstructor
public class FileHandlerConfig {
    private final AmazonS3Client amazonS3Client;
    private final S3Properties s3Properties;
    private final ImageDownloadProperties imageDownloadProperties;

    @Bean
    public FileManager<MultipartFile> fileManager() {
        return new S3FileManager(amazonS3Client, s3Properties);
    }

    @Bean
    public NameGenerator<MultipartFile> nameGenerator() {
        return new NameGeneratorWithDateTime(imageDownloadProperties);
    }
}
