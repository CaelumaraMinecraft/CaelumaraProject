//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package top.auspice.data.database.flatfile;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.base.AuspiceObject;
import top.auspice.data.database.base.SingularKingdomsDatabase;
import top.auspice.data.handlers.abstraction.SingularDataHandler;
import top.auspice.utils.filesystem.FSUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

public abstract class SingularFlatFileDatabase<T extends AuspiceObject> implements SingularKingdomsDatabase<T> {
    @NotNull
    private final Path a;
    @NotNull
    private final SingularDataHandler<T> b;

    public SingularFlatFileDatabase(@NotNull Path var1, @NotNull SingularDataHandler<T> var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        this.a = var1;
        this.b = var2;
    }

    @NotNull

    public final Path getFile() {
        return this.a;
    }

    @NotNull

    public final SingularDataHandler<T> getDataHandler() {
        return this.b;
    }

    @Nullable
    public final T load() {
        if (!Files.exists(this.a, new LinkOption[0])) {
            return null;
        } else {
            BufferedReader var1 = null;
            try {
                var1 = Files.newBufferedReader(this.a, StandardCharsets.UTF_8);
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
                    CloseableUtil.closeFinally(var1, var2);
                }
            }

            CloseableUtil.closeFinally(var1, null);
            return var9;
        }
    }

    @Nullable
    public abstract T load(@NotNull BufferedReader var1);

    public abstract void save(@NotNull T var1, @NotNull BufferedWriter var2);

    public final void save(@NotNull T obj) {
        Intrinsics.checkNotNullParameter(obj, "");

        try {
            BufferedWriter var2 = FSUtil.standardWriter(this.a);
            Throwable thr = null;
            boolean var8 = false;

            try {
                var8 = true;
                Intrinsics.checkNotNull(var2);
                this.save(obj, var2);
                var8 = false;
            } catch (Throwable var9) {
                thr = var9;
                throw var9;
            } finally {
                if (var8) {
                    CloseableUtil.closeFinally(var2, thr);
                }
            }

            CloseableUtil.closeFinally(var2, null);
        } catch (IOException var11) {
            var11.printStackTrace();
        }
    }

    public final boolean hasData() {
        return Files.exists(this.a, new LinkOption[0]);
    }

    public final void deleteAllData() {
        try {
            Files.delete(this.a);
        } catch (IOException e) {  // TODO
            throw new RuntimeException(e);
        }
    }

    public final void close() {
    }
}
