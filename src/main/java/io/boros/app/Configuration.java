package io.boros.app;

import java.util.Set;

public class Configuration {

    final String outputPackage;
    final String generatedPath;
    final String className;
    final Set<String> dirs;

    public Configuration(String outputPackage, String generatedPath, String className, Set<String> dirs) {
        this.outputPackage = outputPackage;
        this.generatedPath = generatedPath;
        this.className = className;
        this.dirs = dirs;
    }

}
