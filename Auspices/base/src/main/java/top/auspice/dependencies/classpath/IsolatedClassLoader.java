package top.auspice.dependencies.classpath;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

public final class IsolatedClassLoader extends URLClassLoader {
    public IsolatedClassLoader(URL[] urls) {
        super(urls, ClassLoader.getSystemClassLoader().getParent());
    }

    public String toString() {
        return "IsolatedClassLoader[" + Arrays.toString(this.getURLs()) + ']';
    }

    static {
        ClassLoader.registerAsParallelCapable();
    }
}
