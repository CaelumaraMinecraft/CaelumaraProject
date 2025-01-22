package top.auspice.data.database.flatfile;

import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.base.KeyedAuspiceObject;
import top.auspice.data.database.base.KeyedKingdomsDatabase;
import top.auspice.data.handlers.abstraction.KeyedDataHandler;
import top.auspice.utils.filesystem.FSUtil;
import top.auspice.utils.internal.CloseableUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class KeyedFlatFileDatabase<K, T extends KeyedAuspiceObject<K>> implements KeyedKingdomsDatabase<K, T> {

    private final @NotNull String extension;
    private final @NotNull Path folder;
    private final @NotNull KeyedDataHandler<K, T> dataHandler;
    private int d;

    public KeyedFlatFileDatabase(@NotNull String var1, @NotNull Path var2, @NotNull KeyedDataHandler<K, T> var3) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        Intrinsics.checkNotNullParameter(var3, "");
        this.extension = var1;
        this.folder = var2;
        this.dataHandler = var3;
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

    private final K a(Path var1) {
        String var2 = var1.getFileName().toString();
        String var10000 = var2.substring(0, var2.length() - this.extension.length() - 1);
        Intrinsics.checkNotNullExpressionValue(var10000, "");
        var2 = var10000;

        try {
            return this.dataHandler.getIdHandler().fromString(var2);
        } catch (Throwable var3) {
            throw new IllegalArgumentException("Malformed data ID from file name: '" + var1 + "' (Did you put/rename a file/folder inside Kingdoms data folders?)");
        }
    }

    @NotNull
    public final Path fileFromKey(@NotNull K var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        Path var10000 = this.folder.resolve(this.dataHandler.getIdHandler().toString(var1) + '.' + this.extension);
        Intrinsics.checkNotNullExpressionValue(var10000, "");
        return var10000;
    }

    @Nullable
    public final T load(@NotNull K var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        Path var2;
        if (!Files.exists(var2 = this.fileFromKey(var1), new LinkOption[0])) {
            return null;
        } else {
            BufferedReader var11;
            try {
                var11 = Files.newBufferedReader(var2, StandardCharsets.UTF_8);
            } catch (IOException e) {  // TODO
                throw new RuntimeException(e);
            }
            Throwable var3 = null;
            boolean var7 = false;

            T var10;

            try {
                var7 = true;
                BufferedReader var4 = var11;
                Intrinsics.checkNotNull(var4);
                var10 = this.load(var1, var4);
                var7 = false;
            } catch (Throwable var8) {
                var3 = var8;
                throw var8;
            } finally {
                if (var7) {
                    CloseableUtils.closeFinally(var11, var3);
                }
            }

            CloseableUtils.closeFinally(var11, null);
            return var10;
        }
    }

    public final void load(@NotNull Collection<K> var1, @NotNull Consumer<T> var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");

        for (K var3 : var1) {
            T obj = this.load(var3);
            if (obj != null) {
                var2.accept(obj);
            }
        }
    }

    @Nullable
    public abstract T load(@NotNull K key, @NotNull BufferedReader var2);

    public abstract void save(@NotNull T obj, @NotNull BufferedWriter var2);

    public final void save(@NotNull T obj) {
        Intrinsics.checkNotNullParameter(obj, "");
        String var2 = "" + obj.getKey() + '.' + this.extension;
        Path var12 = this.folder.resolve(var2);

        try {
            BufferedWriter var13 = FSUtil.standardWriter(var12);
            Throwable thr = null;
            boolean var8 = false;

            try {
                var8 = true;
                Intrinsics.checkNotNull(var13);
                this.save(obj, var13);
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

    public final void delete(@NotNull K var1) {
        Intrinsics.checkNotNullParameter(var1, "");

        try {
            Files.deleteIfExists(this.fileFromKey(var1));
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    public final boolean hasData(@NotNull K var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        return Files.exists(this.fileFromKey(var1), new LinkOption[0]);
    }

    @NotNull
    public final Collection<K> getAllDataKeys() {
        List<K> var1 = new ArrayList<>(this.d);

        try {
            DirectoryStream<Path> pathStream = Files.newDirectoryStream(this.folder);
            Throwable var3 = null;
            boolean var9 = false;

            try {
                var9 = true;

                for (Path path : pathStream) {
                    if (Files.isRegularFile(path, new LinkOption[0])) {
                        Intrinsics.checkNotNull(path);
                        var1.add(this.a(path));
                    }
                }

                var9 = false;
            } catch (Throwable var10) {
                var3 = var10;
                throw var10;
            } finally {
                if (var9) {
                    CloseableUtils.closeFinally(pathStream, var3);
                }
            }

            CloseableUtils.closeFinally(pathStream, null);
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

    public final @NotNull Collection<T> loadAllData(@Nullable Predicate<K> var1) {
        List<T> var2 = new ArrayList<>(this.d);

        try {
            DirectoryStream<Path> var3 = Files.newDirectoryStream(this.folder);
            Throwable var4 = null;
            boolean var12 = false;

            try {
                var12 = true;

                for (Path o : var3) {
                    Path var6;
                    if (Files.isRegularFile(var6 = o, new LinkOption[0])) {
                        Intrinsics.checkNotNull(var6);
                        K var7 = this.a(var6);
                        if (var1 == null || var1.test(var7)) {
                            try {
                                T var10000 = this.load(var7);
                                if (var10000 == null) {
                                    StringBuilder var10002 = (new StringBuilder("Couldn't load bulk data ")).append(var7).append(" -> ").append(var6).append(" -> ");
                                    LinkOption[] var10004 = new LinkOption[0];
                                    var10002.append(Files.exists(var6, Arrays.copyOf(var10004, var10004.length))).append(" -> ").append(this.fileFromKey(var7)).append(" -> ");
                                    Path var10003 = this.fileFromKey(var7);
                                    var10004 = new LinkOption[0];
                                    throw new IllegalStateException(var10002.append(Files.exists(var10003, Arrays.copyOf(var10004, var10004.length))).toString());
                                }

                                var2.add(var10000);
                            } catch (Throwable var13) {
                                throw new RuntimeException("Error while loading " + var7, var13);
                            }
                        }
                    }
                }

                var12 = false;
            } catch (Throwable var14) {
                var4 = var14;
                throw var14;
            } finally {
                if (var12) {
                    CloseableUtils.closeFinally(var3, var4);
                }
            }

            CloseableUtils.closeFinally(var3, null);
        } catch (Exception var16) {
            throw new RuntimeException(var16);  // TODO
        }

        this.d = RangesKt.coerceAtLeast(var2.size(), this.d);
        return var2;
    }

    public final void save(@NotNull Collection<T> var1) {
        Intrinsics.checkNotNullParameter(var1, "");

        for (T var2 : var1) {
            this.save(var2);
        }
    }

    public final void close() {
    }
}
