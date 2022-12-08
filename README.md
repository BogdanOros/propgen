# propgen

## 👨‍💻 Why

Add compile-time safety to your property keys.
---

**Questions**: But really why?
```java

@SqsListener("${cloud.aws.sqs.queue}") // <-- pain point 
public void handleMessage(String body) {}
```

Refactoring the property names is painful and error-prone.  

**Question**: How does the library fix the problem:
```java
@SqsListener("${" + ApplicationProperties.CLOUD_AWS_SQS_QUEUE + "}") // <-- generated safe code
public void handleMessage(String body) {}
```
Renaming the property name in the property file will fail the project compilation.

**Question**: What is the libraries output?
It converts property files to a Java class with property keys.

For:
```yaml
spring:
  datasources:
    jdbcUrl: jdbc:mysql://localhost:3306/test
  profiles:
    active: dev

cloud:
  aws:
    sqs:
      queue: https://sqs.amazonaws.com/queue/test
```
it creates:
```java
public class ApplicationProperties {

   public static final String SPRING_DATASOURCES_JDBC_URL = "spring.datasources.jdbcUrl";
   public static final String CLOUD_AWS_SQS_QUEUE = "cloud.aws.sqs.queue";
   public static final String SPRING_PROFILES_ACTIVE = "spring.profiles.active";

}

```
**Question**: What are the supported properyy files formats?
So far the library supports `.properties` and `.yml` files.  
Feel free to contribute new formats.

## 👨‍💻 How to use
I am using gradle for my build configuration, so here is the gradle configuration:

### Maven
```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>exec-maven-plugin</artifactId>
    <version>3.1.0</version>
    <executions>
        <execution>
            <goals>
                <goal>java</goal>
            </goals>
            <phase>validate</phase>
        </execution>
    </executions>
    <dependencies>
        <dependency>
            <groupId>io.boros</groupId>
            <artifactId>propertygen</artifactId>
            <version>1.5.0</version>
        </dependency>
    </dependencies>
    <configuration>
        <executable>java</executable>
        <executableDependency>
            <groupId>io.boros</groupId>
            <artifactId>propertygen</artifactId>
        </executableDependency>
        <includePluginDependencies>true</includePluginDependencies>
        <includeProjectDependencies>false</includeProjectDependencies>
        <mainClass>io.boros.propgen.PropertiesGenerator</mainClass>
        <arguments>
            <argument>--outputPackage=com.workmotion.properties</argument>
            <argument>--generatedPath=${project.build.directory}/generated-sources/annotations</argument>
            <argument>--dirs=${project.basedir}/src/main/resources</argument>
            <argument>--classname=ApplicationProperties</argument>
        </arguments>
    </configuration>
    </plugin>
```

### Gradle
```groovy
task generateProps(type: JavaExec) {
    classpath = files("/path/to/jar")
    args "--outputPackage=io.boros.app.properties",
         "--generatedPath=${project.projectDir}/src/main/java/",
         "--dirs=${sourceSets.main.resources.srcDirs.join(',')}",
         "--classname=ApplicationProperties"
}
compileJava.dependsOn generateProps
```

### Contribution
Will appreciate a ready-to-use plugin!
