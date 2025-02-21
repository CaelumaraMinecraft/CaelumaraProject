package net.aurika.common.dependency.classpath;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

public class JarInJarClassPathAppender implements ClassPathAppender {
    private final JarInJarClassLoader classLoader;

    public JarInJarClassPathAppender(ClassLoader classLoader) {
        if (!(classLoader instanceof JarInJarClassLoader)) {
            throw new IllegalArgumentException("Loader is not a JarInJarClassLoader: " + classLoader.getClass().getName());
        } else {
            this.classLoader = (JarInJarClassLoader) classLoader;
        }
    }

    public void addJarToClasspath(Path file) {
        try {
            this.classLoader.addURL(file.toUri().toURL());
        } catch (MalformedURLException var3) {
            MalformedURLException e = var3;
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            this.classLoader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
