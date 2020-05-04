# progen
---
### 👨‍💻 Why

Add compile-time safety to your property keys, 
removes the need to full-text search your project and to pray.
---

**Questions**: But really why?
There are a lot of cases during the day-by-day development, when I used string literals 
in Spring Boot projects for example.  
A default example:
```java

@SqsListener("cloud.aws.sqs.queue") // <-- pain point 
public void handleMessage(String body) {}
```

Refactoring the property names is painful and error-prone.  

**Question**: How does the library fix the problem:
```java
@SqsListener(ApplicationProperties.CLOUD_AWS_SQS_QUEUE) // <-- generated safe code
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

**Question**: Why cannot the library just generate type-safe configuration classes?
There are a lot of concerns to worry about.  

1. You cannot guess the property type having only the property value.  
```properties
spring.datasource.password=1234
``` 
What is the correct type for the `password` property? `Number`?
Using `Number` type for generation will work for local development configuration,
but most likely will break your production environment, which uses `qwerty1234` as the password.  
2. All primitives are strings leads to String Driven Development.
3. Using `type-overrides` and other `configuration hacks`.  
Now you should remember that there is a configuration that keeps your builds working.
4. Use comments in property files.
```properties
spring.datasource.password=1234 // string
```
Maybe this is the way to do it properly.

### 👨‍💻 How to use
I am using gradle for my build configuration, so here is the gradle configuration:
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
Will appreciate a ready-to-use plugin.

### Contribution
