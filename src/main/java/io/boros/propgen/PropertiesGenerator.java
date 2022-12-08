package io.boros.propgen;

public class PropertiesGenerator {

  public static void main(String[] args) {
    Configuration configuration = new ArgumentsParser(args).parse();
    new Output(
        new ClassWithProperties(
            configuration,
            new PropertyKeys(
                new PropertyFiles(
                    configuration
                )
            )
        ),
        configuration
    ).output();
  }

}
