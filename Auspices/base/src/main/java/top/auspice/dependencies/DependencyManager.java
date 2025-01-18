package top.auspice.dependencies;

import top.auspice.dependencies.classpath.BootstrapProvider;
import top.auspice.dependencies.classpath.IsolatedClassLoader;
import top.auspice.dependencies.relocation.RelocationHandler;
import top.auspice.dependencies.relocation.SimpleRelocation;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public final class DependencyManager {
    private final BootstrapProvider plugin;
    private final Path cacheDirectory;
    private final EnumMap<Dependency, Path> loaded = new EnumMap<>(Dependency.class);
    private final Map<Set<Dependency>, IsolatedClassLoader> loaders = new HashMap<>();
    private RelocationHandler relocationHandler;
    public static final List<Dependency> REQUIRED_DEPENDENCIES;

    public DependencyManager(BootstrapProvider plugin) {
        this.plugin = plugin;
        this.cacheDirectory = setupCacheDirectory(plugin.getLibsFolder());
    }

    public EnumMap<Dependency, Path> getLoaded() {
        return this.loaded;
    }

    private synchronized RelocationHandler getRelocationHandler() {
        if (this.relocationHandler == null) {
            this.relocationHandler = new RelocationHandler(this);
        }

        return this.relocationHandler;
    }

    public IsolatedClassLoader obtainClassLoaderWith(Set<Dependency> dependencies) {
        Set<Dependency> set = EnumSet.noneOf(Dependency.class);
        set.addAll(dependencies);
        Iterator<Dependency> var3 = dependencies.iterator();

        Dependency dependency;
        do {
            if (!var3.hasNext()) {
                synchronized (this.loaders) {
                    IsolatedClassLoader classLoader = this.loaders.get(set);
                    if (classLoader != null) {
                        return classLoader;
                    }

                    Stream<Dependency> var10000 = set.stream();
                    EnumMap<Dependency, Path> var10001 = this.loaded;
                    Objects.requireNonNull(var10001);
                    URL[] urls = var10000.map(var10001::get).map((Path file) -> {
                        try {
                            return file.toUri().toURL();
                        } catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        }
                    }).toArray(URL[]::new);
                    classLoader = new IsolatedClassLoader(urls);
                    this.loaders.put(set, classLoader);
                    return classLoader;
                }
            }

            dependency = var3.next();
        } while (this.loaded.containsKey(dependency));

        throw new IllegalStateException("Dependency " + dependency + " is not loaded.");
    }

    public void tryLoadDependency(CountDownLatch latch, Dependency dependency) {
        int retries = 0;

        do {
            boolean count = true;

            try {
                this.loadDependency(dependency, retries == 0);
                return;
            } catch (Throwable ex) {
                if (!(ex instanceof DependencyDownloadException) || !(ex.getCause() instanceof SocketException) || !ex.getCause().getMessage().equalsIgnoreCase("Connection reset")) {
                    throw new IllegalStateException("Unable to load dependency: " + dependency.name(), ex);
                }

                this.plugin.getLogger().warning("[" + retries + "] Failed to download dependency '" + dependency + "': " + ex.getMessage() + ". Retrying...");
                count = false;
            } finally {
                if (count) {
                    latch.countDown();
                }

            }

            ++retries;
        } while (retries <= 5);

    }

    public void loadDependencies(Collection<Dependency> dependencies) {
        Objects.requireNonNull(dependencies);
        CountDownLatch latch = new CountDownLatch(dependencies.size());

        for (Dependency dependency : dependencies) {
            this.tryLoadDependency(latch, dependency);
        }

        try {
            latch.await();
        } catch (InterruptedException var5) {
            Thread.currentThread().interrupt();
        }

    }

    static boolean shouldAutoLoad(Dependency dependency) {
        switch (dependency) {
            default:
                return true;
        }
    }

    private void loadDependency(Dependency dependency, boolean notifyDownload) throws Exception {
        if (!this.loaded.containsKey(dependency)) {
            Path downloaded = this.downloadDependency(dependency, notifyDownload);
            Path remapped = this.remapDependency(dependency, downloaded);
            this.loaded.put(dependency, remapped);
            if (shouldAutoLoad(dependency)) {
                this.plugin.getClassPathAppender().addJarToClasspath(remapped);
            }

        }
    }

    private Path downloadDependency(Dependency dependency, boolean notifyDownload) throws DependencyDownloadException {
        Path file = this.cacheDirectory.resolve(dependency.getFileName(null));
        if (Files.exists(file)) {
            return file;
        } else {
            DependencyDownloadException lastError = null;
            AtomicBoolean done = new AtomicBoolean(false);
            if (notifyDownload) {
                this.plugin.getLogger().info("Downloading " + dependency + " v" + dependency.getDefaultVersion().getVersion() + "...");
                this.plugin.runAsyncLater(() -> {
                    if (!done.get()) {
//                        DependencyRepository.MAVEN_CENTRAL.getUrl() + dependency.getMavenRepoPath();
                        this.plugin.getLogger().warning("It looks like downloading " + dependency.name() + " is taking longer than expected. In case you have have any connection issues on your side, here's the direct download link: " + DependencyRepository.MAVEN_CENTRAL.getURLFor(dependency) + "\nAfter downloading it, put it inside '" + this.plugin.getLibsFolder().toAbsolutePath() + "' folder and make sure the file name is '" + dependency.getFileName(null) + "' (if not rename it) then restart the server.");
                    }

                }, 10L, TimeUnit.SECONDS);
            }

            this.plugin.runAsyncLater(() -> {
                if (!done.get()) {
                    this.plugin.getLogger().warning("It appears that the server is stuck trying to download " + dependency + ". Please try restarting the server.");
                }

            }, 5L, TimeUnit.MINUTES);
            DependencyRepository[] var6 = DependencyRepository.values();

            for (DependencyRepository repo : var6) {
                try {
                    repo.download(dependency, file);
                    return file;
                } catch (DependencyDownloadException e) {
                    lastError = e;
                } finally {
                    done.set(true);
                }
            }

            throw Objects.requireNonNull(lastError);
        }
    }

    private Path remapDependency(Dependency dependency, Path normalFile) throws Exception {
        List<SimpleRelocation> rules = new ArrayList<>(dependency.getRelocations());
        if (rules.isEmpty()) {
            return normalFile;
        } else {
            Path remappedFile = this.cacheDirectory.resolve(dependency.getFileName("remapped"));
            if (Files.exists(remappedFile)) {
                return remappedFile;
            } else {
                this.getRelocationHandler().remap(normalFile, remappedFile, rules);
                return remappedFile;
            }
        }
    }

    private static Path setupCacheDirectory(Path path) {
        try {
            Files.createDirectories(path);
            return path;
        } catch (IOException e) {
            throw new RuntimeException("Unable to create libs directory", e);
        }
    }

    static {
        REQUIRED_DEPENDENCIES = Arrays.asList(Dependency.KOTLIN_STDLIB, Dependency.CAFFEINE, Dependency.XSERIES, Dependency.GSON, Dependency.GUAVA);
    }
}
