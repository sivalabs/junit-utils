package io.github.sivalabs.junitutils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.params.provider.ArgumentsSource;

/**
 * {@code @JsonFileSource} is an {@link ArgumentsSource} which is used to load JSON
 * files from one or more classpath {@link #resources} or {@link #files}.
 *
 * <p>This annotation is {@linkplain Inherited inherited} within class hierarchies.
 *
 * @see org.junit.jupiter.params.provider.ArgumentsSource
 * @see org.junit.jupiter.params.ParameterizedClass
 * @see org.junit.jupiter.params.ParameterizedTest
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
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
