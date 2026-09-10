# junit-utils

This is a tiny JUnit utilities library.

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
<dependency>
    <groupId>tools.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>3.2.2</version>
    <scope>test</scope>
</dependency>
```

**Gradle**

```groovy
testImplementation 'io.github.sivalabs:junit-utils:0.0.1'
testImplementation 'tools.jackson.core:jackson-databind:3.2.2'
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