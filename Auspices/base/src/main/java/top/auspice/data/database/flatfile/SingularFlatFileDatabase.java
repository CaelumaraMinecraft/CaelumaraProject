package top.auspice.data.database.flatfile;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.data.object.DataObject;
import top.auspice.data.database.base.SingularDatabase;
import top.auspice.data.handlers.abstraction.SingularDataHandler;
import top.auspice.utils.filesystem.FSUtil;
import top.auspice.utils.unsafe.CloseableUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Objects;

public abstract class SingularFlatFileDatabase<T extends DataObject.Impl> implements SingularDatabase<T> {

    private final @NotNull Path file;
    private final @NotNull SingularDataHandler<T> dataHandler;

    public SingularFlatFileDatabase(@NotNull Path file, @NotNull SingularDataHandler<T> dataHandler) {
        Objects.requireNonNull(file, "file");
        Objects.requireNonNull(dataHandler, "dataHandler");
        this.file = file;
        this.dataHandler = dataHandler;
    }

    public final @NotNull Path getFile() {
        return this.file;
    }

    public final @NotNull SingularDataHandler<T> getDataHandler() {
        return this.dataHandler;
    }

    public final @NotNull T load() {
        if (!Files.exists(this.file, new LinkOption[0])) {
            return null;
        } else {
            BufferedReader var1 = null;
            try {
                var1 = Files.newBufferedReader(this.file, StandardCharsets.UTF_8);
            } catch (IOException e) {  // TODO
                throw new RuntimeException(e);
            }
            Throwable var2 = null;
            boolean var6 = false;

            T var9;
            try {
                var6 = true;
                Intrinsics.checkNotNull(var1);
                var9 = this.load(var1);
                var6 = false;
            } catch (Throwable var7) {
                var2 = var7;
                throw var7;
            } finally {
                if (var6) {
                    CloseableUtils.closeFinally(var1, var2);
                }
            }

            CloseableUtils.closeFinally(var1, null);
            return var9;
        }
    }

    public abstract @Nullable T load(@NotNull BufferedReader var1);

    public abstract void save(@NotNull T var1, @NotNull BufferedWriter var2);

    public final void save(@NotNull T data) {
        Objects.requireNonNull(data, "");

        try {
            BufferedWriter writer = FSUtil.standardWriter(this.file);
            Throwable thr = null;
            boolean var8 = false;

            try {
                var8 = true;
                Intrinsics.checkNotNull(writer);
                this.save(data, writer);
                var8 = false;
            } catch (Throwable var9) {
                thr = var9;
                throw var9;
            } finally {
                if (var8) {
                    CloseableUtils.closeFinally(writer, thr);
                }
            }

            CloseableUtils.closeFinally(writer, null);
        } catch (IOException var11) {
            var11.printStackTrace();
        }
    }

    public final boolean hasData() {
        return Files.exists(this.file, new LinkOption[0]);
    }

    public final void deleteAllData() {
        try {
            Files.delete(this.file);
        } catch (IOException e) {  // TODO
            throw new RuntimeException(e);
        }
    }

    public final void close() {
    }
}
