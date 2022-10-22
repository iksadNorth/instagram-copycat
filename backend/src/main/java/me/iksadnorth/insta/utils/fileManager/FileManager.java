package me.iksadnorth.insta.utils.fileManager;

import java.io.IOException;

public interface FileManager<T> {
    String saveBinaryFile(T srcFile, String trgPath) throws IOException;
}
