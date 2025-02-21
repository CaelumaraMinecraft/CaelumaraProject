package net.aurika.ecliptor.database.sql.base;

import kotlin.text.StringsKt;
import net.aurika.ecliptor.api.DataObject;
import net.aurika.ecliptor.api.Keyed;
import net.aurika.ecliptor.database.DatabaseType;
import net.aurika.ecliptor.database.base.Database;
import net.aurika.ecliptor.database.dataprovider.IdDataTypeHandler;
import net.aurika.ecliptor.database.sql.SQLDataSetterProvider;
import net.aurika.ecliptor.database.sql.connection.SQLConnectionProvider;
import net.aurika.ecliptor.database.sql.statements.SQLStatement;
import net.aurika.ecliptor.database.sql.statements.setters.PreparedNamedSetterStatement;
import net.aurika.ecliptor.database.sql.statements.setters.SimplePreparedStatement;
import net.aurika.ecliptor.handler.DataHandler;
import net.aurika.ecliptor.handler.KeyedDataHandler;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

public abstract class SQLDatabase<T extends DataObject> implements Database<T> {

    private final @NotNull DatabaseType databaseType;
    private final @NotNull String table;
    private final @NotNull SQLConnectionProvider connectionProvider;

    public SQLDatabase(@NotNull DatabaseType databaseType, @NotNull String table, @NotNull SQLConnectionProvider connectionProvider) {
        Objects.requireNonNull(databaseType, "databaseType");
        Objects.requireNonNull(table, "table");
        Objects.requireNonNull(connectionProvider, "connectionProvider");
        this.databaseType = databaseType;
        this.table = table;
        this.connectionProvider = connectionProvider;
    }

    protected final @NotNull String getTable() {
        return this.table;
    }

    protected final @NotNull SQLConnectionProvider getConnectionProvider() {
        return this.connectionProvider;
    }

    protected final @NotNull String handleQuery(@NotNull String query) {
        Validate.Arg.notNull(query, "query");
        char var2;
        return (var2 = this.databaseType.systemIdentifierEscapeChar()) != '`' ? StringsKt.replace(query, '`', var2, false) : query;
    }

    public @NotNull DatabaseType getDatabaseType() {
        return this.databaseType;
    }

    protected final Connection getConnection() {
        return this.connectionProvider.getConnection();
    }

    protected abstract DataHandler<T> getDataHandler();

    public void save(@NotNull T data) {
        Objects.requireNonNull(data, "data");
        DataHandler<T> dataHandler = this.getDataHandler();
        PreparedNamedSetterStatement var3 = new PreparedNamedSetterStatement(this.databaseType, dataHandler.getSqlProperties().getAssociateNamedData());

        try (Connection connection = this.getConnection()) {
            try (connection) {
                SQLDataSetterProvider var7 = new SQLDataSetterProvider(this.databaseType, this.table, null, false, false, var3);
                if (dataHandler instanceof KeyedDataHandler) {
                    IdDataTypeHandler var10000 = ((KeyedDataHandler) dataHandler).getIdHandler();
                    SimplePreparedStatement var10001 = var3;
                    Object key = ((Keyed) data).dataKey();
                    Objects.requireNonNull(key, "dataKey");
                    var10000.setSQL(var10001, key);
                } else {
                    var7.setBoolean("id", true);
                }

                dataHandler.save(var7, data);
                var3.buildStatement(this.table, connection);
                var3.execute();
            }
        } catch (Throwable var14) {
            throw new RuntimeException("Error while saving data " + data + " (" + data.getClass() + ')', var14);
        }
    }

    public void deleteAllData() {
        this.executeStatement("DROP TABLE `" + this.table + '`');
    }

    protected final <A> A prepareStatement(@NotNull String var1, @NotNull Function<? super PreparedStatement, ? extends A> var2) {
        Validate.Arg.notNull(var1, "");
        Validate.Arg.notNull(var2, "");
        var1 = this.handleQuery(var1);

        try (Connection connection = this.getConnection()) {
            try (PreparedStatement var25 = connection.prepareStatement(var1)) {
                return var2.apply(var25);
            }
        } catch (Throwable var23) {
            throw new RuntimeException("Error while handling data with query: " + var1 + " with " + this.getConnectionProvider().getMetaString(), var23);
        }
    }

    protected final <A> A executeStatement(@NotNull String var1, @NotNull Function<? super ResultSet, ? extends A> var2) {
        Validate.Arg.notNull(var1, "");
        Validate.Arg.notNull(var2, "");
        var1 = this.handleQuery(var1);

        try (Connection connection = this.getConnection()) {
            A var40;
            try (Statement var41 = connection.createStatement()) {
                try (ResultSet var42 = var41.executeQuery(var1)) {
                    var40 = var2.apply(var42);
                }
            }
            return var40;
        } catch (Throwable var39) {
            throw new RuntimeException("Error while handling data with query: " + var1 + " with " + this.getConnectionProvider().getMetaString(), var39);
        }
    }

    public final void executeStatement(@NotNull SQLStatement var1) {
        Validate.Arg.notNull(var1, "");
        this.executeStatement(this.databaseType.createStatement(var1, this.table));
    }

    public final void executeStatement(@NotNull String query) {
        Validate.Arg.notNull(query, "query");
        query = this.handleQuery(query);

        try (Connection connection = this.getConnection()) {
            connection.createStatement().execute(query);
        } catch (Throwable var23) {
            throw new RuntimeException("Error while handling data with query: " + query + " with " + this.connectionProvider.getMetaString(), var23);
        }
    }

    public void close() {
        connectionProvider.close();
    }

    public static byte @NotNull [] asBytes(@NotNull UUID uuid) {
        Validate.Arg.notNull(uuid, "uuid");
        ByteBuffer buf = ByteBuffer.wrap(new byte[16]);
        buf.putLong(uuid.getMostSignificantBits());
        buf.putLong(uuid.getLeastSignificantBits());
        return buf.array();
    }

    public static @NotNull UUID asUUID(byte @NotNull [] bytes) {
        Validate.Arg.notNull(bytes, "bytes");
        ByteBuffer buf = ByteBuffer.wrap(bytes);
        long mostSigBits = buf.getLong();
        long leastSigBits = buf.getLong();
        return new UUID(mostSigBits, leastSigBits);
    }
}
