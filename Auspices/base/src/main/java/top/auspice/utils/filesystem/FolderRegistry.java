package top.auspice.utils.filesystem;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.utils.Pair;
import top.auspice.utils.debug.AuspiceDebug;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public abstract class FolderRegistry {
    protected final String displayName;
    protected final Path folder;
    protected boolean copyDefaults = false;
    protected boolean useDefaults = false;
    private final Set<String> a = new HashSet<>();

    public FolderRegistry(@NonNull String displayName, @NonNull Path path) {
        this.displayName = Objects.requireNonNull(displayName);
        this.folder = Objects.requireNonNull(path);
        if (Files.exists(path) && !Files.isDirectory(path)) {
            throw new IllegalArgumentException("Path doesn't refer to a folder: " + path.toAbsolutePath());
        }
    }

    protected boolean filter(@NonNull Path var1) {
        return true;
    }

    protected void visitPresent() {
        if (!Files.exists(this.folder)) {
            throw new IllegalStateException("Cannot visit because the folder doesn't exist: " + this.folder);
        } else {
            try {
                Stream<Path> var1 = Files.walk(this.folder);

                try {
                    var1.filter(Files::isRegularFile).filter(this::filter).forEach((var1x) -> {
                        String var2;
                        String var3 = a(var2 = this.folder.toAbsolutePath().relativize(var1x.toAbsolutePath()).toString());
                        this.a.add(var3);
                        AuspiceLogger.debug(AuspiceDebug.FOLDER_REGISTRY, "Visting present of " + this + " -> " + var3 + " | " + var2);

                        try {
                            this.handle(new Entry(var3, var2, var1x, null));
                        } catch (Throwable var4) {
                            throw new IllegalStateException("Error while registering " + this.displayName + " '" + var3 + "' " + var1x.toFile(), var4);
                        }
                    });
                } catch (Throwable var4) {
                    if (var1 != null) {
                        try {
                            var1.close();
                        } catch (Throwable var3) {
                            var4.addSuppressed(var3);
                        }
                    }

                    throw var4;
                }

                if (var1 == null) {
                    return;
                }

                var1.close();
            } catch (IOException var5) {
                var5.printStackTrace();
            }

        }
    }

    protected abstract Pair<String, URI> getDefaultsURI();

    protected abstract void handle(@NonNull Entry var1);

    public abstract void register();

    public FolderRegistry dontUseDefaultsIfFolderExists() {
        boolean var1 = !Files.exists(this.folder);
        this.copyDefaults(var1).useDefaults(var1);
        return this;
    }

    public FolderRegistry copyDefaults(boolean var1) {
        this.copyDefaults = var1;
        return this;
    }

    public FolderRegistry useDefaults(boolean var1) {
        this.useDefaults = var1;
        return this;
    }

    private static String a(String var0) {
        return var0.substring(0, var0.lastIndexOf(46)).replace('\\', '/').replace(' ', '-');
    }

    protected void visitDefaults() {
        Pair<String, URI> pair;
        try {
            pair = this.getDefaultsURI();
        } catch (Throwable var7) {
            throw new RuntimeException("Failed to obtain defaults for: " + this, var7);
        }

        URI uri = pair.getValue();
        String var11 = pair.getKey();

        try {
            FileSystem var12 = FileSystems.newFileSystem(uri, new HashMap<>());

            try {

                for (Path path : var12.getRootDirectories()) {
                    Stream<Path> pathStream = Files.walk(path.resolve("/" + var11));

                    try {
                        pathStream.filter(Files::isRegularFile).filter(this::filter).forEach((var1x) -> {
                            String var2 = var1x.toString();
                            String var3 = a(var2 = var2.substring(var2.indexOf(47, 1) + 1));
                            if (!this.a.contains(var3)) {
                                AuspiceLogger.debug(AuspiceDebug.FOLDER_REGISTRY, "Visting default of " + this + " -> " + var3 + " | " + var2);
                                Entry var6 = new Entry(var3, var2, this.folder.resolve(var2), var1x);
                                if (this.copyDefaults) {
                                    try {
                                        if (!Files.exists(var6.getPath())) {
                                            Files.createDirectories(var6.getPath().getParent());
                                            Files.copy(var6.getDefaultPath(), var6.getPath());
                                        }
                                    } catch (IOException var5) {
                                        throw new RuntimeException(var5);
                                    }
                                }

                                try {
                                    this.handle(var6);
                                } catch (Throwable var4) {
                                    throw new IllegalStateException("Error while registering default " + var3 + " (" + var2 + ") for " + this, var4);
                                }
                            }
                        });
                    } catch (Throwable var8) {
                        if (pathStream != null) {
                            try {
                                pathStream.close();
                            } catch (Throwable var6) {
                                var8.addSuppressed(var6);
                            }
                        }

                        throw var8;
                    }
                    //Safety
                    //noinspection ConstantValue
                    if (pathStream != null) {
                        pathStream.close();
                    }
                }
            } catch (Throwable var9) {
                if (var12 != null) {
                    try {
                        var12.close();
                    } catch (Throwable var5) {
                        var9.addSuppressed(var5);
                    }
                }

                throw var9;
            }

            if (var12 != null) {
                var12.close();
            }
        } catch (IOException var10) {
            throw new RuntimeException("Failed to create file system for defaults: " + this, var10);
        }
    }

    public String toString() {
        return this.getClass().getSimpleName() + '{' + "displayName='" + this.displayName + '\'' + ", folder=" + this.folder + ", copyDefaults=" + this.copyDefaults + ", useDefaults=" + this.useDefaults + '}';
    }

    public static final class Entry {
        private final @NotNull String name;
        private final @NotNull String relativeName;
        private final @NotNull Path path;
        private final @Nullable Path defaultPath;

        public Entry(@NotNull String var1, @NotNull String var2, @NotNull Path var3, @Nullable Path var4) {
            this.name = var1;
            this.relativeName = var2;
            this.path = var3;
            this.defaultPath = var4;
        }

        @NotNull
        public String getName() {
            return this.name;
        }

        @NotNull
        public String getRelativeName() {
            return this.relativeName;
        }

        @Nullable
        public Path getDefaultPath() {
            return this.defaultPath;
        }

        @NotNull
        public Path getPath() {
            return this.path;
        }

        public boolean isDefault() {
            return this.defaultPath != null;
        }
    }
}

