package io.boros.propgen;

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
        writer.writeln("package $1;".replace("$1", configuration.outputPackage));
        writer.writeln("");
        writer.writeln("public class ApplicationProperties {");

        PicoWriter inner = writer.createDeferredWriter();
        inner.writeln_r("");

        for (String key : propertyKeys.collect()) {
            String formattedKey = key.replaceAll("([A-Z])", "_$1");
            formattedKey = formattedKey.replaceAll("[.-]", "_").toUpperCase();
            inner.writeln("public static final String " + formattedKey + " = \"" + key + "\";");
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
