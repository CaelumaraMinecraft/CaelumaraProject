package top.auspice.data.database.sql.statements.setters;

import com.google.gson.JsonElement;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.data.database.DatabaseType;
import top.auspice.data.database.sql.statements.SQLUpsert;
import top.auspice.utils.gson.KingdomsGson;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

public  class PreparedNamedSetterStatement implements SimplePreparedStatement {
    @NotNull
    private final DatabaseType a;
    @NotNull
    private final List<a> b;
    public PreparedStatement statement;
    private boolean c;
    private boolean d;
    @NotNull
    private final Map<String, Integer> e;
    @NotNull
    private Set<String> f;

    public PreparedNamedSetterStatement(@NotNull DatabaseType var1, @NotNull Map<String, Integer> var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        this.a = var1;
        this.b = new ArrayList<>(30);
        this.e = (Map)(new LinkedHashMap(var2));
        this.f = (Set)(new HashSet());
    }

    @NotNull
    public final PreparedStatement getStatement() {
        PreparedStatement var10000 = this.statement;
        if (var10000 != null) {
            return var10000;
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("");
            return null;
        }
    }

    public final void setStatement(@NotNull PreparedStatement var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        this.statement = var1;
    }

    public final void addParameterIfNotExist(@NotNull String var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        if (!this.e.containsKey(var1)) {
            this.a(var1);
        }

    }

    private final void a(String var1) {
        this.e.put(var1, this.e.size() + 1);
    }

    private final void a(String var1, Function1<? super Integer, Unit> var2) {
        this.f.add(var1);
        if (this.c) {
            var2.invoke(this.b(var1));
        } else {
            this.b.add(new a(var1, var2));
        }
    }

    private final void a() {
        if (!this.c) {
            throw new IllegalStateException("Statement not built yet");
        }
    }

    private final int b(String var1) {
        Integer var10000 = (Integer)this.e.get(var1);
        if (var10000 != null) {
            return var10000;
        } else {
            throw new IllegalStateException("Unknown parameter index for: " + var1 + " (" + this.e + ')');
        }
    }

    public final void buildStatement(@NotNull String var1, @NotNull Connection var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        if (!this.c) {
            StringJoiner var3 = new StringJoiner((CharSequence)", ");
            StringJoiner var4 = new StringJoiner((CharSequence)", ");

            for(String var6 : this.e.keySet()) {
                var3.add((CharSequence)("`" + var6 + '`'));
                var4.add((CharSequence)"?");
            }

            Iterator var8 = this.b.iterator();

            while(var8.hasNext()) {
                String var10 = ((a)var8.next()).a();
                if (!this.e.containsKey(var10)) {
                    this.a(var10);
                    var3.add((CharSequence)("`" + var10 + '`'));
                    var4.add((CharSequence)"?");
                }
            }

            DatabaseType var10000 = this.a;
            String var10003 = var3.toString();
            Intrinsics.checkNotNullExpressionValue(var10003, "");
            String var10004 = var4.toString();
            Intrinsics.checkNotNullExpressionValue(var10004, "");
            String var9 = var10000.createStatement(new SQLUpsert(var10003, var10004), var1);

            try {
                PreparedStatement var10001 = var2.prepareStatement(var9);
                Intrinsics.checkNotNullExpressionValue(var10001, "");
                this.setStatement(var10001);
            } catch (Throwable var7) {
                KLogger.error("Failed to build setter statement with query: " + var9);
                throw var7;
            }

            this.c = true;
        }
    }

    private void b() {
        for(a var2 : this.b) {
            try {
                int var3 = this.b(var2.a());
                var2.b().invoke(var3);
            } catch (Throwable var4) {
                RuntimeException var5;
                (var5 = new RuntimeException(var4)).setStackTrace(var2.c().getStackTrace());
                throw new RuntimeException((Throwable)var5);
            }
        }

    }

    public final void execute() {
        this.a();
        AutoCloseable var1 = (AutoCloseable)this.getStatement();
        Throwable var2 = null;
        boolean var6 = false;

        try {
            var6 = true;
            PreparedStatement var10000 = (PreparedStatement)var1;
            if (this.d) {
                Serializable var12 = (Serializable)this.getStatement().executeBatch();
                var6 = false;
            } else {
                this.b();
                this.c();
                Serializable var13 = (Serializable)this.getStatement().execute();
                var6 = false;
            }
        } catch (Throwable var7) {
            var2 = var7;
            throw var7;
        } finally {
            if (var6) {
                AutoCloseableKt.closeFinally(var1, var2);
            }
        }

        AutoCloseableKt.closeFinally(var1, (Throwable)null);
    }

    private final void c() {
        Map var1 = this.e;
        LinkedHashMap var2 = new LinkedHashMap();
        Iterator var5 = var1.entrySet().iterator();

        while(var5.hasNext()) {
            Map.Entry var3;
            String var4 = (String)(var3 = (Map.Entry)var5.next()).getKey();
            if (!this.f.contains(var4)) {
                var2.put(var3.getKey(), var3.getValue());
            }
        }

        for(Map.Entry var8 : (var6 = (Map)var2).entrySet()) {
            this.getStatement().setObject(((Number)var8.getValue()).intValue(), (Object)null);
        }

        this.f = (Set)(new HashSet());
    }

    public final void addBatch() {
        this.a();
        if (!this.d) {
            this.b();
        }

        this.c();
        this.getStatement().addBatch();
        this.getStatement().clearParameters();
        this.d = true;
    }

    public final void setString(@NotNull String var1, @Nullable final String var2) {
        Intrinsics.checkNotNullParameter(var1, "");

        final class NamelessClass_1 extends Lambda implements Function1<Integer, Unit> {
            NamelessClass_1() {
                super(1);
            }

            public final void a(int var1) {
                PreparedNamedSetterStatement.this.getStatement().setString(var1, var2);
            }
        }

        this.a(var1, new NamelessClass_1());
    }

    public final void setInt(@NotNull String var1, final int var2) {
        Intrinsics.checkNotNullParameter(var1, "");

        final class NamelessClass_2 extends Lambda<Unit> implements Function1<Integer, Unit> {
            NamelessClass_2() {
                super(1);
            }

            public final void invoke(int var1) {
                PreparedNamedSetterStatement.this.getStatement().setInt(var1, var2);
            }
        }

        this.a(var1, new NamelessClass_2());
    }

    public final void setJson(@NotNull String var1, @Nullable final JsonElement var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        switch (PreparedNamedSetterStatement.WhenMappings.$EnumSwitchMapping$0[this.a.ordinal()]) {
            case 1:
                final class NamelessClass_3 extends Lambda implements Function1<Integer, Unit> {
                    NamelessClass_3() {
                        super(1);
                    }

                    public final void a(int var1) {
                        PreparedStatement var10000 = PreparedNamedSetterStatement.this.getStatement();
                        int var10001 = var1;
                        JsonElement var10002 = var2;
                        byte[] var9;
                        if (var10002 != null) {
                            JsonElement var2x = var10002;
                            PreparedStatement var3 = var10000;
                            String var6 = KingdomsGson.toString(var2x);
                            Intrinsics.checkNotNullExpressionValue(var6, "");
                            String var4 = var6;
                            Charset var7 = StandardCharsets.UTF_8;
                            Intrinsics.checkNotNullExpressionValue(var7, "");
                            byte[] var8 = var4.getBytes(var7);
                            Intrinsics.checkNotNullExpressionValue(var8, "");
                            byte[] var5 = var8;
                            var10000 = var3;
                            var10001 = var1;
                            var9 = var5;
                        } else {
                            var9 = null;
                        }

                        var10000.setBytes(var10001, var9);
                    }
                }

                this.a(var1, new NamelessClass_3());
                return;
            case 2:
                final class NamelessClass_4 extends Lambda<Unit> implements Function1<Integer, Unit> {
                    NamelessClass_4() {
                        super(1);
                    }

                    public final void a(int var1) {
                        PGobject var2x;
                        (var2x = new PGobject()).setType("json");
                        var2x.setValue(var2 == null ? null : KingdomsGson.toString(var2));
                        PreparedNamedSetterStatement.this.getStatement().setObject(var1, var2x);
                    }
                }

                this.a(var1, new NamelessClass_4());
                return;
            default:
                PreparedNamedSetterStatement var10000 = this;
                String var10001 = var1;
                String var10002;
                if (var2 != null) {
                    String var3 = var1;
                    var1 = KingdomsGson.toString(var2);
                    var10000 = this;
                    var10001 = var3;
                    var10002 = var1;
                } else {
                    var10002 = null;
                }

                var10000.setString(var10001, var10002);
        }
    }

    public final void setFloat(@NotNull String var1, final float var2) {
        Intrinsics.checkNotNullParameter(var1, "");

        final class NamelessClass_5 extends Lambda implements Function1<Integer, Unit> {
            NamelessClass_5() {
                super(1);
            }

            public final void a(int var1) {
                PreparedNamedSetterStatement.this.getStatement().setFloat(var1, var2);
            }
        }

        this.a(var1, new NamelessClass_5());
    }

    public final void setLong(@NotNull String var1, final long var2) {
        Intrinsics.checkNotNullParameter(var1, "");

        final class NamelessClass_6 extends Lambda implements Function1<Integer, Unit> {
            NamelessClass_6() {
                super(1);
            }

            public final void a(int var1) {
                PreparedNamedSetterStatement.this.getStatement().setLong(var1, var2);
            }
        }

        this.a(var1, new NamelessClass_6());
    }

    public final void setBoolean(@NotNull String var1, final boolean var2) {
        Intrinsics.checkNotNullParameter(var1, "");

        final class NamelessClass_7 extends Lambda implements Function1<Integer, Unit> {
            NamelessClass_7() {
                super(1);
            }

            public final void a(int var1) {
                PreparedNamedSetterStatement.this.getStatement().setBoolean(var1, var2);
            }
        }

        this.a(var1, new NamelessClass_7());
    }

    public final void setDouble(@NotNull String var1, final double var2) {
        Intrinsics.checkNotNullParameter(var1, "");

        final class NamelessClass_8 extends Lambda implements Function1<Integer, Unit> {
            NamelessClass_8() {
                super(1);
            }

            public final void a(int var1) {
                PreparedNamedSetterStatement.this.getStatement().setDouble(var1, var2);
            }
        }

        this.a(var1, new NamelessClass_8());
    }

    public final void setUUID(@NotNull String var1, @Nullable final UUID var2) {
        Intrinsics.checkNotNullParameter(var1, "");

        final class NamelessClass_9 extends Lambda implements Function1<Integer, Unit> {
            NamelessClass_9() {
                super(1);
            }

            public final void a(int var1) {
                if (PreparedNamedSetterStatement.this.a == DatabaseType.PostgreSQL) {
                    PreparedNamedSetterStatement.this.getStatement().setObject(var1, var2);
                } else {
                    PreparedNamedSetterStatement.this.getStatement().setBytes(var1, var2 == null ? null : SQLDatabase.Companion.asBytes(var2));
                }
            }
        }

        this.a(var1, new NamelessClass_9());
    }
}
