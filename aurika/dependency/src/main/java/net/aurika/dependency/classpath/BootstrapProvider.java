package net.aurika.dependency.classpath;

import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public interface BootstrapProvider {
    void onLoad();

    void onEnable();

    void onDisable();

    void runAsyncLater(Runnable runnable, long delay, TimeUnit delayTimeUnit);

    ClassPathAppender getClassPathAppender();

    Path getLibsFolder();

    Logger getLogger();
}
