package io.boros.propgen;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Extension {

  PROPERTIES(new PropertiesParsingStrategy(), "properties"),
  YAML(new YamlParsingStrategy(), "yml", "yaml");

  final ParsingStrategy parsingStrategy;
  final Set<String> extensions;

  Extension(ParsingStrategy parsingStrateg, String... extensions) {
    this.parsingStrategy = parsingStrateg;
    this.extensions = new HashSet<>(Arrays.asList(extensions));
  }

  public static Extension of(Path path) {
    String fileName = path.toString();
    for (Extension extension : Extension.values()) {
      if (extension.extensions.stream().anyMatch(fileName::endsWith)) {
        return extension;
      }
    }
    throw new IllegalStateException("Extension not supported for file path=" + path
        + ". Supported extensions are: " + supportedExtensions());
  }

  public static Set<String> supportedExtensions() {
    return Stream.of(Extension.values()).flatMap(extension -> extension.extensions.stream())
        .collect(Collectors.toSet());
  }

}
