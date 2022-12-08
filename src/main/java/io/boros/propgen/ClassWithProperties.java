package io.boros.propgen;

import io.boros.propgen.PropertyKeys.FormattedKey;
import org.ainslec.picocog.PicoWriter;

public class ClassWithProperties {

    private final Configuration configuration;
    private final PropertyKeys propertyKeys;

    public ClassWithProperties(Configuration configuration, PropertyKeys propertyKeys) {
        this.configuration = configuration;
        this.propertyKeys = propertyKeys;
    }

    public String generate() {
        PicoWriter writer = new PicoWriter();
        writer.writeln(String.format("package %s;", configuration.outputPackage));
        writer.writeln("");
        writer.writeln(String.format("public class %s {", configuration.className));

        PicoWriter inner = writer.createDeferredWriter();
        inner.writeln_r("");

        for (FormattedKey key : propertyKeys.collect()) {
            inner.writeln(String.format("public static final String %s = \"%s\";", key.formatKey(), key.key));
        }
        writer.writeln("");
        writer.writeln("}");
        return writer.toString();
    }

    @Override
    public String toString() {
        return generate();
    }

}
