package top.auspice.data.database.sql.statements.setters;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

final class a {
    @NotNull
    private final String a;
    @NotNull
    private final Function1<Integer, Unit> b;
    @NotNull
    private final RuntimeException c;

    public a(@NotNull String var1, @NotNull Function1<Integer, Unit> var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        this.a = var1;
        this.b = var2;
        this.c = new RuntimeException();
    }

    @NotNull
    public String a() {
        return this.a;
    }

    @NotNull
    public Function1<Integer, Unit> b() {
        return this.b;
    }

    @NotNull
    public RuntimeException c() {
        return this.c;
    }
}
