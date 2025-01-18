package top.auspice.data.database.sql.statements.getters;

import com.google.gson.JsonElement;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.data.database.DatabaseType;
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
    @NotNull
    private final DatabaseType a;
    @NotNull
    private final ResultSet b;

    public SimpleResultSetQuery(@NotNull DatabaseType var1, @NotNull ResultSet var2) {
        Objects.requireNonNull(var1, "");
        Objects.requireNonNull(var2, "");
        this.a = var1;
        this.b = var2;
    }

    public final boolean absolute(int var1) throws SQLException {
        return this.b.absolute(var1);
    }

    public final void afterLast() throws SQLException {
        this.b.afterLast();
    }

    public final void beforeFirst() throws SQLException {
        this.b.beforeFirst();
    }

    public final void cancelRowUpdates() throws SQLException {
        this.b.cancelRowUpdates();
    }

    public final void clearWarnings() throws SQLException {
        this.b.clearWarnings();
    }

    public final void close() throws SQLException {
        this.b.close();
    }

    public final void deleteRow() throws SQLException {
        this.b.deleteRow();
    }

    public final int findColumn(String var1) throws SQLException {
        return this.b.findColumn(var1);
    }

    public final boolean first() throws SQLException {
        return this.b.first();
    }

    public final Array getArray(int var1) throws SQLException {
        return this.b.getArray(var1);
    }

    public final Array getArray(String var1) throws SQLException {
        return this.b.getArray(var1);
    }

    public final InputStream getAsciiStream(int var1) throws SQLException {
        return this.b.getAsciiStream(var1);
    }

    public final InputStream getAsciiStream(String var1) throws SQLException {
        return this.b.getAsciiStream(var1);
    }

    public final BigDecimal getBigDecimal(int var1) throws SQLException {
        return this.b.getBigDecimal(var1);
    }

    @Deprecated(
    )
    public final BigDecimal getBigDecimal(int var1, int var2) throws SQLException {
        return this.b.getBigDecimal(var1, var2);
    }

    public final BigDecimal getBigDecimal(String var1) throws SQLException {
        return this.b.getBigDecimal(var1);
    }

    @Deprecated(
    )
    public final BigDecimal getBigDecimal(String var1, int var2) throws SQLException {
        return this.b.getBigDecimal(var1, var2);
    }

    public final InputStream getBinaryStream(int var1) throws SQLException {
        return this.b.getBinaryStream(var1);
    }

    public final InputStream getBinaryStream(String var1) throws SQLException {
        return this.b.getBinaryStream(var1);
    }

    public final Blob getBlob(int var1) throws SQLException {
        return this.b.getBlob(var1);
    }

    public final Blob getBlob(String var1) throws SQLException {
        return this.b.getBlob(var1);
    }

    public final boolean getBoolean(int var1) throws SQLException {
        return this.b.getBoolean(var1);
    }

    public final boolean getBoolean(String var1) throws SQLException {
        return this.b.getBoolean(var1);
    }

    public final byte getByte(int var1) throws SQLException {
        return this.b.getByte(var1);
    }

    public final byte getByte(String var1) throws SQLException {
        return this.b.getByte(var1);
    }

    public final byte[] getBytes(int var1) throws SQLException {
        return this.b.getBytes(var1);
    }

    public final byte[] getBytes(String var1) throws SQLException {
        return this.b.getBytes(var1);
    }

    public final Reader getCharacterStream(int var1) throws SQLException {
        return this.b.getCharacterStream(var1);
    }

    public final Reader getCharacterStream(String var1) throws SQLException {
        return this.b.getCharacterStream(var1);
    }

    public final Clob getClob(int var1) throws SQLException {
        return this.b.getClob(var1);
    }

    public final Clob getClob(String var1) throws SQLException {
        return this.b.getClob(var1);
    }

    public final int getConcurrency() throws SQLException {
        return this.b.getConcurrency();
    }

    public final String getCursorName() throws SQLException {
        return this.b.getCursorName();
    }

    public final Date getDate(int var1) throws SQLException {
        return this.b.getDate(var1);
    }

    public final Date getDate(int var1, Calendar var2) throws SQLException {
        return this.b.getDate(var1, var2);
    }

    public final Date getDate(String var1) throws SQLException {
        return this.b.getDate(var1);
    }

    public final Date getDate(String var1, Calendar var2) throws SQLException {
        return this.b.getDate(var1, var2);
    }

    public final double getDouble(int var1) throws SQLException {
        return this.b.getDouble(var1);
    }

    public final double getDouble(String var1) throws SQLException {
        return this.b.getDouble(var1);
    }

    public final int getFetchDirection() throws SQLException {
        return this.b.getFetchDirection();
    }

    public final int getFetchSize() throws SQLException {
        return this.b.getFetchSize();
    }

    public final float getFloat(int var1) throws SQLException {
        return this.b.getFloat(var1);
    }

    public final float getFloat(String var1) throws SQLException {
        return this.b.getFloat(var1);
    }

    public final int getHoldability() throws SQLException {
        return this.b.getHoldability();
    }

    public final int getInt(int var1) throws SQLException {
        return this.b.getInt(var1);
    }

    public final int getInt(String var1) throws SQLException {
        return this.b.getInt(var1);
    }

    public final long getLong(int var1) throws SQLException {
        return this.b.getLong(var1);
    }

    public final long getLong(String var1) throws SQLException {
        return this.b.getLong(var1);
    }

    public final ResultSetMetaData getMetaData() throws SQLException {
        return this.b.getMetaData();
    }

    public final Reader getNCharacterStream(int var1) throws SQLException {
        return this.b.getNCharacterStream(var1);
    }

    public final Reader getNCharacterStream(String var1) throws SQLException {
        return this.b.getNCharacterStream(var1);
    }

    public final NClob getNClob(int var1) throws SQLException {
        return this.b.getNClob(var1);
    }

    public final NClob getNClob(String var1) throws SQLException {
        return this.b.getNClob(var1);
    }

    public final String getNString(int var1) throws SQLException {
        return this.b.getNString(var1);
    }

    public final String getNString(String var1) throws SQLException {
        return this.b.getNString(var1);
    }

    public final Object getObject(int var1) throws SQLException {
        return this.b.getObject(var1);
    }

    public final <T> T getObject(int var1, Class<T> var2) throws SQLException {
        return (T) this.b.getObject(var1, var2);
    }

    public final Object getObject(int var1, Map<String, Class<?>> var2) throws SQLException {
        return this.b.getObject(var1, var2);
    }

    public final Object getObject(String var1) throws SQLException {
        return this.b.getObject(var1);
    }

    public final <T> T getObject(String var1, Class<T> var2) throws SQLException {
        return (T) this.b.getObject(var1, var2);
    }

    public final Object getObject(String var1, Map<String, Class<?>> var2) throws SQLException {
        return this.b.getObject(var1, var2);
    }

    public final Ref getRef(int var1) throws SQLException {
        return this.b.getRef(var1);
    }

    public final Ref getRef(String var1) throws SQLException {
        return this.b.getRef(var1);
    }

    public final int getRow() throws SQLException {
        return this.b.getRow();
    }

    public final RowId getRowId(int var1) throws SQLException {
        return this.b.getRowId(var1);
    }

    public final RowId getRowId(String var1) throws SQLException {
        return this.b.getRowId(var1);
    }

    public final SQLXML getSQLXML(int var1) throws SQLException {
        return this.b.getSQLXML(var1);
    }

    public final SQLXML getSQLXML(String var1) throws SQLException {
        return this.b.getSQLXML(var1);
    }

    public final short getShort(int var1) throws SQLException {
        return this.b.getShort(var1);
    }

    public final short getShort(String var1) throws SQLException {
        return this.b.getShort(var1);
    }

    public final Statement getStatement() throws SQLException {
        return this.b.getStatement();
    }

    public final String getString(int var1) throws SQLException {
        return this.b.getString(var1);
    }

    public final String getString(String var1) throws SQLException {
        return this.b.getString(var1);
    }

    public final Time getTime(int var1) throws SQLException {
        return this.b.getTime(var1);
    }

    public final Time getTime(int var1, Calendar var2) throws SQLException {
        return this.b.getTime(var1, var2);
    }

    public final Time getTime(String var1) throws SQLException {
        return this.b.getTime(var1);
    }

    public final Time getTime(String var1, Calendar var2) throws SQLException {
        return this.b.getTime(var1, var2);
    }

    public final Timestamp getTimestamp(int var1) throws SQLException {
        return this.b.getTimestamp(var1);
    }

    public final Timestamp getTimestamp(int var1, Calendar var2) throws SQLException {
        return this.b.getTimestamp(var1, var2);
    }

    public final Timestamp getTimestamp(String var1) throws SQLException {
        return this.b.getTimestamp(var1);
    }

    public final Timestamp getTimestamp(String var1, Calendar var2) throws SQLException {
        return this.b.getTimestamp(var1, var2);
    }

    public final int getType() throws SQLException {
        return this.b.getType();
    }

    public final URL getURL(int var1) throws SQLException {
        return this.b.getURL(var1);
    }

    public final URL getURL(String var1) throws SQLException {
        return this.b.getURL(var1);
    }

    @Deprecated(
    )
    public final InputStream getUnicodeStream(int var1) throws SQLException {
        return this.b.getUnicodeStream(var1);
    }

    @Deprecated(
    )
    public final InputStream getUnicodeStream(String var1) throws SQLException {
        return this.b.getUnicodeStream(var1);
    }

    public final SQLWarning getWarnings() throws SQLException {
        return this.b.getWarnings();
    }

    public final void insertRow() throws SQLException {
        this.b.insertRow();
    }

    public final boolean isAfterLast() throws SQLException {
        return this.b.isAfterLast();
    }

    public final boolean isBeforeFirst() throws SQLException {
        return this.b.isBeforeFirst();
    }

    public final boolean isClosed() throws SQLException {
        return this.b.isClosed();
    }

    public final boolean isFirst() throws SQLException {
        return this.b.isFirst();
    }

    public final boolean isLast() throws SQLException {
        return this.b.isLast();
    }

    public final boolean isWrapperFor(Class<?> var1) throws SQLException {
        return this.b.isWrapperFor(var1);
    }

    public final boolean last() throws SQLException {
        return this.b.last();
    }

    public final void moveToCurrentRow() throws SQLException {
        this.b.moveToCurrentRow();
    }

    public final void moveToInsertRow() throws SQLException {
        this.b.moveToInsertRow();
    }

    public final boolean next() throws SQLException {
        return this.b.next();
    }

    public final boolean previous() throws SQLException {
        return this.b.previous();
    }

    public final void refreshRow() throws SQLException {
        this.b.refreshRow();
    }

    public final boolean relative(int var1) throws SQLException {
        return this.b.relative(var1);
    }

    public final boolean rowDeleted() throws SQLException {
        return this.b.rowDeleted();
    }

    public final boolean rowInserted() throws SQLException {
        return this.b.rowInserted();
    }

    public final boolean rowUpdated() throws SQLException {
        return this.b.rowUpdated();
    }

    public final void setFetchDirection(int var1) throws SQLException {
        this.b.setFetchDirection(var1);
    }

    public final void setFetchSize(int var1) throws SQLException {
        this.b.setFetchSize(var1);
    }

    public final <T> T unwrap(Class<T> var1) throws SQLException {
        return (T) this.b.unwrap(var1);
    }

    public final void updateArray(int var1, Array var2) throws SQLException {
        this.b.updateArray(var1, var2);
    }

    public final void updateArray(String var1, Array var2) throws SQLException {
        this.b.updateArray(var1, var2);
    }

    public final void updateAsciiStream(int var1, InputStream var2) throws SQLException {
        this.b.updateAsciiStream(var1, var2);
    }

    public final void updateAsciiStream(int var1, InputStream var2, int var3) throws SQLException {
        this.b.updateAsciiStream(var1, var2, var3);
    }

    public final void updateAsciiStream(int var1, InputStream var2, long var3) throws SQLException {
        this.b.updateAsciiStream(var1, var2, var3);
    }

    public final void updateAsciiStream(String var1, InputStream var2) throws SQLException {
        this.b.updateAsciiStream(var1, var2);
    }

    public final void updateAsciiStream(String var1, InputStream var2, int var3) throws SQLException {
        this.b.updateAsciiStream(var1, var2, var3);
    }

    public final void updateAsciiStream(String var1, InputStream var2, long var3) throws SQLException {
        this.b.updateAsciiStream(var1, var2, var3);
    }

    public final void updateBigDecimal(int var1, BigDecimal var2) throws SQLException {
        this.b.updateBigDecimal(var1, var2);
    }

    public final void updateBigDecimal(String var1, BigDecimal var2) throws SQLException {
        this.b.updateBigDecimal(var1, var2);
    }

    public final void updateBinaryStream(int var1, InputStream var2) throws SQLException {
        this.b.updateBinaryStream(var1, var2);
    }

    public final void updateBinaryStream(int var1, InputStream var2, int var3) throws SQLException {
        this.b.updateBinaryStream(var1, var2, var3);
    }

    public final void updateBinaryStream(int var1, InputStream var2, long var3) throws SQLException {
        this.b.updateBinaryStream(var1, var2, var3);
    }

    public final void updateBinaryStream(String var1, InputStream var2) throws SQLException {
        this.b.updateBinaryStream(var1, var2);
    }

    public final void updateBinaryStream(String var1, InputStream var2, int var3) throws SQLException {
        this.b.updateBinaryStream(var1, var2, var3);
    }

    public final void updateBinaryStream(String var1, InputStream var2, long var3) throws SQLException {
        this.b.updateBinaryStream(var1, var2, var3);
    }

    public final void updateBlob(int var1, InputStream var2) throws SQLException {
        this.b.updateBlob(var1, var2);
    }

    public final void updateBlob(int var1, InputStream var2, long var3) throws SQLException {
        this.b.updateBlob(var1, var2, var3);
    }

    public final void updateBlob(int var1, Blob var2) throws SQLException {
        this.b.updateBlob(var1, var2);
    }

    public final void updateBlob(String var1, InputStream var2) throws SQLException {
        this.b.updateBlob(var1, var2);
    }

    public final void updateBlob(String var1, InputStream var2, long var3) throws SQLException {
        this.b.updateBlob(var1, var2, var3);
    }

    public final void updateBlob(String var1, Blob var2) throws SQLException {
        this.b.updateBlob(var1, var2);
    }

    public final void updateBoolean(int var1, boolean var2) throws SQLException {
        this.b.updateBoolean(var1, var2);
    }

    public final void updateBoolean(String var1, boolean var2) throws SQLException {
        this.b.updateBoolean(var1, var2);
    }

    public final void updateByte(int var1, byte var2) throws SQLException {
        this.b.updateByte(var1, var2);
    }

    public final void updateByte(String var1, byte var2) throws SQLException {
        this.b.updateByte(var1, var2);
    }

    public final void updateBytes(int var1, byte[] var2) throws SQLException {
        this.b.updateBytes(var1, var2);
    }

    public final void updateBytes(String var1, byte[] var2) throws SQLException {
        this.b.updateBytes(var1, var2);
    }

    public final void updateCharacterStream(int var1, Reader var2) throws SQLException {
        this.b.updateCharacterStream(var1, var2);
    }

    public final void updateCharacterStream(int var1, Reader var2, int var3) throws SQLException {
        this.b.updateCharacterStream(var1, var2, var3);
    }

    public final void updateCharacterStream(int var1, Reader var2, long var3) throws SQLException {
        this.b.updateCharacterStream(var1, var2, var3);
    }

    public final void updateCharacterStream(String var1, Reader var2) throws SQLException {
        this.b.updateCharacterStream(var1, var2);
    }

    public final void updateCharacterStream(String var1, Reader var2, int var3) throws SQLException {
        this.b.updateCharacterStream(var1, var2, var3);
    }

    public final void updateCharacterStream(String var1, Reader var2, long var3) throws SQLException {
        this.b.updateCharacterStream(var1, var2, var3);
    }

    public final void updateClob(int var1, Reader var2) throws SQLException {
        this.b.updateClob(var1, var2);
    }

    public final void updateClob(int var1, Reader var2, long var3) throws SQLException {
        this.b.updateClob(var1, var2, var3);
    }

    public final void updateClob(int var1, Clob var2) throws SQLException {
        this.b.updateClob(var1, var2);
    }

    public final void updateClob(String var1, Reader var2) throws SQLException {
        this.b.updateClob(var1, var2);
    }

    public final void updateClob(String var1, Reader var2, long var3) throws SQLException {
        this.b.updateClob(var1, var2, var3);
    }

    public final void updateClob(String var1, Clob var2) throws SQLException {
        this.b.updateClob(var1, var2);
    }

    public final void updateDate(int var1, Date var2) throws SQLException {
        this.b.updateDate(var1, var2);
    }

    public final void updateDate(String var1, Date var2) throws SQLException {
        this.b.updateDate(var1, var2);
    }

    public final void updateDouble(int var1, double var2) throws SQLException {
        this.b.updateDouble(var1, var2);
    }

    public final void updateDouble(String var1, double var2) throws SQLException {
        this.b.updateDouble(var1, var2);
    }

    public final void updateFloat(int var1, float var2) throws SQLException {
        this.b.updateFloat(var1, var2);
    }

    public final void updateFloat(String var1, float var2) throws SQLException {
        this.b.updateFloat(var1, var2);
    }

    public final void updateInt(int var1, int var2) throws SQLException {
        this.b.updateInt(var1, var2);
    }

    public final void updateInt(String var1, int var2) throws SQLException {
        this.b.updateInt(var1, var2);
    }

    public final void updateLong(int var1, long var2) throws SQLException {
        this.b.updateLong(var1, var2);
    }

    public final void updateLong(String var1, long var2) throws SQLException {
        this.b.updateLong(var1, var2);
    }

    public final void updateNCharacterStream(int var1, Reader var2) throws SQLException {
        this.b.updateNCharacterStream(var1, var2);
    }

    public final void updateNCharacterStream(int var1, Reader var2, long var3) throws SQLException {
        this.b.updateNCharacterStream(var1, var2, var3);
    }

    public final void updateNCharacterStream(String var1, Reader var2) throws SQLException {
        this.b.updateNCharacterStream(var1, var2);
    }

    public final void updateNCharacterStream(String var1, Reader var2, long var3) throws SQLException {
        this.b.updateNCharacterStream(var1, var2, var3);
    }

    public final void updateNClob(int var1, Reader var2) throws SQLException {
        this.b.updateNClob(var1, var2);
    }

    public final void updateNClob(int var1, Reader var2, long var3) throws SQLException {
        this.b.updateNClob(var1, var2, var3);
    }

    public final void updateNClob(int var1, NClob var2) throws SQLException {
        this.b.updateNClob(var1, var2);
    }

    public final void updateNClob(String var1, Reader var2) throws SQLException {
        this.b.updateNClob(var1, var2);
    }

    public final void updateNClob(String var1, Reader var2, long var3) throws SQLException {
        this.b.updateNClob(var1, var2, var3);
    }

    public final void updateNClob(String var1, NClob var2) throws SQLException {
        this.b.updateNClob(var1, var2);
    }

    public final void updateNString(int var1, String var2) throws SQLException {
        this.b.updateNString(var1, var2);
    }

    public final void updateNString(String var1, String var2) throws SQLException {
        this.b.updateNString(var1, var2);
    }

    public final void updateNull(int var1) throws SQLException {
        this.b.updateNull(var1);
    }

    public final void updateNull(String var1) throws SQLException {
        this.b.updateNull(var1);
    }

    public final void updateObject(int var1, Object var2) throws SQLException {
        this.b.updateObject(var1, var2);
    }

    public final void updateObject(int var1, Object var2, int var3) throws SQLException {
        this.b.updateObject(var1, var2, var3);
    }

    public final void updateObject(String var1, Object var2) throws SQLException {
        this.b.updateObject(var1, var2);
    }

    public final void updateObject(String var1, Object var2, int var3) throws SQLException {
        this.b.updateObject(var1, var2, var3);
    }

    public final void updateRef(int var1, Ref var2) throws SQLException {
        this.b.updateRef(var1, var2);
    }

    public final void updateRef(String var1, Ref var2) throws SQLException {
        this.b.updateRef(var1, var2);
    }

    public final void updateRow() throws SQLException {
        this.b.updateRow();
    }

    public final void updateRowId(int var1, RowId var2) throws SQLException {
        this.b.updateRowId(var1, var2);
    }

    public final void updateRowId(String var1, RowId var2) throws SQLException {
        this.b.updateRowId(var1, var2);
    }

    public final void updateSQLXML(int var1, SQLXML var2) throws SQLException {
        this.b.updateSQLXML(var1, var2);
    }

    public final void updateSQLXML(String var1, SQLXML var2) throws SQLException {
        this.b.updateSQLXML(var1, var2);
    }

    public final void updateShort(int var1, short var2) throws SQLException {
        this.b.updateShort(var1, var2);
    }

    public final void updateShort(String var1, short var2) throws SQLException {
        this.b.updateShort(var1, var2);
    }

    public final void updateString(int var1, String var2) throws SQLException {
        this.b.updateString(var1, var2);
    }

    public final void updateString(String var1, String var2) throws SQLException {
        this.b.updateString(var1, var2);
    }

    public final void updateTime(int var1, Time var2) throws SQLException {
        this.b.updateTime(var1, var2);
    }

    public final void updateTime(String var1, Time var2) throws SQLException {
        this.b.updateTime(var1, var2);
    }

    public final void updateTimestamp(int var1, Timestamp var2) throws SQLException {
        this.b.updateTimestamp(var1, var2);
    }

    public final void updateTimestamp(String var1, Timestamp var2) throws SQLException {
        this.b.updateTimestamp(var1, var2);
    }

    public final boolean wasNull() throws SQLException {
        return this.b.wasNull();
    }

    @Nullable
    public final UUID getUUID(@NotNull String var1) throws SQLException {
        Objects.requireNonNull(var1, "");
        if (this.a == DatabaseType.PostgreSQL) {
            return (UUID) this.b.getObject(var1, UUID.class);
        } else {
            byte[] var10000 = this.b.getBytes(var1);
            if (var10000 != null) {
                byte[] var2 = var10000;
                return SQLDatabase.Companion.asUUID(var2);
            } else {
                return null;
            }
        }
    }

    @Nullable
    public final JsonElement getJson(@NotNull String var1) throws SQLException {
        Objects.requireNonNull(var1, "");
        String var5;
        if (this.a == DatabaseType.H2) {
            byte[] var10000 = this.b.getBytes(var1);
            Intrinsics.checkNotNullExpressionValue(var10000, "");
            Charset var4 = StandardCharsets.UTF_8;
            Intrinsics.checkNotNullExpressionValue(var4, "");
            var5 = new String(var10000, var4);
        } else {
            var5 = this.b.getString(var1);
        }

        return KingdomsGson.fromString(var5);
    }
}
