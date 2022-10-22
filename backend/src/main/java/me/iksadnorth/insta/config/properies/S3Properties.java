package me.iksadnorth.insta.config.properies;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "cloud.aws")
public class S3Properties {
    private Region region;
    private S3 s3;
    private Credentials credentials;

    @Data
    public static class Region {
        private String Static;
    }
    @Data
    public static class S3 {
        private String bucket;
    }
    @Data
    public static class Credentials {
        private String accessKey;
        private String secretKey;
    }

}
