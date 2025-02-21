package net.aurika.auspice.craftbukkit.loader;

import top.auspice.api.user.AuspiceUser;
import top.auspice.dependencies.classpath.BootstrapProvider;
import top.auspice.dependencies.classpath.ClassPathAppender;
import top.auspice.dependencies.classpath.ReflectionClassPathAppender;

import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

final class a implements BootstrapProvider {
    private final BukkitAuspiceLoader loader;
    private final ReflectionClassPathAppender b;

    public a(BukkitAuspiceLoader loader) {
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
        return BukkitAuspiceLoader.getFolder().resolve("libs");
    }

    public Logger getLogger() {
        return this.loader.getLogger();
    }
}