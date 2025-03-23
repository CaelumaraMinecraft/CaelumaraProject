package net.aurika.auspice.folia.bootstrap;

import net.aurika.auspice.user.AuspiceUser;
import net.aurika.common.dependency.classpath.BootstrapProvider;
import net.aurika.common.dependency.classpath.ClassPathAppender;
import net.aurika.common.dependency.classpath.ReflectionClassPathAppender;

import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

class FoliaBootstrapProvider implements BootstrapProvider {
    private final FoliaAuspiceLoader loader;
    private final ReflectionClassPathAppender b;

    public FoliaBootstrapProvider(FoliaAuspiceLoader loader) {
        this.loader = loader;
        this.b = new ReflectionClassPathAppender(this.getClass().getClassLoader());
    }

    public void onLoad() {
        this.loader.onLoad();
    }

    public void onEnable() {
        this.loader.onEnable();
    }

    public void onDisable() {
        this.loader.onDisable();
    }

    public void runAsyncLater(Runnable runnable, long delay, TimeUnit delayTimeUnit) {
        AuspiceUser.taskScheduler().async().delayed(Duration.ofMillis(delayTimeUnit.toMillis(delay)), runnable);
    }

    public ClassPathAppender getClassPathAppender() {
        return this.b;
    }

    public Path getLibsFolder() {
        return FoliaAuspiceLoader.getFolder().resolve("libs");
    }

    public Logger getLogger() {
        return this.loader.getLogger();
    }
}