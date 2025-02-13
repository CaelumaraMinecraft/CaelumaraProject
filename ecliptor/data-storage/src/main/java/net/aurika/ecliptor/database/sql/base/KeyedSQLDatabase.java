package net.aurika.ecliptor.database.sql.base;

import kotlin.jvm.internal.Intrinsics;
import net.aurika.ecliptor.database.DatabaseType;
import net.aurika.ecliptor.database.base.KeyedDatabase;
import net.aurika.ecliptor.database.dataprovider.IdDataTypeHandler;
import net.aurika.ecliptor.database.sql.SQLDataGetterProvider;
import net.aurika.ecliptor.database.sql.SQLDataSetterProvider;
import net.aurika.ecliptor.database.sql.connection.SQLConnectionProvider;
import net.aurika.ecliptor.database.sql.statements.getters.SimpleResultSetQuery;
import net.aurika.ecliptor.database.sql.statements.setters.PreparedNamedSetterStatement;
import net.aurika.ecliptor.database.sql.statements.setters.RawSimplePreparedStatement;
import net.aurika.ecliptor.handler.KeyedDataHandler;
import net.aurika.ecliptor.api.KeyedDataObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import top.auspice.utils.logging.AuspiceLogger;
import top.auspice.utils.string.Strings;
import top.auspice.utils.unsafe.AutoCloseableUtils;

import java.sql.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class KeyedSQLDatabase<K, T extends KeyedDataObject.Impl<K>> extends SQLDatabase<T> implements KeyedDatabase<K, T> {

    private final @NotNull KeyedDataHandler<K, T> a;
    private int b;

    public KeyedSQLDatabase(@NotNull DatabaseType databaseType, @NotNull String table, @NotNull KeyedDataHandler<K, T> dataHandler, @NotNull SQLConnectionProvider connectionProvider) {
        super(databaseType, table, connectionProvider);
        Objects.requireNonNull(dataHandler, "dataHandler");
        this.a = dataHandler;
        this.b = 10;
    }

    protected @NotNull KeyedDataHandler<K, T> getDataHandler() {
        return this.a;
    }

    public @Nullable T load(@NotNull K key) {
        Intrinsics.checkNotNullParameter(key, "");
        Objects.requireNonNull(key);
        String var2 = "SELECT * FROM `" + this.getTable() + "` WHERE " + this.getDataHandler().getIdHandler().getWhereClause();
        SQLDatabase<T> var3 = this;
        var2 = var3.handleQuery(var2);

        try {
            Connection connection = var3.getConnection();
            Throwable var5 = null;
            boolean var18 = false;

            T var42;
            try {
                var18 = true;
                PreparedStatement var43 = connection.prepareStatement(var2);
                Throwable var7 = null;
                boolean var26 = false;

                T var45;
                try {
                    var26 = true;
                    this.getDataHandler().getIdHandler().setSQL(new RawSimplePreparedStatement(this.getDatabaseType(), var43), key);
                    ResultSet var44 = var43.executeQuery();
                    Throwable var9 = null;
                    boolean var34 = false;

                    T var10000;
                    label182:
                    {
                        label181:
                        {
                            T var47;
                            try {
                                var34 = true;
                                if (!var44.next()) {
                                    var34 = false;
                                    break label181;
                                }

                                DatabaseType var10002 = this.getDatabaseType();
                                String var10003 = this.getTable();
                                DatabaseType var10009 = this.getDatabaseType();
                                Intrinsics.checkNotNull(var44);
                                SQLDataGetterProvider var46 = new SQLDataGetterProvider(var10002, var10003, null, false, false, new SimpleResultSetQuery(var10009, var44));
                                var47 = this.getDataHandler().load(var46, key);
                                var34 = false;
                            } catch (Throwable var35) {
                                var9 = var35;
                                throw var35;
                            } finally {
                                if (var34) {
                                    AutoCloseableUtils.closeFinally(var44, var9);
                                }
                            }

                            AutoCloseableUtils.closeFinally(var44, null);
                            var10000 = var47;
                            break label182;
                        }

                        AutoCloseableUtils.closeFinally(var44, null);
                        var10000 = null;
                    }

                    var45 = var10000;
                    var26 = false;
                } catch (Throwable var37) {
                    var7 = var37;
                    throw var37;
                } finally {
                    if (var26) {
                        AutoCloseableUtils.closeFinally(var43, var7);
                    }
                }

                AutoCloseableUtils.closeFinally(var43, null);
                var42 = var45;
                var18 = false;
            } catch (Throwable var39) {
                var5 = var39;
                throw var39;
            } finally {
                if (var18) {
                    AutoCloseableUtils.closeFinally(connection, var5);
                }
            }

            AutoCloseableUtils.closeFinally(connection, null);
            return var42;
        } catch (Throwable var41) {
            throw new RuntimeException("Error while handling data with query: " + var2 + " with " + var3.getConnectionProvider().getMetaString(), var41);
        }
    }

    public void load(@NotNull Collection<K> keys, @NotNull Consumer<T> dataConsumer) {
        Intrinsics.checkNotNullParameter(keys, "");
        Intrinsics.checkNotNullParameter(dataConsumer, "");
        if (!keys.isEmpty()) {
            int var3 = this.getDataHandler().getIdHandler().getColumns().length;
            String var4;
            String var5;
            Intrinsics.checkNotNull(var5 = var4 = Strings.repeat("(" + this.getDataHandler().getIdHandler().getInClause() + "),", keys.size()));
            int var49 = var4.length() - 1;
            String var10000 = var5.substring(0, var49);
            Intrinsics.checkNotNullExpressionValue(var10000, "");
            var4 = var10000;
            var5 = "SELECT * FROM `" + this.getTable() + "` WHERE (" + this.getDataHandler().getIdHandler().getColumnsTuple() + ") IN(" + var4 + ')';
            SQLDatabase<T> var50 = this;
            var5 = var50.handleQuery(var5);

            try {
                Connection var6 = var50.getConnection();
                Throwable var7 = null;
                boolean var21 = false;

                try {
                    var21 = true;
                    PreparedStatement var51 = var6.prepareStatement(var5);
                    Throwable var9 = null;
                    boolean var29 = false;

                    try {
                        var29 = true;
                        Iterator<K> var45 = keys.iterator();
                        int var11 = 0;

                        while (var45.hasNext()) {
                            int var12 = var11++;
                            K k = var45.next();
                            this.getDataHandler().getIdHandler().setSQL(new RawSimplePreparedStatement(var12 * var3 + 1, this.getDatabaseType(), var51), k);
                        }

                        ResultSet var46 = var51.executeQuery();
                        Throwable var53 = null;
                        boolean var37 = false;

                        try {
                            var37 = true;

                            while (true) {
                                if (!var46.next()) {
                                    var37 = false;
                                    break;
                                }

                                IdDataTypeHandler<K> var55 = this.getDataHandler().getIdHandler();
                                DatabaseType var10003 = this.getDatabaseType();
                                Intrinsics.checkNotNull(var46);
                                K var47 = var55.fromSQL(new SimpleResultSetQuery(var10003, var46));
                                SQLDataGetterProvider var52 = new SQLDataGetterProvider(this.getDatabaseType(), this.getTable(), null, false, false, new SimpleResultSetQuery(this.getDatabaseType(), var46));
                                T var48 = this.getDataHandler().load(var52, var47);
                                dataConsumer.accept(var48);
                            }
                        } catch (Throwable var38) {
                            var53 = var38;
                            throw var38;
                        } finally {
                            if (var37) {
                                AutoCloseableUtils.closeFinally(var46, var53);
                            }
                        }

                        AutoCloseableUtils.closeFinally(var46, null);
                        var29 = false;
                    } catch (Throwable var40) {
                        var9 = var40;
                        throw var40;
                    } finally {
                        if (var29) {
                            AutoCloseableUtils.closeFinally(var51, var9);
                        }
                    }

                    AutoCloseableUtils.closeFinally(var51, null);
                    var21 = false;
                } catch (Throwable var42) {
                    var7 = var42;
                    throw var42;
                } finally {
                    if (var21) {
                        AutoCloseableUtils.closeFinally(var6, var7);
                    }
                }

                AutoCloseableUtils.closeFinally(var6, null);
            } catch (Throwable var44) {
                throw new RuntimeException("Error while handling data with query: " + var5 + " with " + var50.getConnectionProvider().getMetaString(), var44);
            }
        }
    }

    public void delete(@NotNull K key) {
        Intrinsics.checkNotNullParameter(key, "");
        String var2 = "DELETE FROM `" + this.getTable() + "` WHERE " + this.getDataHandler().getIdHandler().getWhereClause();
        SQLDatabase<T> var3 = this;
        var2 = var3.handleQuery(var2);

        try {
            Connection var4 = var3.getConnection();
            Throwable var5 = null;
            boolean var14 = false;

            try {
                var14 = true;
                PreparedStatement var26 = var4.prepareStatement(var2);
                Throwable var7 = null;
                boolean var20 = false;

                try {
                    var20 = true;
                    this.getDataHandler().getIdHandler().setSQL(new RawSimplePreparedStatement(this.getDatabaseType(), var26), key);
                    var26.execute();
                    var20 = false;
                } catch (Throwable var21) {
                    var7 = var21;
                    throw var21;
                } finally {
                    if (var20) {
                        AutoCloseableUtils.closeFinally(var26, var7);
                    }
                }

                AutoCloseableUtils.closeFinally(var26, null);
                var14 = false;
            } catch (Throwable var23) {
                var5 = var23;
                throw var23;
            } finally {
                if (var14) {
                    AutoCloseableUtils.closeFinally(var4, var5);
                }
            }

            AutoCloseableUtils.closeFinally(var4, null);
        } catch (Throwable var25) {
            throw new RuntimeException("Error while handling data with query: " + var2 + " with " + var3.getConnectionProvider().getMetaString(), var25);
        }
    }

    public boolean hasData(@NotNull K key) {
        Intrinsics.checkNotNullParameter(key, "");
        String var2 = "SELECT 1 FROM `" + this.getTable() + "` WHERE " + this.getDataHandler().getIdHandler().getWhereClause();
        SQLDatabase<T> var3 = this;
        var2 = var3.handleQuery(var2);

        try {
            Connection var4 = var3.getConnection();
            Throwable var5 = null;
            boolean var14 = false;

            boolean var27;
            try {
                var14 = true;
                PreparedStatement var26 = var4.prepareStatement(var2);
                Throwable var7 = null;
                boolean var20 = false;

                try {
                    var20 = true;
                    this.getDataHandler().getIdHandler().setSQL(new RawSimplePreparedStatement(this.getDatabaseType(), var26), key);
                    var27 = var26.executeQuery().next();
                    var20 = false;
                } catch (Throwable var21) {
                    var7 = var21;
                    throw var21;
                } finally {
                    if (var20) {
                        AutoCloseableUtils.closeFinally(var26, var7);
                    }
                }

                AutoCloseableUtils.closeFinally(var26, null);
                var14 = false;
            } catch (Throwable var23) {
                var5 = var23;
                throw var23;
            } finally {
                if (var14) {
                    AutoCloseableUtils.closeFinally(var4, var5);
                }
            }

            AutoCloseableUtils.closeFinally(var4, null);
            return var27;
        } catch (Throwable var25) {
            throw new RuntimeException("Error while handling data with query: " + var2 + " with " + var3.getConnectionProvider().getMetaString(), var25);
        }
    }

    @NotNull
    public Collection<K> getAllDataKeys() {
        List<K> var1 = new ArrayList(this.b);
        String var2 = "SELECT " + this.getDataHandler().getIdHandler().getColumnsTuple() + " FROM `" + this.getTable() + '`';
        SQLDatabase<T> var3 = this;
        var2 = var3.handleQuery(var2);

        try {
            Connection var4 = var3.getConnection();
            Throwable var5 = null;
            boolean var19 = false;

            try {
                var19 = true;
                Statement var43 = var4.createStatement();
                Throwable var7 = null;
                boolean var27 = false;

                try {
                    var27 = true;
                    ResultSet var44 = var43.executeQuery(var2);
                    Throwable var9 = null;
                    boolean var35 = false;

                    try {
                        var35 = true;

                        while (var44.next()) {
                            K var11 = this.getDataHandler().getIdHandler().fromSQL(new SimpleResultSetQuery(this.getDatabaseType(), var44));
                            var1.add(var11);
                        }

                        var35 = false;
                    } catch (Throwable var36) {
                        var9 = var36;
                        throw var36;
                    } finally {
                        if (var35) {
                            AutoCloseableUtils.closeFinally(var44, var9);
                        }
                    }

                    AutoCloseableUtils.closeFinally(var44, null);
                    var27 = false;
                } catch (Throwable var38) {
                    var7 = var38;
                    throw var38;
                } finally {
                    if (var27) {
                        AutoCloseableUtils.closeFinally(var43, var7);
                    }
                }

                AutoCloseableUtils.closeFinally(var43, null);
                var19 = false;
            } catch (Throwable var40) {
                var5 = var40;
                throw var40;
            } finally {
                if (var19) {
                    AutoCloseableUtils.closeFinally(var4, var5);
                }
            }

            AutoCloseableUtils.closeFinally(var4, null);
        } catch (Throwable var42) {
            throw new RuntimeException("Error while handling data with query: " + var2 + " with " + var3.getConnectionProvider().getMetaString(), var42);
        }

        this.b = (int) Math.max(this.b, (double) var1.size());
        return var1;
    }

    @NotNull
    public Collection<T> loadAllData(@Nullable Predicate<K> keyFilter) {
        List<T> var2 = new ArrayList<>(this.b);

        String var3 = "SELECT * FROM `" + this.getTable() + '`';
        SQLDatabase<T> var4 = this;
        var3 = var4.handleQuery(var3);

        try {
            Connection var5 = var4.getConnection();
            Throwable var6 = null;
            boolean var22 = false;

            try {
                var22 = true;
                PreparedStatement var49 = var5.prepareStatement(var3);
                Throwable var8 = null;
                boolean var31 = false;

                try {
                    var31 = true;
                    ResultSet var50 = var49.executeQuery();
                    Throwable var10 = null;
                    boolean var40 = false;

                    try {
                        var40 = true;

                        label225:
                        while (true) {
                            SimpleResultSetQuery var12;
                            K var13;
                            do {
                                if (!var50.next()) {
                                    var40 = false;
                                    break label225;
                                }

                                DatabaseType var10002 = this.getDatabaseType();
                                Intrinsics.checkNotNull(var50);
                                var12 = new SimpleResultSetQuery(var10002, var50);
                                var13 = this.getDataHandler().getIdHandler().fromSQL(var12);
                            } while (keyFilter != null && !keyFilter.test(var13));

                            try {
                                SQLDataGetterProvider var51 = new SQLDataGetterProvider(this.getDatabaseType(), this.getTable(), null, false, false, var12);
                                var2.add(this.getDataHandler().load(var51, var13));
                            } catch (Throwable var41) {
                                AuspiceLogger.error("Error while loading '" + var13 + "' of type " + this.getDataHandler().getClass().getSimpleName() + " in table '" + this.getTable() + "' (Skipping):");
                                var41.printStackTrace();
                            }
                        }
                    } catch (Throwable var42) {
                        var10 = var42;
                        throw var42;
                    } finally {
                        if (var40) {
                            AutoCloseableUtils.closeFinally(var50, var10);
                        }
                    }

                    AutoCloseableUtils.closeFinally(var50, null);
                    var31 = false;
                } catch (Throwable var44) {
                    var8 = var44;
                    throw var44;
                } finally {
                    if (var31) {
                        AutoCloseableUtils.closeFinally(var49, var8);
                    }
                }

                AutoCloseableUtils.closeFinally(var49, null);
                var22 = false;
            } catch (Throwable var46) {
                var6 = var46;
                throw var46;
            } finally {
                if (var22) {
                    AutoCloseableUtils.closeFinally(var5, var6);
                }
            }

            AutoCloseableUtils.closeFinally(var5, null);
        } catch (Throwable var48) {
            throw new RuntimeException("Error while handling data with query: " + var3 + " with " + var4.getConnectionProvider().getMetaString(), var48);
        }

        this.b = (int) Math.max(this.b, (double) var2.size());
        return var2;
    }

    public void save(@NotNull @Unmodifiable Collection<T> data) {
        Intrinsics.checkNotNullParameter(data, "");
        if (!data.isEmpty()) {
            PreparedNamedSetterStatement var2 = new PreparedNamedSetterStatement(this.getDatabaseType(), this.getDataHandler().getSqlProperties().getAssociateNamedData());

            try {
                Connection connection = this.getConnection();
                Throwable var4 = null;
                boolean var11 = false;

                try {
                    var11 = true;
                    connection.setAutoCommit(false);
                    Iterator<? extends T> var15 = data.iterator();

                    while (true) {
                        if (!var15.hasNext()) {
                            var2.execute();
                            connection.commit();
                            connection.setAutoCommit(true);
                            var11 = false;
                            break;
                        }

                        T var6 = var15.next();
                        SQLDataSetterProvider var7 = new SQLDataSetterProvider(this.getDatabaseType(), this.getTable(), null, false, false, var2);
                        IdDataTypeHandler<K> var10000 = this.getDataHandler().getIdHandler();
                        K var10002 = var6.getKey();
                        Intrinsics.checkNotNullExpressionValue(var10002, "");
                        var10000.setSQL(var2, var10002);
                        this.getDataHandler().save(var7, var6);
                        var2.buildStatement(this.getTable(), connection);
                        var2.addBatch();
                    }
                } catch (Throwable var12) {
                    var4 = var12;
                    throw var12;
                } finally {
                    if (var11) {
                        AutoCloseableUtils.closeFinally(connection, var4);
                    }
                }

                AutoCloseableUtils.closeFinally(connection, null);
            } catch (SQLException var14) {
                throw new RuntimeException("Error while trying to save batch data with " + this.getConnectionProvider().getMetaString(), var14);
            }
        }
    }
}
