package me.iksadnorth.insta.config.properies;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "download.image")
public class ImageDownloadProperties {
    private String trgDir;
}
