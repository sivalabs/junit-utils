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
    <scope>test</scope>
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
    <scope>test</scope>
</dependency>
```

**Gradle**

```groovy
testImplementation 'io.github.sivalabs:junit-utils:0.0.1'

// To use SNAPSHOT version

repositories {
    mavenCentral()
    //Groovy DSL
    maven { url = "https://central.sonatype.com/repository/maven-snapshots" }
    //Kotlin DSL
    maven { url = uri("https://central.sonatype.com/repository/maven-snapshots/") }
}

testImplementation 'io.github.sivalabs:junit-utils:0.0.2-SNAPSHOT'

```

### Using @JsonFileSource


```java
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import org.junit.jupiter.params.ParameterizedTest;

import io.github.sivalabs.junitutils.JsonFileSource;

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