package net.aurika.common.dependency.classpath;

import java.net.MalformedURLException;
import java.net.URLClassLoader;
import java.nio.file.Path;

public class ReflectionClassPathAppender implements ClassPathAppender {

  private final URLClassLoaderAccess classLoaderAccess;

  public ReflectionClassPathAppender(ClassLoader classLoader) throws IllegalStateException {
    if (classLoader instanceof URLClassLoader) {
      this.classLoaderAccess = URLClassLoaderAccess.create((URLClassLoader) classLoader);
    } else {
      throw new IllegalStateException("ClassLoader is not instance of URLClassLoader");
    }
  }

  public ReflectionClassPathAppender(Object bootstrap) throws IllegalStateException {
    this(bootstrap.getClass().getClassLoader());
  }

  public void addJarToClasspath(Path file) {
    try {
      this.classLoaderAccess.addURL(file.toUri().toURL());
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

}
