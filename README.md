# junit-utils

This is a tiny utilities library with commonly used features in Spring applications.

## How to use?

### Add the dependency

**Maven** 

```xml
<dependency>
    <groupId>io.github.sivalabs</groupId>
    <artifactId>junit-utils</artifactId>
    <version>0.0.1</version>
</dependency>

<!-- To use SNAPSHOT version -->
<repositories>
    <repository>
        <name>Central Portal Snapshots</name>
        <id>central-portal-snapshots</id>
        <url>https://central.sonatype.com/repository/maven-snapshots/</url>
        <releases>
            <enabled>false</enabled>
        </releases>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
</repositories>

<dependency>
    <groupId>io.github.sivalabs</groupId>
    <artifactId>junit-utils</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

**Gradle**

```groovy
implementation 'io.github.sivalabs:junit-utils:0.0.1'

// To use SNAPSHOT version

repositories {
    mavenCentral()
    //Groovy DSL
    maven { url = "https://central.sonatype.com/repository/maven-snapshots" }
    //Kotlin DSL
    maven { url = uri("https://central.sonatype.com/repository/maven-snapshots/") }
}

implementation 'io.github.sivalabs:junit-utils:0.0.4-SNAPSHOT'

```

### Using @JsonFileSource


```java
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import org.junit.jupiter.params.ParameterizedTest;

class JsonFileSourceTest {

    @ParameterizedTest
    @JsonFileSource(resources = "/people.json")
    void bindsEachJsonArrayElement(Person person) {
        assertTrue(Arrays.asList("Alice", "Bob").contains(person.name));
        assertTrue(person.age > 0);
    }

    @ParameterizedTest
    @JsonFileSource(files = "src/test/resources/person.json")
    void bindsJsonObject(Person person) {
        assertEquals("Siva", person.name);
        assertEquals(42, person.age);
    }

    record Person(String name, int age) {}
}
```

**people.json**

```json
[
  {"name": "Alice", "age": 31},
  {"name": "Bob", "age": 27}
]
```

**person.json**

```json
{"name": "Siva", "age": 42}
```