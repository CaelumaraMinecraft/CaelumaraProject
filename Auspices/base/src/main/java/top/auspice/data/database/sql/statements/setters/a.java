package top.auspice.data.database.sql.statements.setters;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

final class a {

    private final @NotNull String a;
    private final @NotNull Consumer<Integer> b;
    private final @NotNull RuntimeException c;

    public a(@NotNull String var1, @NotNull Consumer<Integer> var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        this.a = var1;
        this.b = var2;
        this.c = new RuntimeException();
    }

    public @NotNull String a() {
        return this.a;
    }

    public @NotNull Consumer<Integer> b() {
        return this.b;
    }

    public @NotNull RuntimeException c() {
        return this.c;
    }
}
