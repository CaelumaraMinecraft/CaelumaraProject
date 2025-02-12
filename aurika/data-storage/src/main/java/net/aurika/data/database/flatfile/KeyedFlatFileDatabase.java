package net.aurika.data.database.flatfile;

import net.aurika.data.api.KeyedDataObject;
import net.aurika.data.handler.KeyedDataHandler;
import net.aurika.data.database.base.KeyedDatabase;
import net.aurika.util.file.FSUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class KeyedFlatFileDatabase<K, T extends KeyedDataObject<K>> implements KeyedDatabase<K, T> {

    private final @NotNull String extension;
    private final @NotNull Path folder;
    private final @NotNull KeyedDataHandler<K, T> dataHandler;
    private int d;

    public KeyedFlatFileDatabase(@NotNull String extension, @NotNull Path folder, @NotNull KeyedDataHandler<K, T> dataHandler) {
        Objects.requireNonNull(extension, "extension");
        Objects.requireNonNull(folder, "folder");
        Objects.requireNonNull(dataHandler, "dataHandler");
        this.extension = extension;
        this.folder = folder;
        this.dataHandler = dataHandler;
        this.d = 10;
        if (!Files.exists(this.folder, new LinkOption[0])) {
            try {
                Files.createDirectories(this.folder);
            } catch (IOException var4) {
                var4.printStackTrace();
            }
        }
    }

    public final @NotNull String getExtension() {
        return this.extension;
    }

    public final @NotNull Path getFolder() {
        return this.folder;
    }

    public final @NotNull KeyedDataHandler<K, T> getDataHandler() {
        return this.dataHandler;
    }

    private K a(Path var1) {
        String var2 = var1.getFileName().toString();
        var2 = var2.substring(0, var2.length() - this.extension.length() - 1);

        try {
            return this.dataHandler.getIdHandler().fromString(var2);
        } catch (Throwable var3) {
            throw new IllegalArgumentException("Malformed data ID from file name: '" + var1 + "' (Did you put/rename a file/folder inside Kingdoms data folders?)");
        }
    }

    @NotNull
    public final Path fileFromKey(@NotNull K key) {
        Objects.requireNonNull(key, "key");
        return folder.resolve(this.dataHandler.getIdHandler().toString(key) + '.' + this.extension);
    }

    @Nullable
    public final T load(@NotNull K key) {
        Objects.requireNonNull(key, "key");
        Path var2 = this.fileFromKey(key);
        if (!Files.exists(var2, new LinkOption[0])) {
            return null;
        } else {
            BufferedReader var11;
            try {
                var11 = Files.newBufferedReader(var2, StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            T var10;

            Objects.requireNonNull(var11, "");
            var10 = this.load(key, var11);

            return var10;
        }
    }

    public final void load(@NotNull Collection<K> keys, @NotNull Consumer<T> var2) {
        Objects.requireNonNull(keys, "");
        Objects.requireNonNull(var2, "");

        for (K var3 : keys) {
            T obj = this.load(var3);
            if (obj != null) {
                var2.accept(obj);
            }
        }
    }

    public abstract @Nullable T load(@NotNull K key, @NotNull BufferedReader var2);

    public abstract void save(@NotNull T obj, @NotNull BufferedWriter var2);

    public final void save(@NotNull T data) {
        Objects.requireNonNull(data, "data");
        String var2 = "" + data.getKey() + '.' + this.extension;
        Path var12 = this.folder.resolve(var2);

        try {
            BufferedWriter var13 = FSUtil.standardWriter(var12);
            Throwable thr = null;
            boolean var8 = false;

            try {
                var8 = true;
                Intrinsics.checkNotNull(var13);
                this.save(data, var13);
                var8 = false;
            } catch (Throwable var9) {
                thr = var9;
                throw var9;
            } finally {
                if (var8) {
                    CloseableUtils.closeFinally(var13, thr);
                }
            }

            CloseableUtils.closeFinally(var13, null);
        } catch (IOException var11) {
            var11.printStackTrace();
        }
    }

    public final void delete(@NotNull K key) {
        Objects.requireNonNull(key, "");

        try {
            Files.deleteIfExists(this.fileFromKey(key));
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    public final boolean hasData(@NotNull K key) {
        Objects.requireNonNull(key, "");
        return Files.exists(this.fileFromKey(key), new LinkOption[0]);
    }

    @NotNull
    public final Collection<K> getAllDataKeys() {
        List<K> var1 = new ArrayList<>(this.d);

        try {
            DirectoryStream<Path> pathStream = Files.newDirectoryStream(this.folder);

            for (Path path : pathStream) {
                if (Files.isRegularFile(path, new LinkOption[0])) {
                    Objects.requireNonNull(path);
                    var1.add(this.a(path));
                }
            }
        } catch (IOException var12) {
            throw new RuntimeException(var12);  // TODO
        }

        this.d = RangesKt.coerceAtLeast(var1.size(), this.d);
        return var1;
    }

    public final void deleteAllData() {
        try {
            FSUtil.deleteFolder(this.folder);
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public int count() {
        return FSUtil.countEntriesOf(this.folder);
    }

    public final @NotNull Collection<T> loadAllData(@Nullable Predicate<K> keyFilter) {
        List<T> var2 = new ArrayList<>(this.d);

        try {
            DirectoryStream<Path> var3 = Files.newDirectoryStream(this.folder);

            for (Path o : var3) {
                Path var6;
                if (Files.isRegularFile(var6 = o, new LinkOption[0])) {
                    Objects.requireNonNull(var6);
                    K var7 = this.a(var6);
                    if (keyFilter == null || keyFilter.test(var7)) {
                        try {
                            T var10000 = this.load(var7);
                            if (var10000 == null) {
                                StringBuilder sb = (new StringBuilder("Couldn't load bulk data ")).append(var7).append("w -> ").append(var6).append(" -> ");
                                LinkOption[] var10004 = new LinkOption[0];
                                sb.append(Files.exists(var6, Arrays.copyOf(var10004, var10004.length))).append(" -> ").append(this.fileFromKey(var7)).append(" -> ");
                                Path var10003 = this.fileFromKey(var7);
                                var10004 = new LinkOption[0];
                                throw new IllegalStateException(sb.append(Files.exists(var10003, Arrays.copyOf(var10004, var10004.length))).toString());
                            }

                            var2.add(var10000);
                        } catch (Throwable var13) {
                            throw new RuntimeException("Error while loading " + var7, var13);
                        }
                    }
                }
            }

        } catch (Exception var16) {
            throw new RuntimeException(var16);  // TODO
        }

        this.d = RangesKt.coerceAtLeast(var2.size(), this.d);
        return var2;
    }

    public final void save(@NotNull Collection<T> data) {
        Objects.requireNonNull(data, "");

        for (T t : data) {
            this.save(t);
        }
    }

    public final void close() {
    }
}
