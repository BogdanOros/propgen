package io.boros.propgen;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

class PropertyKeys {

    private final PropertyFiles propertyFiles;

    PropertyKeys(PropertyFiles propertyFiles) {
        this.propertyFiles = propertyFiles;
    }

    Set<String> collect() {
        return propertyFiles
                .content()
                .entrySet()
                .parallelStream()
                .map(entry -> Extension.of(entry.getKey()).parsingStrategy.parse(entry.getKey(), entry.getValue()))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

}
