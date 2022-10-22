package me.iksadnorth.insta.config.properies;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Data
@Configuration
@ConfigurationProperties(prefix = "download.image")
public class ImageDownloadProperties {
    public static final Path STATIC_RESOURCES = Paths.get("src/main/resources/static");
    private String trgDir;

    public String getTrgDirFromStatic() {
        return getDirFromStatic(trgDir);
    }

    public static String getDirFromStatic(String path) {
        return String.format("%s/%s", STATIC_RESOURCES, path);
    }
}
