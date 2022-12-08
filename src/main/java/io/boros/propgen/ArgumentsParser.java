package io.boros.propgen;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class ArgumentsParser {

  private final String[] arguments;

  ArgumentsParser(String[] arguments) {
    this.arguments = arguments;
  }

  Configuration parse() {
    Map<String, String> config = Arrays.stream(arguments)
        .map(arg -> arg.split("="))
        .collect(Collectors.toMap(pair -> pair[0], pair -> pair[1]));

    String outputPackage = config.get("--outputPackage");
    if (outputPackage == null) {
      throw new IllegalArgumentException("'outputPackage' should be specified");
    }

    String generatedPath = config.get("--generatedPath");
    if (generatedPath == null) {
      throw new IllegalArgumentException("'generatedPath' should be specified");
    }

    String className = config.getOrDefault("--classname", "ApplicationProperties");

    String configDirs = config.get("--dirs");
    if (configDirs == null) {
      throw new IllegalArgumentException("'dirs' should list the properties location");
    }
    Set<String> dirs = new HashSet<>(Arrays.asList(configDirs.split(",")));

    return new Configuration(outputPackage, generatedPath, className, dirs);
  }

}
