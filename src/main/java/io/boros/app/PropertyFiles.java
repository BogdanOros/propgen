package io.boros.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class PropertyFiles {

    private final Set<String> dirs;
    private final Set<String> supportedExtensions;

    public PropertyFiles(Configuration configuration) {
        this(configuration.dirs, Extension.supportedExtensions());
    }

    PropertyFiles(Set<String> dirs, Set<String> supportedExtensions) {
        this.dirs = dirs;
        this.supportedExtensions = supportedExtensions;
    }

    public Map<Path, String> content() {
        Map<Path, String> content = new HashMap<>();
        try {
            for (String file : dirs) {
                Path filePath = Paths.get(file);
                if (!Files.exists(filePath)) {
                    throw new IllegalArgumentException("Not existing path=" + filePath);
                }
                if (!Files.isDirectory(filePath)) {
                    content.put(filePath, new String(Files.readAllBytes(filePath)));
                } else {
                    try (Stream<Path> files = Files.walk(filePath)) {
                        files
                                .filter(path -> supportedExtensions.stream().anyMatch(ext -> path.toString().endsWith(ext)))
                                .forEach(path -> {
                                    try {
                                        content.put(path, new String(Files.readAllBytes(path)));
                                    } catch (IOException ex) {
                                        throw new IllegalStateException("Error faced during reading the path=" + path, ex);
                                    }
                                });
                    }
                }
            }
        } catch (IOException ex) {
            throw new IllegalStateException("Eror faced during reading configuration files.", ex);
        }
        return content;
    }
}
