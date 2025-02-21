package net.aurika.common.dependency.classpath;

import java.nio.file.Path;

public interface ClassPathAppender extends AutoCloseable {
    void addJarToClasspath(Path path);

    default void close() {
    }
}
