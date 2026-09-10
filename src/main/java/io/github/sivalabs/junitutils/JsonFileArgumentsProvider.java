package io.github.sivalabs.junitutils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.AnnotationBasedArgumentsProvider;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.support.ParameterDeclarations;
import org.junit.platform.commons.JUnitException;
import org.junit.platform.commons.PreconditionViolationException;
import org.junit.platform.commons.util.Preconditions;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

public class JsonFileArgumentsProvider extends AnnotationBasedArgumentsProvider<JsonFileSource> {

    private final JsonMapper jsonMapper = new JsonMapper();

    @Override
    protected Stream<? extends Arguments> provideArguments(
            ParameterDeclarations parameters, ExtensionContext context, JsonFileSource jsonFileSource) {
        Preconditions.condition(parameters.getAll().size() == 1, "@JsonFileSource requires exactly one parameter");

        Charset charset = getCharset(jsonFileSource);
        Class<?> parameterType = parameters.getFirst().orElseThrow().getParameterType();
        Stream<Source> resources = Arrays.stream(jsonFileSource.resources()).map(Source::resource);
        Stream<Source> files = Arrays.stream(jsonFileSource.files()).map(Source::file);
        List<Source> sources = Stream.concat(resources, files).collect(Collectors.toList());

        return Preconditions.notEmpty(sources, "Resources or files must not be empty").stream()
                .flatMap(source -> readArguments(source, context, charset, parameterType));
    }

    private Stream<Arguments> readArguments(
            Source source, ExtensionContext context, Charset charset, Class<?> parameterType) {
        try (InputStream inputStream = source.open(context);
                Reader reader = new InputStreamReader(inputStream, charset)) {
            JsonNode root = jsonMapper.readTree(reader);
            Preconditions.notNull(root, () -> "JSON source [" + source.path() + "] must not be empty");

            Stream<JsonNode> values = root.isArray() ? root.valueStream() : Stream.of(root);
            return values.map(value -> toArgument(value, parameterType, source)).toList().stream();
        } catch (PreconditionViolationException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new JUnitException("JSON source [" + source.path() + "] could not be read", exception);
        }
    }

    private Arguments toArgument(JsonNode value, Class<?> parameterType, Source source) {
        try {
            return Arguments.of(jsonMapper.treeToValue(value, parameterType));
        } catch (Exception exception) {
            throw new JUnitException(
                    "JSON value from source [" + source.path() + "] could not be bound to "
                            + parameterType.getTypeName(),
                    exception);
        }
    }

    private static Charset getCharset(JsonFileSource jsonFileSource) {
        try {
            return Charset.forName(jsonFileSource.encoding());
        } catch (Exception exception) {
            throw new PreconditionViolationException(
                    "The charset supplied in " + jsonFileSource + " is invalid", exception);
        }
    }

    private static class Source {

        private final String path;
        private final boolean resource;

        private Source(String path, boolean resource) {
            this.path = path;
            this.resource = resource;
        }

        static Source resource(String path) {
            return new Source(path, true);
        }

        static Source file(String path) {
            return new Source(path, false);
        }

        String path() {
            return path;
        }

        InputStream open(ExtensionContext context) {
            Preconditions.notBlank(
                    path, () -> (resource ? "Classpath resource" : "File") + " path must not be null or blank");
            if (resource) {
                InputStream inputStream = context.getRequiredTestClass().getResourceAsStream(path);
                return Preconditions.notNull(inputStream, () -> "Classpath resource [" + path + "] does not exist");
            }
            try {
                return Files.newInputStream(Path.of(path));
            } catch (IOException exception) {
                throw new JUnitException("File [" + path + "] could not be read", exception);
            }
        }
    }
}
