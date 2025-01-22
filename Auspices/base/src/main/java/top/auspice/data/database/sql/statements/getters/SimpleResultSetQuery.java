package top.auspice.data.database.sql.statements.getters;

import com.google.gson.JsonElement;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.data.database.DatabaseType;
import top.auspice.data.database.sql.base.SQLDatabase;
import top.auspice.utils.gson.KingdomsGson;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Calendar;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class SimpleResultSetQuery implements ResultSet {

    private final @NotNull DatabaseType databaseType;
    private final @NotNull ResultSet resultSet;

    public SimpleResultSetQuery(@NotNull DatabaseType databaseType, @NotNull ResultSet resultSet) {
        Objects.requireNonNull(databaseType, "databaseType");
        Objects.requireNonNull(resultSet, "resultSet");
        this.databaseType = databaseType;
        this.resultSet = resultSet;
    }

    public boolean absolute(int var1) throws SQLException {
        return this.resultSet.absolute(var1);
    }

    public void afterLast() throws SQLException {
        this.resultSet.afterLast();
    }

    public void beforeFirst() throws SQLException {
        this.resultSet.beforeFirst();
    }

    public void cancelRowUpdates() throws SQLException {
        this.resultSet.cancelRowUpdates();
    }

    public void clearWarnings() throws SQLException {
        this.resultSet.clearWarnings();
    }

    public void close() throws SQLException {
        this.resultSet.close();
    }

    public void deleteRow() throws SQLException {
        this.resultSet.deleteRow();
    }

    public int findColumn(String var1) throws SQLException {
        return this.resultSet.findColumn(var1);
    }

    public boolean first() throws SQLException {
        return this.resultSet.first();
    }

    public Array getArray(int var1) throws SQLException {
        return this.resultSet.getArray(var1);
    }

    public Array getArray(String var1) throws SQLException {
        return this.resultSet.getArray(var1);
    }

    public InputStream getAsciiStream(int var1) throws SQLException {
        return this.resultSet.getAsciiStream(var1);
    }

    public InputStream getAsciiStream(String var1) throws SQLException {
        return this.resultSet.getAsciiStream(var1);
    }

    public BigDecimal getBigDecimal(int var1) throws SQLException {
        return this.resultSet.getBigDecimal(var1);
    }

    @Deprecated(
    )
    public BigDecimal getBigDecimal(int var1, int var2) throws SQLException {
        return this.resultSet.getBigDecimal(var1, var2);
    }

    public BigDecimal getBigDecimal(String var1) throws SQLException {
        return this.resultSet.getBigDecimal(var1);
    }

    @Deprecated(
    )
    public BigDecimal getBigDecimal(String var1, int var2) throws SQLException {
        return this.resultSet.getBigDecimal(var1, var2);
    }

    public InputStream getBinaryStream(int var1) throws SQLException {
        return this.resultSet.getBinaryStream(var1);
    }

    public InputStream getBinaryStream(String var1) throws SQLException {
        return this.resultSet.getBinaryStream(var1);
    }

    public Blob getBlob(int var1) throws SQLException {
        return this.resultSet.getBlob(var1);
    }

    public Blob getBlob(String var1) throws SQLException {
        return this.resultSet.getBlob(var1);
    }

    public boolean getBoolean(int var1) throws SQLException {
        return this.resultSet.getBoolean(var1);
    }

    public boolean getBoolean(String var1) throws SQLException {
        return this.resultSet.getBoolean(var1);
    }

    public byte getByte(int var1) throws SQLException {
        return this.resultSet.getByte(var1);
    }

    public byte getByte(String var1) throws SQLException {
        return this.resultSet.getByte(var1);
    }

    public byte[] getBytes(int var1) throws SQLException {
        return this.resultSet.getBytes(var1);
    }

    public byte[] getBytes(String var1) throws SQLException {
        return this.resultSet.getBytes(var1);
    }

    public Reader getCharacterStream(int var1) throws SQLException {
        return this.resultSet.getCharacterStream(var1);
    }

    public Reader getCharacterStream(String var1) throws SQLException {
        return this.resultSet.getCharacterStream(var1);
    }

    public Clob getClob(int var1) throws SQLException {
        return this.resultSet.getClob(var1);
    }

    public Clob getClob(String var1) throws SQLException {
        return this.resultSet.getClob(var1);
    }

    public int getConcurrency() throws SQLException {
        return this.resultSet.getConcurrency();
    }

    public String getCursorName() throws SQLException {
        return this.resultSet.getCursorName();
    }

    public Date getDate(int var1) throws SQLException {
        return this.resultSet.getDate(var1);
    }

    public Date getDate(int var1, Calendar var2) throws SQLException {
        return this.resultSet.getDate(var1, var2);
    }

    public Date getDate(String var1) throws SQLException {
        return this.resultSet.getDate(var1);
    }

    public Date getDate(String var1, Calendar var2) throws SQLException {
        return this.resultSet.getDate(var1, var2);
    }

    public double getDouble(int var1) throws SQLException {
        return this.resultSet.getDouble(var1);
    }

    public double getDouble(String var1) throws SQLException {
        return this.resultSet.getDouble(var1);
    }

    public int getFetchDirection() throws SQLException {
        return this.resultSet.getFetchDirection();
    }

    public int getFetchSize() throws SQLException {
        return this.resultSet.getFetchSize();
    }

    public float getFloat(int var1) throws SQLException {
        return this.resultSet.getFloat(var1);
    }

    public float getFloat(String var1) throws SQLException {
        return this.resultSet.getFloat(var1);
    }

    public int getHoldability() throws SQLException {
        return this.resultSet.getHoldability();
    }

    public int getInt(int var1) throws SQLException {
        return this.resultSet.getInt(var1);
    }

    public int getInt(String var1) throws SQLException {
        return this.resultSet.getInt(var1);
    }

    public long getLong(int var1) throws SQLException {
        return this.resultSet.getLong(var1);
    }

    public long getLong(String var1) throws SQLException {
        return this.resultSet.getLong(var1);
    }

    public ResultSetMetaData getMetaData() throws SQLException {
        return this.resultSet.getMetaData();
    }

    public Reader getNCharacterStream(int var1) throws SQLException {
        return this.resultSet.getNCharacterStream(var1);
    }

    public Reader getNCharacterStream(String var1) throws SQLException {
        return this.resultSet.getNCharacterStream(var1);
    }

    public NClob getNClob(int var1) throws SQLException {
        return this.resultSet.getNClob(var1);
    }

    public NClob getNClob(String var1) throws SQLException {
        return this.resultSet.getNClob(var1);
    }

    public String getNString(int var1) throws SQLException {
        return this.resultSet.getNString(var1);
    }

    public String getNString(String var1) throws SQLException {
        return this.resultSet.getNString(var1);
    }

    public Object getObject(int var1) throws SQLException {
        return this.resultSet.getObject(var1);
    }

    public <T> T getObject(int var1, Class<T> var2) throws SQLException {
        return this.resultSet.getObject(var1, var2);
    }

    public Object getObject(int var1, Map<String, Class<?>> var2) throws SQLException {
        return this.resultSet.getObject(var1, var2);
    }

    public Object getObject(String var1) throws SQLException {
        return this.resultSet.getObject(var1);
    }

    public <T> T getObject(String var1, Class<T> var2) throws SQLException {
        return this.resultSet.getObject(var1, var2);
    }

    public Object getObject(String var1, Map<String, Class<?>> var2) throws SQLException {
        return this.resultSet.getObject(var1, var2);
    }

    public Ref getRef(int var1) throws SQLException {
        return this.resultSet.getRef(var1);
    }

    public Ref getRef(String var1) throws SQLException {
        return this.resultSet.getRef(var1);
    }

    public int getRow() throws SQLException {
        return this.resultSet.getRow();
    }

    public RowId getRowId(int var1) throws SQLException {
        return this.resultSet.getRowId(var1);
    }

    public RowId getRowId(String var1) throws SQLException {
        return this.resultSet.getRowId(var1);
    }

    public SQLXML getSQLXML(int var1) throws SQLException {
        return this.resultSet.getSQLXML(var1);
    }

    public SQLXML getSQLXML(String var1) throws SQLException {
        return this.resultSet.getSQLXML(var1);
    }

    public short getShort(int var1) throws SQLException {
        return this.resultSet.getShort(var1);
    }

    public short getShort(String var1) throws SQLException {
        return this.resultSet.getShort(var1);
    }

    public Statement getStatement() throws SQLException {
        return this.resultSet.getStatement();
    }

    public String getString(int var1) throws SQLException {
        return this.resultSet.getString(var1);
    }

    public String getString(String var1) throws SQLException {
        return this.resultSet.getString(var1);
    }

    public Time getTime(int var1) throws SQLException {
        return this.resultSet.getTime(var1);
    }

    public Time getTime(int var1, Calendar var2) throws SQLException {
        return this.resultSet.getTime(var1, var2);
    }

    public Time getTime(String var1) throws SQLException {
        return this.resultSet.getTime(var1);
    }

    public Time getTime(String var1, Calendar var2) throws SQLException {
        return this.resultSet.getTime(var1, var2);
    }

    public Timestamp getTimestamp(int var1) throws SQLException {
        return this.resultSet.getTimestamp(var1);
    }

    public Timestamp getTimestamp(int var1, Calendar var2) throws SQLException {
        return this.resultSet.getTimestamp(var1, var2);
    }

    public Timestamp getTimestamp(String var1) throws SQLException {
        return this.resultSet.getTimestamp(var1);
    }

    public Timestamp getTimestamp(String var1, Calendar var2) throws SQLException {
        return this.resultSet.getTimestamp(var1, var2);
    }

    public int getType() throws SQLException {
        return this.resultSet.getType();
    }

    public URL getURL(int var1) throws SQLException {
        return this.resultSet.getURL(var1);
    }

    public URL getURL(String var1) throws SQLException {
        return this.resultSet.getURL(var1);
    }

    @Deprecated(
    )
    public InputStream getUnicodeStream(int var1) throws SQLException {
        return this.resultSet.getUnicodeStream(var1);
    }

    @Deprecated(
    )
    public InputStream getUnicodeStream(String var1) throws SQLException {
        return this.resultSet.getUnicodeStream(var1);
    }

    public SQLWarning getWarnings() throws SQLException {
        return this.resultSet.getWarnings();
    }

    public void insertRow() throws SQLException {
        this.resultSet.insertRow();
    }

    public boolean isAfterLast() throws SQLException {
        return this.resultSet.isAfterLast();
    }

    public boolean isBeforeFirst() throws SQLException {
        return this.resultSet.isBeforeFirst();
    }

    public boolean isClosed() throws SQLException {
        return this.resultSet.isClosed();
    }

    public boolean isFirst() throws SQLException {
        return this.resultSet.isFirst();
    }

    public boolean isLast() throws SQLException {
        return this.resultSet.isLast();
    }

    public boolean isWrapperFor(Class<?> var1) throws SQLException {
        return this.resultSet.isWrapperFor(var1);
    }

    public boolean last() throws SQLException {
        return this.resultSet.last();
    }

    public void moveToCurrentRow() throws SQLException {
        this.resultSet.moveToCurrentRow();
    }

    public void moveToInsertRow() throws SQLException {
        this.resultSet.moveToInsertRow();
    }

    public boolean next() throws SQLException {
        return this.resultSet.next();
    }

    public boolean previous() throws SQLException {
        return this.resultSet.previous();
    }

    public void refreshRow() throws SQLException {
        this.resultSet.refreshRow();
    }

    public boolean relative(int var1) throws SQLException {
        return this.resultSet.relative(var1);
    }

    public boolean rowDeleted() throws SQLException {
        return this.resultSet.rowDeleted();
    }

    public boolean rowInserted() throws SQLException {
        return this.resultSet.rowInserted();
    }

    public boolean rowUpdated() throws SQLException {
        return this.resultSet.rowUpdated();
    }

    public void setFetchDirection(int var1) throws SQLException {
        this.resultSet.setFetchDirection(var1);
    }

    public void setFetchSize(int var1) throws SQLException {
        this.resultSet.setFetchSize(var1);
    }

    public <T> T unwrap(Class<T> var1) throws SQLException {
        return this.resultSet.unwrap(var1);
    }

    public void updateArray(int var1, Array var2) throws SQLException {
        this.resultSet.updateArray(var1, var2);
    }

    public void updateArray(String var1, Array var2) throws SQLException {
        this.resultSet.updateArray(var1, var2);
    }

    public void updateAsciiStream(int var1, InputStream var2) throws SQLException {
        this.resultSet.updateAsciiStream(var1, var2);
    }

    public void updateAsciiStream(int var1, InputStream var2, int var3) throws SQLException {
        this.resultSet.updateAsciiStream(var1, var2, var3);
    }

    public void updateAsciiStream(int var1, InputStream var2, long var3) throws SQLException {
        this.resultSet.updateAsciiStream(var1, var2, var3);
    }

    public void updateAsciiStream(String var1, InputStream var2) throws SQLException {
        this.resultSet.updateAsciiStream(var1, var2);
    }

    public void updateAsciiStream(String var1, InputStream var2, int var3) throws SQLException {
        this.resultSet.updateAsciiStream(var1, var2, var3);
    }

    public void updateAsciiStream(String var1, InputStream var2, long var3) throws SQLException {
        this.resultSet.updateAsciiStream(var1, var2, var3);
    }

    public void updateBigDecimal(int var1, BigDecimal var2) throws SQLException {
        this.resultSet.updateBigDecimal(var1, var2);
    }

    public void updateBigDecimal(String var1, BigDecimal var2) throws SQLException {
        this.resultSet.updateBigDecimal(var1, var2);
    }

    public void updateBinaryStream(int var1, InputStream var2) throws SQLException {
        this.resultSet.updateBinaryStream(var1, var2);
    }

    public void updateBinaryStream(int var1, InputStream var2, int var3) throws SQLException {
        this.resultSet.updateBinaryStream(var1, var2, var3);
    }

    public void updateBinaryStream(int var1, InputStream var2, long var3) throws SQLException {
        this.resultSet.updateBinaryStream(var1, var2, var3);
    }

    public void updateBinaryStream(String var1, InputStream var2) throws SQLException {
        this.resultSet.updateBinaryStream(var1, var2);
    }

    public void updateBinaryStream(String var1, InputStream var2, int var3) throws SQLException {
        this.resultSet.updateBinaryStream(var1, var2, var3);
    }

    public void updateBinaryStream(String var1, InputStream var2, long var3) throws SQLException {
        this.resultSet.updateBinaryStream(var1, var2, var3);
    }

    public void updateBlob(int var1, InputStream var2) throws SQLException {
        this.resultSet.updateBlob(var1, var2);
    }

    public void updateBlob(int var1, InputStream var2, long var3) throws SQLException {
        this.resultSet.updateBlob(var1, var2, var3);
    }

    public void updateBlob(int var1, Blob var2) throws SQLException {
        this.resultSet.updateBlob(var1, var2);
    }

    public void updateBlob(String var1, InputStream var2) throws SQLException {
        this.resultSet.updateBlob(var1, var2);
    }

    public void updateBlob(String var1, InputStream var2, long var3) throws SQLException {
        this.resultSet.updateBlob(var1, var2, var3);
    }

    public void updateBlob(String var1, Blob var2) throws SQLException {
        this.resultSet.updateBlob(var1, var2);
    }

    public void updateBoolean(int var1, boolean var2) throws SQLException {
        this.resultSet.updateBoolean(var1, var2);
    }

    public void updateBoolean(String var1, boolean var2) throws SQLException {
        this.resultSet.updateBoolean(var1, var2);
    }

    public void updateByte(int var1, byte var2) throws SQLException {
        this.resultSet.updateByte(var1, var2);
    }

    public void updateByte(String var1, byte var2) throws SQLException {
        this.resultSet.updateByte(var1, var2);
    }

    public void updateBytes(int var1, byte[] var2) throws SQLException {
        this.resultSet.updateBytes(var1, var2);
    }

    public void updateBytes(String var1, byte[] var2) throws SQLException {
        this.resultSet.updateBytes(var1, var2);
    }

    public void updateCharacterStream(int var1, Reader var2) throws SQLException {
        this.resultSet.updateCharacterStream(var1, var2);
    }

    public void updateCharacterStream(int var1, Reader var2, int var3) throws SQLException {
        this.resultSet.updateCharacterStream(var1, var2, var3);
    }

    public void updateCharacterStream(int var1, Reader var2, long var3) throws SQLException {
        this.resultSet.updateCharacterStream(var1, var2, var3);
    }

    public void updateCharacterStream(String var1, Reader var2) throws SQLException {
        this.resultSet.updateCharacterStream(var1, var2);
    }

    public void updateCharacterStream(String var1, Reader var2, int var3) throws SQLException {
        this.resultSet.updateCharacterStream(var1, var2, var3);
    }

    public void updateCharacterStream(String var1, Reader var2, long var3) throws SQLException {
        this.resultSet.updateCharacterStream(var1, var2, var3);
    }

    public void updateClob(int var1, Reader var2) throws SQLException {
        this.resultSet.updateClob(var1, var2);
    }

    public void updateClob(int var1, Reader var2, long var3) throws SQLException {
        this.resultSet.updateClob(var1, var2, var3);
    }

    public void updateClob(int var1, Clob var2) throws SQLException {
        this.resultSet.updateClob(var1, var2);
    }

    public void updateClob(String var1, Reader var2) throws SQLException {
        this.resultSet.updateClob(var1, var2);
    }

    public void updateClob(String var1, Reader var2, long var3) throws SQLException {
        this.resultSet.updateClob(var1, var2, var3);
    }

    public void updateClob(String var1, Clob var2) throws SQLException {
        this.resultSet.updateClob(var1, var2);
    }

    public void updateDate(int var1, Date var2) throws SQLException {
        this.resultSet.updateDate(var1, var2);
    }

    public void updateDate(String var1, Date var2) throws SQLException {
        this.resultSet.updateDate(var1, var2);
    }

    public void updateDouble(int var1, double var2) throws SQLException {
        this.resultSet.updateDouble(var1, var2);
    }

    public void updateDouble(String var1, double var2) throws SQLException {
        this.resultSet.updateDouble(var1, var2);
    }

    public void updateFloat(int var1, float var2) throws SQLException {
        this.resultSet.updateFloat(var1, var2);
    }

    public void updateFloat(String var1, float var2) throws SQLException {
        this.resultSet.updateFloat(var1, var2);
    }

    public void updateInt(int var1, int var2) throws SQLException {
        this.resultSet.updateInt(var1, var2);
    }

    public void updateInt(String var1, int var2) throws SQLException {
        this.resultSet.updateInt(var1, var2);
    }

    public void updateLong(int var1, long var2) throws SQLException {
        this.resultSet.updateLong(var1, var2);
    }

    public void updateLong(String var1, long var2) throws SQLException {
        this.resultSet.updateLong(var1, var2);
    }

    public void updateNCharacterStream(int var1, Reader var2) throws SQLException {
        this.resultSet.updateNCharacterStream(var1, var2);
    }

    public void updateNCharacterStream(int var1, Reader var2, long var3) throws SQLException {
        this.resultSet.updateNCharacterStream(var1, var2, var3);
    }

    public void updateNCharacterStream(String var1, Reader var2) throws SQLException {
        this.resultSet.updateNCharacterStream(var1, var2);
    }

    public void updateNCharacterStream(String var1, Reader var2, long var3) throws SQLException {
        this.resultSet.updateNCharacterStream(var1, var2, var3);
    }

    public void updateNClob(int var1, Reader var2) throws SQLException {
        this.resultSet.updateNClob(var1, var2);
    }

    public void updateNClob(int var1, Reader var2, long var3) throws SQLException {
        this.resultSet.updateNClob(var1, var2, var3);
    }

    public void updateNClob(int var1, NClob var2) throws SQLException {
        this.resultSet.updateNClob(var1, var2);
    }

    public void updateNClob(String var1, Reader var2) throws SQLException {
        this.resultSet.updateNClob(var1, var2);
    }

    public void updateNClob(String var1, Reader var2, long var3) throws SQLException {
        this.resultSet.updateNClob(var1, var2, var3);
    }

    public void updateNClob(String var1, NClob var2) throws SQLException {
        this.resultSet.updateNClob(var1, var2);
    }

    public void updateNString(int var1, String var2) throws SQLException {
        this.resultSet.updateNString(var1, var2);
    }

    public void updateNString(String var1, String var2) throws SQLException {
        this.resultSet.updateNString(var1, var2);
    }

    public void updateNull(int var1) throws SQLException {
        this.resultSet.updateNull(var1);
    }

    public void updateNull(String var1) throws SQLException {
        this.resultSet.updateNull(var1);
    }

    public void updateObject(int var1, Object var2) throws SQLException {
        this.resultSet.updateObject(var1, var2);
    }

    public void updateObject(int var1, Object var2, int var3) throws SQLException {
        this.resultSet.updateObject(var1, var2, var3);
    }

    public void updateObject(String var1, Object var2) throws SQLException {
        this.resultSet.updateObject(var1, var2);
    }

    public void updateObject(String var1, Object var2, int var3) throws SQLException {
        this.resultSet.updateObject(var1, var2, var3);
    }

    public void updateRef(int var1, Ref var2) throws SQLException {
        this.resultSet.updateRef(var1, var2);
    }

    public void updateRef(String var1, Ref var2) throws SQLException {
        this.resultSet.updateRef(var1, var2);
    }

    public void updateRow() throws SQLException {
        this.resultSet.updateRow();
    }

    public void updateRowId(int var1, RowId var2) throws SQLException {
        this.resultSet.updateRowId(var1, var2);
    }

    public void updateRowId(String var1, RowId var2) throws SQLException {
        this.resultSet.updateRowId(var1, var2);
    }

    public void updateSQLXML(int var1, SQLXML var2) throws SQLException {
        this.resultSet.updateSQLXML(var1, var2);
    }

    public void updateSQLXML(String var1, SQLXML var2) throws SQLException {
        this.resultSet.updateSQLXML(var1, var2);
    }

    public void updateShort(int var1, short var2) throws SQLException {
        this.resultSet.updateShort(var1, var2);
    }

    public void updateShort(String var1, short var2) throws SQLException {
        this.resultSet.updateShort(var1, var2);
    }

    public void updateString(int var1, String var2) throws SQLException {
        this.resultSet.updateString(var1, var2);
    }

    public void updateString(String var1, String var2) throws SQLException {
        this.resultSet.updateString(var1, var2);
    }

    public void updateTime(int var1, Time var2) throws SQLException {
        this.resultSet.updateTime(var1, var2);
    }

    public void updateTime(String var1, Time var2) throws SQLException {
        this.resultSet.updateTime(var1, var2);
    }

    public void updateTimestamp(int var1, Timestamp var2) throws SQLException {
        this.resultSet.updateTimestamp(var1, var2);
    }

    public void updateTimestamp(String var1, Timestamp var2) throws SQLException {
        this.resultSet.updateTimestamp(var1, var2);
    }

    public boolean wasNull() throws SQLException {
        return this.resultSet.wasNull();
    }

    public @Nullable UUID getUUID(@NotNull String var1) throws SQLException {
        Objects.requireNonNull(var1, "");
        if (this.databaseType == DatabaseType.PostgreSQL) {
            return this.resultSet.getObject(var1, UUID.class);
        } else {
            byte[] var10000 = this.resultSet.getBytes(var1);
            if (var10000 != null) {
                return SQLDatabase.asUUID(var10000);
            } else {
                return null;
            }
        }
    }

    public @Nullable JsonElement getJson(@NotNull String var1) throws SQLException {
        Objects.requireNonNull(var1, "");
        String var5;
        if (this.databaseType == DatabaseType.H2) {
            byte[] var10000 = this.resultSet.getBytes(var1);
            Intrinsics.checkNotNullExpressionValue(var10000, "");
            Charset var4 = StandardCharsets.UTF_8;
            Intrinsics.checkNotNullExpressionValue(var4, "");
            var5 = new String(var10000, var4);
        } else {
            var5 = this.resultSet.getString(var1);
        }

        return KingdomsGson.fromString(var5);
    }
}
