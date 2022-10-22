package me.iksadnorth.insta.utils.fileManager;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import me.iksadnorth.insta.config.properies.S3Properties;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
public class S3FileManager implements FileManager<MultipartFile>{
    private final AmazonS3Client amazonS3Client;
    private final S3Properties properties;

    @Override
    public String saveBinaryFile(MultipartFile srcFile, String trgPath) throws IOException {
        final String bucket = properties.getS3().getBucket();

        try(InputStream inputStream = srcFile.getInputStream()) {
            final ObjectMetadata metaData = new ObjectMetadata();

            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    bucket,
                    trgPath,
                    inputStream,
                    metaData
            );

            amazonS3Client.putObject(putObjectRequest);
        }

        return amazonS3Client.getUrl(bucket, trgPath).toString();
    }
}
