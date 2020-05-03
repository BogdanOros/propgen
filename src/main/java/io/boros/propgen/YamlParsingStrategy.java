package io.boros.propgen;

import org.yaml.snakeyaml.Yaml;

import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class YamlParsingStrategy implements ParsingStrategy {

    private final Yaml yaml = new Yaml();

    @Override
    public Set<String> parse(Path path, String content) {
        Map<String, Object> map = yaml.load(content);
        return getKeys(map, "");
    }

    private Set<String> getKeys(Map<String, Object> yamlConfig, String prefix) {
        Set<String> results = new LinkedHashSet<>();

        for (Map.Entry<String, Object> property : yamlConfig.entrySet()) {
            final String propertyPart = prefix.equals("")
                    ? property.getKey()
                    : prefix + "." + property.getKey();
            if (!(property.getValue() instanceof Map)) {
                results.add(propertyPart);
            } else {
                @SuppressWarnings("unchecked")
                Map<String, Object> inner = (Map<String, Object>) yamlConfig.get(property.getKey());
                results.addAll(getKeys(inner, propertyPart));
            }
        }
        return results;
    }

}
