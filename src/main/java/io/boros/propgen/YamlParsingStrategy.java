package io.boros.propgen;

import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.yaml.snakeyaml.Yaml;

import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class YamlParsingStrategy implements ParsingStrategy {

    private final Yaml yaml = new Yaml();

    @Override
    public Set<String> parse(Path path, String content) {
        Iterable<Object> yamlFiles = yaml.loadAll(content);
        return StreamSupport.stream(yamlFiles.spliterator(), false)
            .map(__ -> (Map<String, Object>) __)
            .flatMap(props -> getKeys(props, "").stream())
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Set<String> getKeys(Map<String, Object> yamlConfig, String prefix) {
        Set<String> results = new LinkedHashSet<>();

        for (Map.Entry<String, Object> property : yamlConfig.entrySet()) {
            final String propertyPart = prefix.equals("")
                    ? property.getKey()
                    : prefix + "." + property.getKey();
            results.add(propertyPart);
            if (property.getValue() instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> inner = (Map<String, Object>) yamlConfig.get(property.getKey());
                results.addAll(getKeys(inner, propertyPart));
            }
        }

        return results;
    }

}
