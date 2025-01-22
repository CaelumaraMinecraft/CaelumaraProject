package top.auspice.data.database.sql.connection;

import org.checkerframework.dataflow.qual.Pure;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Executor;

public class NonClosableConnection implements Connection {

    private final @NotNull Connection connection;

    public NonClosableConnection(@NotNull Connection var1) {
        Objects.requireNonNull(var1, "");
        this.connection = var1;
    }

    public void abort(Executor var1) throws SQLException {
        this.connection.abort(var1);
    }

    public void clearWarnings() throws SQLException {
        this.connection.clearWarnings();
    }

    public void commit() throws SQLException {
        this.connection.commit();
    }

    public Array createArrayOf(String var1, Object[] var2) throws SQLException {
        return this.connection.createArrayOf(var1, var2);
    }

    public Blob createBlob() throws SQLException {
        return this.connection.createBlob();
    }

    public Clob createClob() throws SQLException {
        return this.connection.createClob();
    }

    public NClob createNClob() throws SQLException {
        return this.connection.createNClob();
    }

    public SQLXML createSQLXML() throws SQLException {
        return this.connection.createSQLXML();
    }

    public Statement createStatement() throws SQLException {
        return this.connection.createStatement();
    }

    public Statement createStatement(int var1, int var2) throws SQLException {
        return this.connection.createStatement(var1, var2);
    }

    public Statement createStatement(int var1, int var2, int var3) throws SQLException {
        return this.connection.createStatement(var1, var2, var3);
    }

    public Struct createStruct(String var1, Object[] var2) throws SQLException {
        return this.connection.createStruct(var1, var2);
    }

    public boolean getAutoCommit() throws SQLException {
        return this.connection.getAutoCommit();
    }

    public String getCatalog() throws SQLException {
        return this.connection.getCatalog();
    }

    public Properties getClientInfo() throws SQLException {
        return this.connection.getClientInfo();
    }

    public String getClientInfo(String var1) throws SQLException {
        return this.connection.getClientInfo(var1);
    }

    public int getHoldability() throws SQLException {
        return this.connection.getHoldability();
    }

    public DatabaseMetaData getMetaData() throws SQLException {
        return this.connection.getMetaData();
    }

    public int getNetworkTimeout() throws SQLException {
        return this.connection.getNetworkTimeout();
    }

    public String getSchema() throws SQLException {
        return this.connection.getSchema();
    }

    public int getTransactionIsolation() throws SQLException {
        return this.connection.getTransactionIsolation();
    }

    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return this.connection.getTypeMap();
    }

    public SQLWarning getWarnings() throws SQLException {
        return this.connection.getWarnings();
    }

    public boolean isClosed() throws SQLException {
        return this.connection.isClosed();
    }

    public boolean isReadOnly() throws SQLException {
        return this.connection.isReadOnly();
    }

    public boolean isValid(int var1) throws SQLException {
        return this.connection.isValid(var1);
    }

    public String nativeSQL(String var1) throws SQLException {
        return this.connection.nativeSQL(var1);
    }

    public CallableStatement prepareCall(String var1) throws SQLException {
        return this.connection.prepareCall(var1);
    }

    public CallableStatement prepareCall(String var1, int var2, int var3) throws SQLException {
        return this.connection.prepareCall(var1, var2, var3);
    }

    public CallableStatement prepareCall(String var1, int var2, int var3, int var4) throws SQLException {
        return this.connection.prepareCall(var1, var2, var3, var4);
    }

    public PreparedStatement prepareStatement(String var1) throws SQLException {
        return this.connection.prepareStatement(var1);
    }

    public PreparedStatement prepareStatement(String var1, String[] var2) throws SQLException {
        return this.connection.prepareStatement(var1, var2);
    }

    public PreparedStatement prepareStatement(String var1, int var2) throws SQLException {
        return this.connection.prepareStatement(var1, var2);
    }

    public PreparedStatement prepareStatement(String var1, int var2, int var3) throws SQLException {
        return this.connection.prepareStatement(var1, var2, var3);
    }

    public PreparedStatement prepareStatement(String var1, int var2, int var3, int var4) throws SQLException {
        return this.connection.prepareStatement(var1, var2, var3, var4);
    }

    public PreparedStatement prepareStatement(String var1, int[] var2) throws SQLException {
        return this.connection.prepareStatement(var1, var2);
    }

    public void releaseSavepoint(Savepoint var1) throws SQLException {
        this.connection.releaseSavepoint(var1);
    }

    public void rollback() throws SQLException {
        this.connection.rollback();
    }

    public void rollback(Savepoint var1) throws SQLException {
        this.connection.rollback(var1);
    }

    public void setAutoCommit(boolean var1) throws SQLException {
        this.connection.setAutoCommit(var1);
    }

    public void setCatalog(String var1) throws SQLException {
        this.connection.setCatalog(var1);
    }

    public void setClientInfo(Properties var1) throws SQLClientInfoException {
        this.connection.setClientInfo(var1);
    }

    public void setClientInfo(String var1, String var2) throws SQLClientInfoException {
        this.connection.setClientInfo(var1, var2);
    }

    public void setHoldability(int var1) throws SQLException {
        this.connection.setHoldability(var1);
    }

    public void setNetworkTimeout(Executor var1, int var2) throws SQLException {
        this.connection.setNetworkTimeout(var1, var2);
    }

    public void setReadOnly(boolean var1) throws SQLException {
        this.connection.setReadOnly(var1);
    }

    public Savepoint setSavepoint() throws SQLException {
        return this.connection.setSavepoint();
    }

    public Savepoint setSavepoint(String var1) throws SQLException {
        return this.connection.setSavepoint(var1);
    }

    public void setSchema(String var1) throws SQLException {
        this.connection.setSchema(var1);
    }

    public void setTransactionIsolation(int var1) throws SQLException {
        this.connection.setTransactionIsolation(var1);
    }

    public void setTypeMap(Map<String, Class<?>> var1) throws SQLException {
        this.connection.setTypeMap(var1);
    }

    public void shutdown() throws SQLException {
        this.connection.close();
    }

    @Pure
    public void close() throws SQLException {
    }

    @Pure
    public boolean isWrapperFor(@NotNull Class<?> var1) throws SQLException {
        Objects.requireNonNull(var1);
        return var1.isInstance(this.connection) || this.connection.isWrapperFor(var1);
    }

    public <T> T unwrap(@NotNull Class<T> clazz) throws SQLException {
        Objects.requireNonNull(clazz);
        return clazz.isInstance(this.connection) ? (T) this.connection : this.connection.unwrap(clazz);
    }
}
