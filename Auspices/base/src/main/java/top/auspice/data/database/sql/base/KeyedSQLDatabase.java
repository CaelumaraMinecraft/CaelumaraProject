package top.auspice.data.database.sql.base;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.base.KeyedAuspiceObject;
import top.auspice.data.database.DatabaseType;
import top.auspice.data.database.base.KeyedKingdomsDatabase;
import top.auspice.data.database.sql.connection.SQLConnectionProvider;
import top.auspice.data.handlers.abstraction.KeyedDataHandler;

import java.sql.Connection;
import java.util.Objects;

public final class KeyedSQLDatabase<K, T extends KeyedAuspiceObject<K>> extends SQLDatabase<T> implements KeyedKingdomsDatabase<K, T> {
    @NotNull
    private final KeyedDataHandler<K, T> a;
    private int b;

    public KeyedSQLDatabase(@NotNull DatabaseType var1, @NotNull String var2, @NotNull KeyedDataHandler<K, T> var3, @NotNull SQLConnectionProvider var4) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        Intrinsics.checkNotNullParameter(var3, "");
        Intrinsics.checkNotNullParameter(var4, "");
        super(var1, var2, var4);
        this.a = var3;
        this.b = 10;
    }

    @NotNull
    protected final KeyedDataHandler<K, T> getDataHandler() {
        return this.a;
    }

    @Nullable
    public final T load(@NotNull K var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        Objects.requireNonNull(var1);
        String var2 = "SELECT * FROM `" + this.getTable() + "` WHERE " + this.getDataHandler().getIdHandler().getWhereClause();
        SQLDatabase var3;
        var2 = SQLDatabase.access$handleQuery(var3 = (SQLDatabase)this, var2);

        try {
            AutoCloseable var4 = (AutoCloseable)SQLDatabase.access$getConnection(var3);
            Throwable var5 = null;
            boolean var18 = false;

            KeyedAuspiceObject var42;
            try {
                var18 = true;
                Connection var6;
                AutoCloseable var43 = (AutoCloseable)(var6 = (Connection)var4).prepareStatement(var2);
                Throwable var7 = null;
                boolean var26 = false;

                KeyedKingdomsObject var45;
                try {
                    var26 = true;
                    PreparedStatement var8 = (PreparedStatement)var43;
                    this.getDataHandler().getIdHandler().setSQL((SimplePreparedStatement)(new RawSimplePreparedStatement(((SQLDatabase)this).getDatabaseType(), var8)), var1);
                    AutoCloseable var44 = (AutoCloseable)var8.executeQuery();
                    Throwable var9 = null;
                    boolean var34 = false;

                    KeyedKingdomsObject var10000;
                    label182: {
                        label181: {
                            KeyedKingdomsObject var47;
                            try {
                                var34 = true;
                                ResultSet var10;
                                if (!(var10 = (ResultSet)var44).next()) {
                                    var34 = false;
                                    break label181;
                                }

                                DatabaseType var10002 = ((SQLDatabase)this).getDatabaseType();
                                String var10003 = this.getTable();
                                DatabaseType var10009 = ((SQLDatabase)this).getDatabaseType();
                                Intrinsics.checkNotNull(var10);
                                SQLDataGetterProvider var46 = new SQLDataGetterProvider(var10002, var10003, (String)null, false, false, new SimpleResultSetQuery(var10009, var10));
                                var47 = (KeyedKingdomsObject)this.getDataHandler().load((SectionableDataGetter)var46, var1);
                                var34 = false;
                            } catch (Throwable var35) {
                                var9 = var35;
                                throw var35;
                            } finally {
                                if (var34) {
                                    AutoCloseableKt.closeFinally(var44, var9);
                                }
                            }

                            AutoCloseableKt.closeFinally(var44, (Throwable)null);
                            var10000 = var47;
                            break label182;
                        }

                        AutoCloseableKt.closeFinally(var44, (Throwable)null);
                        var10000 = null;
                    }

                    var45 = var10000;
                    var26 = false;
                } catch (Throwable var37) {
                    var7 = var37;
                    throw var37;
                } finally {
                    if (var26) {
                        AutoCloseableKt.closeFinally(var43, var7);
                    }
                }

                AutoCloseableKt.closeFinally(var43, (Throwable)null);
                var42 = var45;
                var18 = false;
            } catch (Throwable var39) {
                var5 = var39;
                throw var39;
            } finally {
                if (var18) {
                    AutoCloseableKt.closeFinally(var4, var5);
                }
            }

            AutoCloseableKt.closeFinally(var4, (Throwable)null);
            return var42;
        } catch (Throwable var41) {
            throw new RuntimeException("Error while handling data with query: " + var2 + " with " + SQLDatabase.access$getConnectionProvider(var3).getMetaString(), var41);
        }
    }

    public final void load(@NotNull Collection<K> var1, @NotNull Consumer<T> var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        if (!var1.isEmpty()) {
            int var3 = this.getDataHandler().getIdHandler().getColumns().length;
            String var4;
            String var5;
            Intrinsics.checkNotNull(var5 = var4 = Strings.repeat("(" + this.getDataHandler().getIdHandler().getInClause() + "),", var1.size()));
            int var49 = var4.length() - 1;
            String var10000 = var5.substring(0, var49);
            Intrinsics.checkNotNullExpressionValue(var10000, "");
            var4 = var10000;
            var5 = "SELECT * FROM `" + this.getTable() + "` WHERE (" + this.getDataHandler().getIdHandler().getColumnsTuple() + ") IN(" + var4 + ')';
            SQLDatabase var50;
            var5 = SQLDatabase.access$handleQuery(var50 = (SQLDatabase)this, var5);

            try {
                AutoCloseable var6 = (AutoCloseable)SQLDatabase.access$getConnection(var50);
                Throwable var7 = null;
                boolean var21 = false;

                try {
                    var21 = true;
                    Connection var8;
                    AutoCloseable var51 = (AutoCloseable)(var8 = (Connection)var6).prepareStatement(var5);
                    Throwable var9 = null;
                    boolean var29 = false;

                    try {
                        var29 = true;
                        PreparedStatement var10 = (PreparedStatement)var51;
                        Iterator var45 = ((Iterable)var1).iterator();
                        int var11 = 0;

                        while(var45.hasNext()) {
                            int var12 = var11++;
                            Object var13 = var45.next();
                            this.getDataHandler().getIdHandler().setSQL((SimplePreparedStatement)(new RawSimplePreparedStatement(var12 * var3 + 1, ((SQLDatabase)this).getDatabaseType(), var10)), var13);
                        }

                        AutoCloseable var46 = (AutoCloseable)var10.executeQuery();
                        Throwable var53 = null;
                        boolean var37 = false;

                        Unit var56;
                        try {
                            var37 = true;
                            ResultSet var54 = (ResultSet)var46;

                            while(true) {
                                if (!var54.next()) {
                                    var56 = Unit.INSTANCE;
                                    var37 = false;
                                    break;
                                }

                                IdDataTypeHandler var55 = this.getDataHandler().getIdHandler();
                                DatabaseType var10003 = ((SQLDatabase)this).getDatabaseType();
                                Intrinsics.checkNotNull(var54);
                                Object var47 = var55.fromSQL(new SimpleResultSetQuery(var10003, var54));
                                SQLDataGetterProvider var52 = new SQLDataGetterProvider(((SQLDatabase)this).getDatabaseType(), this.getTable(), (String)null, false, false, new SimpleResultSetQuery(((SQLDatabase)this).getDatabaseType(), var54));
                                KeyedKingdomsObject var48 = (KeyedKingdomsObject)this.getDataHandler().load((SectionableDataGetter)var52, var47);
                                var2.accept(var48);
                            }
                        } catch (Throwable var38) {
                            var53 = var38;
                            throw var38;
                        } finally {
                            if (var37) {
                                AutoCloseableKt.closeFinally(var46, var53);
                            }
                        }

                        AutoCloseableKt.closeFinally(var46, (Throwable)null);
                        var56 = Unit.INSTANCE;
                        var29 = false;
                    } catch (Throwable var40) {
                        var9 = var40;
                        throw var40;
                    } finally {
                        if (var29) {
                            AutoCloseableKt.closeFinally(var51, var9);
                        }
                    }

                    AutoCloseableKt.closeFinally(var51, (Throwable)null);
                    var21 = false;
                } catch (Throwable var42) {
                    var7 = var42;
                    throw var42;
                } finally {
                    if (var21) {
                        AutoCloseableKt.closeFinally(var6, var7);
                    }
                }

                AutoCloseableKt.closeFinally(var6, (Throwable)null);
            } catch (Throwable var44) {
                throw new RuntimeException("Error while handling data with query: " + var5 + " with " + SQLDatabase.access$getConnectionProvider(var50).getMetaString(), var44);
            }
        }
    }

    public final void delete(@NotNull K var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        String var2 = "DELETE FROM `" + this.getTable() + "` WHERE " + this.getDataHandler().getIdHandler().getWhereClause();
        SQLDatabase var3;
        var2 = SQLDatabase.access$handleQuery(var3 = (SQLDatabase)this, var2);

        try {
            AutoCloseable var4 = (AutoCloseable)SQLDatabase.access$getConnection(var3);
            Throwable var5 = null;
            boolean var14 = false;

            try {
                var14 = true;
                Connection var6;
                AutoCloseable var26 = (AutoCloseable)(var6 = (Connection)var4).prepareStatement(var2);
                Throwable var7 = null;
                boolean var20 = false;

                try {
                    var20 = true;
                    PreparedStatement var8 = (PreparedStatement)var26;
                    this.getDataHandler().getIdHandler().setSQL((SimplePreparedStatement)(new RawSimplePreparedStatement(((SQLDatabase)this).getDatabaseType(), var8)), var1);
                    var8.execute();
                    var20 = false;
                } catch (Throwable var21) {
                    var7 = var21;
                    throw var21;
                } finally {
                    if (var20) {
                        AutoCloseableKt.closeFinally(var26, var7);
                    }
                }

                AutoCloseableKt.closeFinally(var26, (Throwable)null);
                var14 = false;
            } catch (Throwable var23) {
                var5 = var23;
                throw var23;
            } finally {
                if (var14) {
                    AutoCloseableKt.closeFinally(var4, var5);
                }
            }

            AutoCloseableKt.closeFinally(var4, (Throwable)null);
        } catch (Throwable var25) {
            throw new RuntimeException("Error while handling data with query: " + var2 + " with " + SQLDatabase.access$getConnectionProvider(var3).getMetaString(), var25);
        }
    }

    public final boolean hasData(@NotNull K var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        String var2 = "SELECT 1 FROM `" + this.getTable() + "` WHERE " + this.getDataHandler().getIdHandler().getWhereClause();
        SQLDatabase var3;
        var2 = SQLDatabase.access$handleQuery(var3 = (SQLDatabase)this, var2);

        try {
            AutoCloseable var4 = (AutoCloseable)SQLDatabase.access$getConnection(var3);
            Throwable var5 = null;
            boolean var14 = false;

            boolean var27;
            try {
                var14 = true;
                Connection var6;
                AutoCloseable var26 = (AutoCloseable)(var6 = (Connection)var4).prepareStatement(var2);
                Throwable var7 = null;
                boolean var20 = false;

                try {
                    var20 = true;
                    PreparedStatement var8 = (PreparedStatement)var26;
                    this.getDataHandler().getIdHandler().setSQL((SimplePreparedStatement)(new RawSimplePreparedStatement(((SQLDatabase)this).getDatabaseType(), var8)), var1);
                    var27 = var8.executeQuery().next();
                    var20 = false;
                } catch (Throwable var21) {
                    var7 = var21;
                    throw var21;
                } finally {
                    if (var20) {
                        AutoCloseableKt.closeFinally(var26, var7);
                    }
                }

                AutoCloseableKt.closeFinally(var26, (Throwable)null);
                var14 = false;
            } catch (Throwable var23) {
                var5 = var23;
                throw var23;
            } finally {
                if (var14) {
                    AutoCloseableKt.closeFinally(var4, var5);
                }
            }

            AutoCloseableKt.closeFinally(var4, (Throwable)null);
            return var27;
        } catch (Throwable var25) {
            throw new RuntimeException("Error while handling data with query: " + var2 + " with " + SQLDatabase.access$getConnectionProvider(var3).getMetaString(), var25);
        }
    }

    @NotNull
    public final Collection<K> getAllDataKeys() {
        List var1 = (List)(new ArrayList(this.b));
        String var2 = "SELECT " + this.getDataHandler().getIdHandler().getColumnsTuple() + " FROM `" + this.getTable() + '`';
        SQLDatabase var3;
        var2 = SQLDatabase.access$handleQuery(var3 = (SQLDatabase)this, var2);

        try {
            AutoCloseable var4 = (AutoCloseable)SQLDatabase.access$getConnection(var3);
            Throwable var5 = null;
            boolean var19 = false;

            try {
                var19 = true;
                Connection var6;
                AutoCloseable var43 = (AutoCloseable)(var6 = (Connection)var4).createStatement();
                Throwable var7 = null;
                boolean var27 = false;

                try {
                    var27 = true;
                    Statement var8;
                    AutoCloseable var44 = (AutoCloseable)(var8 = (Statement)var43).executeQuery(var2);
                    Throwable var9 = null;
                    boolean var35 = false;

                    try {
                        var35 = true;
                        ResultSet var10 = (ResultSet)var44;

                        while(var10.next()) {
                            Object var11 = this.getDataHandler().getIdHandler().fromSQL(new SimpleResultSetQuery(((SQLDatabase)this).getDatabaseType(), var10));
                            var1.add(var11);
                        }

                        Unit var10000 = Unit.INSTANCE;
                        var35 = false;
                    } catch (Throwable var36) {
                        var9 = var36;
                        throw var36;
                    } finally {
                        if (var35) {
                            AutoCloseableKt.closeFinally(var44, var9);
                        }
                    }

                    AutoCloseableKt.closeFinally(var44, (Throwable)null);
                    var27 = false;
                } catch (Throwable var38) {
                    var7 = var38;
                    throw var38;
                } finally {
                    if (var27) {
                        AutoCloseableKt.closeFinally(var43, var7);
                    }
                }

                AutoCloseableKt.closeFinally(var43, (Throwable)null);
                var19 = false;
            } catch (Throwable var40) {
                var5 = var40;
                throw var40;
            } finally {
                if (var19) {
                    AutoCloseableKt.closeFinally(var4, var5);
                }
            }

            AutoCloseableKt.closeFinally(var4, (Throwable)null);
        } catch (Throwable var42) {
            throw new RuntimeException("Error while handling data with query: " + var2 + " with " + SQLDatabase.access$getConnectionProvider(var3).getMetaString(), var42);
        }

        this.b = (int)Math.max((double)this.b, (double)var1.size());
        return (Collection)var1;
    }

    @NotNull
    public final Collection<T> loadAllData(@Nullable Predicate<K> var1) {
        List var2 = (List)(new ArrayList(this.b));
        String var3 = "SELECT * FROM `" + this.getTable() + '`';
        SQLDatabase var4;
        var3 = SQLDatabase.access$handleQuery(var4 = (SQLDatabase)this, var3);

        try {
            AutoCloseable var5 = (AutoCloseable)SQLDatabase.access$getConnection(var4);
            Throwable var6 = null;
            boolean var22 = false;

            try {
                var22 = true;
                Connection var7;
                AutoCloseable var49 = (AutoCloseable)(var7 = (Connection)var5).prepareStatement(var3);
                Throwable var8 = null;
                boolean var31 = false;

                try {
                    var31 = true;
                    PreparedStatement var9;
                    AutoCloseable var50 = (AutoCloseable)(var9 = (PreparedStatement)var49).executeQuery();
                    Throwable var10 = null;
                    boolean var40 = false;

                    Unit var10000;
                    try {
                        var40 = true;
                        ResultSet var11 = (ResultSet)var50;

                        label225:
                        while(true) {
                            SimpleResultSetQuery var12;
                            Object var13;
                            do {
                                if (!var11.next()) {
                                    var10000 = Unit.INSTANCE;
                                    var40 = false;
                                    break label225;
                                }

                                DatabaseType var10002 = ((SQLDatabase)this).getDatabaseType();
                                Intrinsics.checkNotNull(var11);
                                var12 = new SimpleResultSetQuery(var10002, var11);
                                var13 = this.getDataHandler().getIdHandler().fromSQL(var12);
                            } while(var1 != null && !var1.test(var13));

                            try {
                                SQLDataGetterProvider var51 = new SQLDataGetterProvider(((SQLDatabase)this).getDatabaseType(), this.getTable(), (String)null, false, false, var12);
                                var2.add(this.getDataHandler().load((SectionableDataGetter)var51, var13));
                            } catch (Throwable var41) {
                                KLogger.error("Error while loading '" + var13 + "' of type " + this.getDataHandler().getClass().getSimpleName() + " in table '" + this.getTable() + "' (Skipping):");
                                var41.printStackTrace();
                            }
                        }
                    } catch (Throwable var42) {
                        var10 = var42;
                        throw var42;
                    } finally {
                        if (var40) {
                            AutoCloseableKt.closeFinally(var50, var10);
                        }
                    }

                    AutoCloseableKt.closeFinally(var50, (Throwable)null);
                    var10000 = Unit.INSTANCE;
                    var31 = false;
                } catch (Throwable var44) {
                    var8 = var44;
                    throw var44;
                } finally {
                    if (var31) {
                        AutoCloseableKt.closeFinally(var49, var8);
                    }
                }

                AutoCloseableKt.closeFinally(var49, (Throwable)null);
                var22 = false;
            } catch (Throwable var46) {
                var6 = var46;
                throw var46;
            } finally {
                if (var22) {
                    AutoCloseableKt.closeFinally(var5, var6);
                }
            }

            AutoCloseableKt.closeFinally(var5, (Throwable)null);
        } catch (Throwable var48) {
            throw new RuntimeException("Error while handling data with query: " + var3 + " with " + SQLDatabase.access$getConnectionProvider(var4).getMetaString(), var48);
        }

        this.b = (int)Math.max((double)this.b, (double)var2.size());
        return (Collection)var2;
    }

    public final void save(@NotNull Collection<? extends T> var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        if (!var1.isEmpty()) {
            PreparedNamedSetterStatement var2 = new PreparedNamedSetterStatement(((SQLDatabase)this).getDatabaseType(), this.getDataHandler().getSqlProperties().getAssociateNamedData());

            try {
                AutoCloseable var3 = (AutoCloseable)this.getConnection();
                Throwable var4 = null;
                boolean var11 = false;

                try {
                    var11 = true;
                    Connection var5;
                    (var5 = (Connection)var3).setAutoCommit(false);
                    Iterator var15 = var1.iterator();

                    while(true) {
                        if (!var15.hasNext()) {
                            var2.execute();
                            var5.commit();
                            var5.setAutoCommit(true);
                            Unit var16 = Unit.INSTANCE;
                            var11 = false;
                            break;
                        }

                        KeyedKingdomsObject var6 = (KeyedKingdomsObject)var15.next();
                        SQLDataSetterProvider var7 = new SQLDataSetterProvider(((SQLDatabase)this).getDatabaseType(), this.getTable(), (String)null, false, false, var2);
                        IdDataTypeHandler var10000 = this.getDataHandler().getIdHandler();
                        SimplePreparedStatement var10001 = (SimplePreparedStatement)var2;
                        Object var10002 = var6.getKey();
                        Intrinsics.checkNotNullExpressionValue(var10002, "");
                        var10000.setSQL(var10001, var10002);
                        this.getDataHandler().save((SectionableDataSetter)var7, var6);
                        var2.buildStatement(this.getTable(), var5);
                        var2.addBatch();
                    }
                } catch (Throwable var12) {
                    var4 = var12;
                    throw var12;
                } finally {
                    if (var11) {
                        AutoCloseableKt.closeFinally(var3, var4);
                    }
                }

                AutoCloseableKt.closeFinally(var3, (Throwable)null);
            } catch (SQLException var14) {
                throw new RuntimeException("Error while trying to save batch data with " + this.getConnectionProvider().getMetaString(), (Throwable)var14);
            }
        }
    }
}
