package io.boros.propgen;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class PropertiesParsingStrategy implements ParsingStrategy {

    @Override
    public Set<String> parse(Path path, String content) {
        final Properties properties = new Properties();
        try {
            properties.load(new StringReader(content));
            return properties.keySet().stream().map(String::valueOf).collect(Collectors.toCollection(LinkedHashSet::new));
        } catch (IOException ex) {
            throw new IllegalStateException("Cannot read the property file=" + path, ex);
        }
    }
}
