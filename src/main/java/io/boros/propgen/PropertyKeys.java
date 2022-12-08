package io.boros.propgen;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

class PropertyKeys {

    private final PropertyFiles propertyFiles;

    PropertyKeys(PropertyFiles propertyFiles) {
        this.propertyFiles = propertyFiles;
    }

    Set<FormattedKey> collect() {
      return propertyFiles
                .content()
                .entrySet()
                .stream()
                .map(entry -> Extension.of(entry.getKey()).parsingStrategy.parse(entry.getKey(), entry.getValue()))
                .flatMap(Collection::stream)
                .map(FormattedKey::new)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    static final class FormattedKey {

        public final String key;

        public FormattedKey(String key) {
            this.key = key;
        }

        public String formatKey() {
          return key
              .replaceAll("([A-Z])", "_$1")
              .replaceAll("[.-]", "_")
              .toUpperCase();
        }

      @Override
      public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }
        FormattedKey that = (FormattedKey) o;
        return Objects.equals(formatKey(), that.formatKey());
      }

      @Override
      public int hashCode() {
        return Objects.hash(formatKey());
      }
    }

}
