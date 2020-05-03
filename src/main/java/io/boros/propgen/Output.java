package io.boros.propgen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Output {

    private final String content;
    private final Configuration configuration;

    public Output(Object any, Configuration configuration) {
        this(any.toString(), configuration);
    }

    public Output(String content, Configuration configuration) {
        this.content = content;
        this.configuration = configuration;
    }

    public void output() {
        String normalizedDirectory = configuration.outputPackage.replace('.', '/');
        Path path = Paths.get(
                configuration.generatedPath,
                normalizedDirectory,
                configuration.className + ".java"
        );
        try {
            Path generatedPath = Paths.get(configuration.generatedPath, normalizedDirectory);
            Files.createDirectories(generatedPath);
            Files.write(
                    path,
                    content.getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE
            );
        } catch (IOException ex) {
            throw new IllegalStateException("Cannot generate classfile for filename=" + configuration.className, ex);
        }
    }

}
