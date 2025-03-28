package net.aurika.common.dependency.classpath;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;

public final class JarInJarClassLoader extends URLClassLoader {

  public JarInJarClassLoader(ClassLoader loaderClassLoader, String jarResourcePath) throws LoadingException {
    super(new URL[]{extractJar(loaderClassLoader, jarResourcePath)}, loaderClassLoader);
  }

  public void addURL(URL url) {
    super.addURL(url);
  }

  public void close() throws IOException {
    super.close();
    URL[] urls = this.getURLs();
    if (urls.length != 0) {
      try {
        Path path = Paths.get(urls[0].toURI());
        Files.deleteIfExists(path);
      } catch (Exception var3) {
      }
    }
  }

  public <T> BootstrapProvider instantiatePlugin(String bootstrapClass, Class<T> loaderPluginType, T loaderPlugin) throws LoadingException {
    Class<?> plugin;
    try {
      plugin = this.loadClass(bootstrapClass).asSubclass(BootstrapProvider.class);
    } catch (ReflectiveOperationException e) {
      throw new LoadingException("Unable to load bootstrap class", e);
    }

    ReflectiveOperationException e;
    Constructor<?> constructor;
    try {
      constructor = plugin.getConstructor(loaderPluginType);
    } catch (ReflectiveOperationException var8) {
      e = var8;
      throw new LoadingException("Unable to get bootstrap constructor", e);
    }

    try {
      return (BootstrapProvider) constructor.newInstance(loaderPlugin);
    } catch (ReflectiveOperationException var7) {
      e = var7;
      throw new LoadingException("Unable to create bootstrap plugin instance", e);
    }
  }

  public static URL extractJar(ClassLoader loaderClassLoader, String jarResourcePath) throws LoadingException {
    URL jarInJar = loaderClassLoader.getResource(jarResourcePath);
    if (jarInJar == null) {
      throw new LoadingException("Could not locate jar-in-jar");
    } else {
      Path path;
      IOException e;
      try {
        path = Files.createTempFile("kingdoms-jarinjar", ".jar.tmp");
      } catch (IOException var9) {
        e = var9;
        throw new LoadingException("Unable to create a temporary file", e);
      }

      path.toFile().deleteOnExit();

      try {
        InputStream in = jarInJar.openStream();

        try {
          Files.copy(in, path, new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
        } catch (Throwable var10) {
          if (in != null) {
            try {
              in.close();
            } catch (Throwable var7) {
              var10.addSuppressed(var7);
            }
          }

          throw var10;
        }

        if (in != null) {
          in.close();
        }
      } catch (IOException var11) {
        e = var11;
        throw new LoadingException("Unable to copy jar-in-jar to temporary path", e);
      }

      try {
        return path.toUri().toURL();
      } catch (MalformedURLException ex) {
        throw new LoadingException("Unable to get URL from path", ex);
      }
    }
  }

  static {
    ClassLoader.registerAsParallelCapable();
  }
}
