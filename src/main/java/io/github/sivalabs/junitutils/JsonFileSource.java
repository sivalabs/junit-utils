package io.github.sivalabs.junitutils;

import static org.apiguardian.api.API.Status.STABLE;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apiguardian.api.API;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * {@code @CsvFileSource} is a {@linkplain Repeatable repeatable}
 * {@link ArgumentsSource} which is used to load comma-separated value (CSV)
 * files from one or more classpath {@link #resources} or {@link #files}.
 *
 * <h2>Inheritance</h2>
 *
 * <p>This annotation is {@linkplain Inherited inherited} within class hierarchies.
 *
 * @since 5.0
 * @see CsvSource
 * @see org.junit.jupiter.params.provider.ArgumentsSource
 * @see org.junit.jupiter.params.ParameterizedClass
 * @see org.junit.jupiter.params.ParameterizedTest
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@API(status = STABLE, since = "5.7")
@ArgumentsSource(JsonFileArgumentsProvider.class)
@SuppressWarnings("exports")
public @interface JsonFileSource {

    /**
     * The JSON classpath resources to use as the sources of arguments; must not
     * be empty unless {@link #files} is non-empty.
     */
    String[] resources() default {};

    /**
     * The JSON files to use as the sources of arguments; must not be empty
     * unless {@link #resources} is non-empty.
     */
    String[] files() default {};

    /**
     * The encoding to use when reading the JSON files; must be a valid charset.
     *
     * <p>Defaults to {@code "UTF-8"}.
     *
     * @see java.nio.charset.StandardCharsets
     */
    String encoding() default "UTF-8";
}
