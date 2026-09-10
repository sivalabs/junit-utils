package demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import io.github.sivalabs.junitutils.JsonFileSource;
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
