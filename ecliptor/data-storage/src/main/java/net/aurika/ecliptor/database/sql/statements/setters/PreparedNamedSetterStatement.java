package net.aurika.ecliptor.database.sql.statements.setters;

import com.google.gson.JsonElement;
import kotlin.jvm.internal.Intrinsics;
import net.aurika.annotations.data.LateInit;
import net.aurika.ecliptor.database.DatabaseType;
import net.aurika.ecliptor.database.sql.base.SQLDatabase;
import net.aurika.ecliptor.database.sql.statements.SQLUpsert;
import net.aurika.util.gson.AurikaGson;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.postgresql.util.PGobject;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Consumer;

public class PreparedNamedSetterStatement implements SimplePreparedStatement {

    private final @NotNull DatabaseType databaseType;
    private final @NotNull List<a> b;
    public @LateInit PreparedStatement statement;
    private boolean c;
    private boolean d;
    private final @NotNull Map<String, Integer> e;
    private @NotNull Set<String> f;

    public PreparedNamedSetterStatement(@NotNull DatabaseType var1, @NotNull Map<String, Integer> var2) {
        Validate.Arg.notNull(var1, "");
        Validate.Arg.notNull(var2, "");
        this.databaseType = var1;
        this.b = new ArrayList<>(30);
        this.e = new LinkedHashMap<>(var2);
        this.f = new HashSet<>();
    }

    public @NotNull PreparedStatement getStatement() {
        PreparedStatement var10000 = this.statement;
        if (var10000 != null) {
            return var10000;
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("statement");
            return null;
        }
    }

    public void setStatement(@NotNull PreparedStatement var1) {
        Validate.Arg.notNull(var1, "");
        this.statement = var1;
    }

    public void addParameterIfNotExist(@NotNull String var1) {
        Validate.Arg.notNull(var1, "");
        if (!this.e.containsKey(var1)) {
            this.a(var1);
        }
    }

    private void a(String var1) {
        this.e.put(var1, this.e.size() + 1);
    }

    private void a(String var1, Consumer<Integer> var2) {
        this.f.add(var1);
        if (this.c) {
            var2.accept(this.b(var1));
        } else {
            this.b.add(new a(var1, var2));
        }
    }

    private void a() {
        if (!this.c) {
            throw new IllegalStateException("Statement not built yet");
        }
    }

    private int b(String var1) {
        Integer var10000 = this.e.get(var1);
        if (var10000 != null) {
            return var10000;
        } else {
            throw new IllegalStateException("Unknown parameter index for: " + var1 + " (" + this.e + ')');
        }
    }

    public void buildStatement(@NotNull String var1, @NotNull Connection var2) {
        Validate.Arg.notNull(var1, "");
        Validate.Arg.notNull(var2, "");
        if (!this.c) {
            StringJoiner var3 = new StringJoiner(", ");
            StringJoiner var4 = new StringJoiner(", ");

            for (String var6 : this.e.keySet()) {
                var3.add("`" + var6 + '`');
                var4.add("?");
            }

            for (net.aurika.ecliptor.database.sql.statements.setters.a value : this.b) {
                String var10 = value.a();
                if (!this.e.containsKey(var10)) {
                    this.a(var10);
                    var3.add("`" + var10 + '`');
                    var4.add("?");
                }
            }

            String var10003 = var3.toString();
            Intrinsics.checkNotNullExpressionValue(var10003, "");
            String var10004 = var4.toString();
            Intrinsics.checkNotNullExpressionValue(var10004, "");
            String var9 = this.databaseType.createStatement(new SQLUpsert(var10003, var10004), var1);

            try {
                PreparedStatement var10001 = var2.prepareStatement(var9);
                Intrinsics.checkNotNullExpressionValue(var10001, "");
                this.setStatement(var10001);
            } catch (Throwable var7) {
                AuspiceLogger.error("Failed to build setter statement with query: " + var9);
                throw new RuntimeException(var7);  // TODO
            }

            this.c = true;
        }
    }

    private void b() {
        for (a var2 : this.b) {
            try {
                int var3 = this.b(var2.a());
                var2.b().accept(var3);
            } catch (Throwable var4) {
                RuntimeException var5 = new RuntimeException(var4);
                var5.setStackTrace(var2.c().getStackTrace());
                throw new RuntimeException(var5);
            }
        }
    }

    public void execute() {
        this.a();
        AutoCloseable var1 = this.getStatement();

        try {
            if (this.d) {
                this.getStatement().executeBatch();
            } else {
                this.b();
                this.c();
                this.getStatement().execute();
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private void c() {
        final Map<String, Integer> e = this.e;
        final LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : e.entrySet()) {
            if (!this.f.contains(entry.getKey())) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        for (Map.Entry<String, Integer> stringIntegerEntry : linkedHashMap.entrySet()) {
            try {
                this.getStatement().setObject(stringIntegerEntry.getValue(), null);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        this.f = new HashSet<>();
    }

    public void addBatch() {
        this.a();
        if (!this.d) {
            this.b();
        }

        this.c();
        try {
            this.getStatement().addBatch();
            this.getStatement().clearParameters();
        } catch (SQLException ex) {  // TODO
            throw new RuntimeException(ex);
        }
        this.d = true;
    }

    public void setString(@NotNull String key, @Nullable String value) {
        Validate.Arg.notNull(key, "");
        this.a(key, (i) -> {
            try {
                PreparedNamedSetterStatement.this.getStatement().setString(i, value);
            } catch (SQLException ex) {  // TODO
                throw new RuntimeException(ex);
            }
        });
    }

    public void setInt(@NotNull String key, int value) {
        Validate.Arg.notNull(key, "");
        this.a(key, (i) -> {
            try {
                PreparedNamedSetterStatement.this.getStatement().setInt(i, value);
            } catch (SQLException ex) {  // TODO
                throw new RuntimeException(ex);
            }
        });
    }

    public void setJson(@NotNull String var1, @Nullable JsonElement element) {
        Validate.Arg.notNull(var1, "");
        switch (this.databaseType) {
            case H2:
                this.a(var1, i -> {
                    PreparedStatement var10000 = PreparedNamedSetterStatement.this.getStatement();
                    int var10001 = i;
                    byte[] var9;
                    if (element != null) {
                        String var6 = AurikaGson.toString(element);
                        Intrinsics.checkNotNullExpressionValue(var6, "");
                        Charset var7 = StandardCharsets.UTF_8;
                        Intrinsics.checkNotNullExpressionValue(var7, "");
                        byte[] var8 = var6.getBytes(var7);
                        Intrinsics.checkNotNullExpressionValue(var8, "");
                        var9 = var8;
                    } else {
                        var9 = null;
                    }

                    try {
                        var10000.setBytes(var10001, var9);
                    } catch (SQLException ex) {  // TODO
                        throw new RuntimeException(ex);
                    }
                });
                return;
            case PostgreSQL:
                this.a(var1, i -> {
                    PGobject var2x = new PGobject();
                    var2x.setType("json");
                    try {
                        var2x.setValue(element == null ? null : AurikaGson.toString(element));
                        PreparedNamedSetterStatement.this.getStatement().setObject(i, var2x);
                    } catch (SQLException ex) {  // TODO
                        throw new RuntimeException(ex);
                    }
                });
                return;
            default:
                String var10001 = var1;
                String var10002;
                if (element != null) {
                    String var3 = var1;
                    var1 = AurikaGson.toString(element);
                    var10001 = var3;
                    var10002 = var1;
                } else {
                    var10002 = null;
                }

                this.setString(var10001, var10002);
        }
    }

    public void setFloat(@NotNull String key, float value) {
        Validate.Arg.notNull(key, "");
        this.a(key, (i) -> {
            try {
                PreparedNamedSetterStatement.this.getStatement().setFloat(i, value);
            } catch (SQLException ex) {  // TODO
                throw new RuntimeException(ex);
            }
        });
    }

    public void setLong(@NotNull String key, long value) {
        Validate.Arg.notNull(key, "");
        this.a(key, (i) -> {
            try {
                PreparedNamedSetterStatement.this.getStatement().setLong(i, value);
            } catch (SQLException ex) {  // TODO
                throw new RuntimeException(ex);
            }
        });
    }

    public void setBoolean(@NotNull String key, boolean value) {
        Validate.Arg.notNull(key, "");
        this.a(key, (i) -> {
            try {
                PreparedNamedSetterStatement.this.getStatement().setBoolean(i, value);
            } catch (SQLException ex) {  // TODO
                throw new RuntimeException(ex);
            }
        });
    }

    public void setDouble(@NotNull String key, double value) {
        Validate.Arg.notNull(key, "");
        this.a(key, (i) -> {
            try {
                PreparedNamedSetterStatement.this.getStatement().setDouble(i, value);
            } catch (SQLException ex) {  // TODO
                throw new RuntimeException(ex);
            }
        });
    }

    public void setUUID(@NotNull String key, @Nullable UUID value) {
        Validate.Arg.notNull(key, "");
        this.a(key, i -> {
            try {

                if (PreparedNamedSetterStatement.this.databaseType == DatabaseType.PostgreSQL) {
                    PreparedNamedSetterStatement.this.getStatement().setObject(i, value);
                } else {
                    PreparedNamedSetterStatement.this.getStatement().setBytes(i, value == null ? null : SQLDatabase.asBytes(value));
                }
            } catch (SQLException ex) {  // TODO
                throw new RuntimeException(ex);
            }
        });
    }
}
