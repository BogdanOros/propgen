package io.boros.app;

import java.nio.file.Path;
import java.util.Set;

public interface ParsingStrategy {

    Set<String> parse(Path path, String content);

}
