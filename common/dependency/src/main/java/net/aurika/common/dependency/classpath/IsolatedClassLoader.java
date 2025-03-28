package net.aurika.common.dependency.classpath;

import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

public final class IsolatedClassLoader extends URLClassLoader {

  public IsolatedClassLoader(@NotNull URL @NotNull [] urls) {
    super(urls, ClassLoader.getSystemClassLoader().getParent());
  }

  public @NotNull String toString() {
    return "IsolatedClassLoader[" + Arrays.toString(this.getURLs()) + ']';
  }

  static {
    ClassLoader.registerAsParallelCapable();
  }
}
