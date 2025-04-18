package net.aurika.ecliptor.database.sql.statements.getters;

import com.google.gson.JsonElement;
import kotlin.jvm.internal.Intrinsics;
import net.aurika.ecliptor.database.DatabaseType;
import net.aurika.ecliptor.database.sql.base.SQLDatabase;
import net.aurika.util.gson.AurikaGson;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

public class SimpleResultSetQuery implements ResultSet {

  private final @NotNull DatabaseType databaseType;
  private final @NotNull ResultSet resultSet;

  public SimpleResultSetQuery(@NotNull DatabaseType databaseType, @NotNull ResultSet resultSet) {
    Validate.Arg.notNull(databaseType, "databaseType");
    Validate.Arg.notNull(resultSet, "resultSet");
    this.databaseType = databaseType;
    this.resultSet = resultSet;
  }

  @Override
  public boolean absolute(int row) throws SQLException {
    return resultSet.absolute(row);
  }

  @Override
  public void afterLast() throws SQLException {
    resultSet.afterLast();
  }

  @Override
  public void beforeFirst() throws SQLException {
    resultSet.beforeFirst();
  }

  @Override
  public void cancelRowUpdates() throws SQLException {
    resultSet.cancelRowUpdates();
  }

  @Override
  public void clearWarnings() throws SQLException {
    resultSet.clearWarnings();
  }

  @Override
  public void close() throws SQLException {
    resultSet.close();
  }

  @Override
  public void deleteRow() throws SQLException {
    resultSet.deleteRow();
  }

  @Override
  public int findColumn(String columnLabel) throws SQLException {
    return resultSet.findColumn(columnLabel);
  }

  @Override
  public boolean first() throws SQLException {
    return resultSet.first();
  }

  @Override
  public Array getArray(int columnIndex) throws SQLException {
    return resultSet.getArray(columnIndex);
  }

  @Override
  public Array getArray(String columnLabel) throws SQLException {
    return resultSet.getArray(columnLabel);
  }

  @Override
  public InputStream getAsciiStream(int columnIndex) throws SQLException {
    return resultSet.getAsciiStream(columnIndex);
  }

  @Override
  public InputStream getAsciiStream(String columnLabel) throws SQLException {
    return resultSet.getAsciiStream(columnLabel);
  }

  @Override
  public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
    return resultSet.getBigDecimal(columnIndex);
  }

  @Override
  @Deprecated
  public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
    return resultSet.getBigDecimal(columnIndex, scale);
  }

  @Override
  public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
    return resultSet.getBigDecimal(columnLabel);
  }

  @Override
  @Deprecated
  public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
    return resultSet.getBigDecimal(columnLabel, scale);
  }

  @Override
  public InputStream getBinaryStream(int columnIndex) throws SQLException {
    return resultSet.getBinaryStream(columnIndex);
  }

  @Override
  public InputStream getBinaryStream(String columnLabel) throws SQLException {
    return resultSet.getBinaryStream(columnLabel);
  }

  @Override
  public Blob getBlob(int columnIndex) throws SQLException {
    return resultSet.getBlob(columnIndex);
  }

  @Override
  public Blob getBlob(String columnLabel) throws SQLException {
    return resultSet.getBlob(columnLabel);
  }

  @Override
  public boolean getBoolean(int columnIndex) throws SQLException {
    return resultSet.getBoolean(columnIndex);
  }

  @Override
  public boolean getBoolean(String columnLabel) throws SQLException {
    return resultSet.getBoolean(columnLabel);
  }

  @Override
  public byte getByte(int columnIndex) throws SQLException {
    return resultSet.getByte(columnIndex);
  }

  @Override
  public byte getByte(String columnLabel) throws SQLException {
    return resultSet.getByte(columnLabel);
  }

  @Override
  public byte[] getBytes(int columnIndex) throws SQLException {
    return resultSet.getBytes(columnIndex);
  }

  @Override
  public byte[] getBytes(String columnLabel) throws SQLException {
    return resultSet.getBytes(columnLabel);
  }

  @Override
  public Reader getCharacterStream(int columnIndex) throws SQLException {
    return resultSet.getCharacterStream(columnIndex);
  }

  @Override
  public Reader getCharacterStream(String columnLabel) throws SQLException {
    return resultSet.getCharacterStream(columnLabel);
  }

  @Override
  public Clob getClob(int columnIndex) throws SQLException {
    return resultSet.getClob(columnIndex);
  }

  @Override
  public Clob getClob(String columnLabel) throws SQLException {
    return resultSet.getClob(columnLabel);
  }

  @Override
  public int getConcurrency() throws SQLException {
    return resultSet.getConcurrency();
  }

  @Override
  public String getCursorName() throws SQLException {
    return resultSet.getCursorName();
  }

  @Override
  public Date getDate(int columnIndex) throws SQLException {
    return resultSet.getDate(columnIndex);
  }

  @Override
  public Date getDate(int columnIndex, Calendar var2) throws SQLException {
    return resultSet.getDate(columnIndex, var2);
  }

  @Override
  public Date getDate(String columnLabel) throws SQLException {
    return resultSet.getDate(columnLabel);
  }

  @Override
  public Date getDate(String columnLabel, Calendar cal) throws SQLException {
    return resultSet.getDate(columnLabel, cal);
  }

  @Override
  public double getDouble(int columnIndex) throws SQLException {
    return resultSet.getDouble(columnIndex);
  }

  @Override
  public double getDouble(String columnLabel) throws SQLException {
    return resultSet.getDouble(columnLabel);
  }

  @Override
  public int getFetchDirection() throws SQLException {
    return resultSet.getFetchDirection();
  }

  @Override
  public int getFetchSize() throws SQLException {
    return resultSet.getFetchSize();
  }

  @Override
  public float getFloat(int columnIndex) throws SQLException {
    return resultSet.getFloat(columnIndex);
  }

  @Override
  public float getFloat(String columnLabel) throws SQLException {
    return resultSet.getFloat(columnLabel);
  }

  @Override
  public int getHoldability() throws SQLException {
    return resultSet.getHoldability();
  }

  @Override
  public int getInt(int columnIndex) throws SQLException {
    return resultSet.getInt(columnIndex);
  }

  @Override
  public int getInt(String columnLabel) throws SQLException {
    return resultSet.getInt(columnLabel);
  }

  @Override
  public long getLong(int columnIndex) throws SQLException {
    return resultSet.getLong(columnIndex);
  }

  @Override
  public long getLong(String columnLabel) throws SQLException {
    return resultSet.getLong(columnLabel);
  }

  @Override
  public ResultSetMetaData getMetaData() throws SQLException {
    return resultSet.getMetaData();
  }

  @Override
  public Reader getNCharacterStream(int columnIndex) throws SQLException {
    return resultSet.getNCharacterStream(columnIndex);
  }

  @Override
  public Reader getNCharacterStream(String columnLabel) throws SQLException {
    return resultSet.getNCharacterStream(columnLabel);
  }

  @Override
  public NClob getNClob(int columnIndex) throws SQLException {
    return resultSet.getNClob(columnIndex);
  }

  @Override
  public NClob getNClob(String columnLabel) throws SQLException {
    return resultSet.getNClob(columnLabel);
  }

  @Override
  public String getNString(int columnIndex) throws SQLException {
    return resultSet.getNString(columnIndex);
  }

  @Override
  public String getNString(String columnLabel) throws SQLException {
    return resultSet.getNString(columnLabel);
  }

  @Override
  public Object getObject(int columnIndex) throws SQLException {
    return resultSet.getObject(columnIndex);
  }

  @Override
  public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
    return resultSet.getObject(columnIndex, type);
  }

  @Override
  public Object getObject(int columnIndex, Map<String, Class<?>> stringClassMap) throws SQLException {
    return resultSet.getObject(columnIndex, stringClassMap);
  }

  @Override
  public Object getObject(String columnLabel) throws SQLException {
    return resultSet.getObject(columnLabel);
  }

  @Override
  public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
    return resultSet.getObject(columnLabel, type);
  }

  @Override
  public Object getObject(String columnLabel, Map<String, Class<?>> stringClassMap) throws SQLException {
    return resultSet.getObject(columnLabel, stringClassMap);
  }

  @Override
  public Ref getRef(int columnIndex) throws SQLException {
    return resultSet.getRef(columnIndex);
  }

  @Override
  public Ref getRef(String columnLabel) throws SQLException {
    return resultSet.getRef(columnLabel);
  }

  @Override
  public int getRow() throws SQLException {
    return resultSet.getRow();
  }

  @Override
  public RowId getRowId(int columnIndex) throws SQLException {
    return resultSet.getRowId(columnIndex);
  }

  @Override
  public RowId getRowId(String columnLabel) throws SQLException {
    return resultSet.getRowId(columnLabel);
  }

  @Override
  public SQLXML getSQLXML(int columnIndex) throws SQLException {
    return resultSet.getSQLXML(columnIndex);
  }

  @Override
  public SQLXML getSQLXML(String columnLabel) throws SQLException {
    return resultSet.getSQLXML(columnLabel);
  }

  @Override
  public short getShort(int columnIndex) throws SQLException {
    return resultSet.getShort(columnIndex);
  }

  @Override
  public short getShort(String columnLabel) throws SQLException {
    return resultSet.getShort(columnLabel);
  }

  @Override
  public Statement getStatement() throws SQLException {
    return resultSet.getStatement();
  }

  @Override
  public String getString(int columnIndex) throws SQLException {
    return resultSet.getString(columnIndex);
  }

  @Override
  public String getString(String columnLabel) throws SQLException {
    return resultSet.getString(columnLabel);
  }

  @Override
  public Time getTime(int columnIndex) throws SQLException {
    return resultSet.getTime(columnIndex);
  }

  @Override
  public Time getTime(int columnIndex, Calendar cal) throws SQLException {
    return resultSet.getTime(columnIndex, cal);
  }

  @Override
  public Time getTime(String columnLabel) throws SQLException {
    return resultSet.getTime(columnLabel);
  }

  @Override
  public Time getTime(String columnLabel, Calendar cal) throws SQLException {
    return resultSet.getTime(columnLabel, cal);
  }

  @Override
  public Timestamp getTimestamp(int columnIndex) throws SQLException {
    return resultSet.getTimestamp(columnIndex);
  }

  @Override
  public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
    return resultSet.getTimestamp(columnIndex, cal);
  }

  @Override
  public Timestamp getTimestamp(String columnLabel) throws SQLException {
    return resultSet.getTimestamp(columnLabel);
  }

  @Override
  public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
    return resultSet.getTimestamp(columnLabel, cal);
  }

  @Override
  public int getType() throws SQLException {
    return resultSet.getType();
  }

  @Override
  public URL getURL(int columnIndex) throws SQLException {
    return resultSet.getURL(columnIndex);
  }

  @Override
  public URL getURL(String columnLabel) throws SQLException {
    return resultSet.getURL(columnLabel);
  }

  @Override
  @Deprecated
  public InputStream getUnicodeStream(int columnIndex) throws SQLException {
    return resultSet.getUnicodeStream(columnIndex);
  }

  @Override
  @Deprecated(
  )
  public InputStream getUnicodeStream(String columnLabel) throws SQLException {
    return resultSet.getUnicodeStream(columnLabel);
  }

  @Override
  public SQLWarning getWarnings() throws SQLException {
    return resultSet.getWarnings();
  }

  @Override
  public void insertRow() throws SQLException {
    resultSet.insertRow();
  }

  @Override
  public boolean isAfterLast() throws SQLException {
    return resultSet.isAfterLast();
  }

  @Override
  public boolean isBeforeFirst() throws SQLException {
    return resultSet.isBeforeFirst();
  }

  @Override
  public boolean isClosed() throws SQLException {
    return resultSet.isClosed();
  }

  @Override
  public boolean isFirst() throws SQLException {
    return resultSet.isFirst();
  }

  @Override
  public boolean isLast() throws SQLException {
    return resultSet.isLast();
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return resultSet.isWrapperFor(iface);
  }

  @Override
  public boolean last() throws SQLException {
    return resultSet.last();
  }

  @Override
  public void moveToCurrentRow() throws SQLException {
    resultSet.moveToCurrentRow();
  }

  @Override
  public void moveToInsertRow() throws SQLException {
    resultSet.moveToInsertRow();
  }

  @Override
  public boolean next() throws SQLException {
    return resultSet.next();
  }

  @Override
  public boolean previous() throws SQLException {
    return resultSet.previous();
  }

  @Override
  public void refreshRow() throws SQLException {
    resultSet.refreshRow();
  }

  @Override
  public boolean relative(int var1) throws SQLException {
    return resultSet.relative(var1);
  }

  @Override
  public boolean rowDeleted() throws SQLException {
    return resultSet.rowDeleted();
  }

  @Override
  public boolean rowInserted() throws SQLException {
    return resultSet.rowInserted();
  }

  @Override
  public boolean rowUpdated() throws SQLException {
    return resultSet.rowUpdated();
  }

  @Override
  public void setFetchDirection(int direction) throws SQLException {
    resultSet.setFetchDirection(direction);
  }

  @Override
  public void setFetchSize(int rows) throws SQLException {
    resultSet.setFetchSize(rows);
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    return resultSet.unwrap(iface);
  }

  @Override
  public void updateArray(int columnIndex, Array x) throws SQLException {
    resultSet.updateArray(columnIndex, x);
  }

  @Override
  public void updateArray(String columnLabel, Array x) throws SQLException {
    resultSet.updateArray(columnLabel, x);
  }

  @Override
  public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
    resultSet.updateAsciiStream(columnIndex, x);
  }

  @Override
  public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
    resultSet.updateAsciiStream(columnIndex, x, length);
  }

  @Override
  public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
    resultSet.updateAsciiStream(columnIndex, x, length);
  }

  @Override
  public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
    resultSet.updateAsciiStream(columnLabel, x);
  }

  @Override
  public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
    resultSet.updateAsciiStream(columnLabel, x, length);
  }

  @Override
  public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
    resultSet.updateAsciiStream(columnLabel, x, length);
  }

  @Override
  public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
    resultSet.updateBigDecimal(columnIndex, x);
  }

  @Override
  public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
    resultSet.updateBigDecimal(columnLabel, x);
  }

  @Override
  public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
    resultSet.updateBinaryStream(columnIndex, x);
  }

  @Override
  public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
    resultSet.updateBinaryStream(columnIndex, x, length);
  }

  @Override
  public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
    resultSet.updateBinaryStream(columnIndex, x, length);
  }

  @Override
  public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
    resultSet.updateBinaryStream(columnLabel, x);
  }

  @Override
  public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
    resultSet.updateBinaryStream(columnLabel, x, length);
  }

  @Override
  public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
    resultSet.updateBinaryStream(columnLabel, x, length);
  }

  @Override
  public void updateBlob(int columnIndex, InputStream x) throws SQLException {
    resultSet.updateBlob(columnIndex, x);
  }

  @Override
  public void updateBlob(int columnIndex, InputStream x, long length) throws SQLException {
    resultSet.updateBlob(columnIndex, x, length);
  }

  @Override
  public void updateBlob(int var1, Blob var2) throws SQLException {
    resultSet.updateBlob(var1, var2);
  }

  @Override
  public void updateBlob(String var1, InputStream var2) throws SQLException {
    resultSet.updateBlob(var1, var2);
  }

  @Override
  public void updateBlob(String var1, InputStream var2, long var3) throws SQLException {
    resultSet.updateBlob(var1, var2, var3);
  }

  @Override
  public void updateBlob(String var1, Blob var2) throws SQLException {
    resultSet.updateBlob(var1, var2);
  }

  @Override
  public void updateBoolean(int var1, boolean var2) throws SQLException {
    resultSet.updateBoolean(var1, var2);
  }

  @Override
  public void updateBoolean(String var1, boolean var2) throws SQLException {
    resultSet.updateBoolean(var1, var2);
  }

  @Override
  public void updateByte(int var1, byte var2) throws SQLException {
    resultSet.updateByte(var1, var2);
  }

  @Override
  public void updateByte(String var1, byte var2) throws SQLException {
    resultSet.updateByte(var1, var2);
  }

  @Override
  public void updateBytes(int var1, byte[] var2) throws SQLException {
    resultSet.updateBytes(var1, var2);
  }

  @Override
  public void updateBytes(String var1, byte[] var2) throws SQLException {
    resultSet.updateBytes(var1, var2);
  }

  @Override
  public void updateCharacterStream(int var1, Reader var2) throws SQLException {
    resultSet.updateCharacterStream(var1, var2);
  }

  @Override
  public void updateCharacterStream(int var1, Reader var2, int var3) throws SQLException {
    resultSet.updateCharacterStream(var1, var2, var3);
  }

  @Override
  public void updateCharacterStream(int var1, Reader var2, long var3) throws SQLException {
    resultSet.updateCharacterStream(var1, var2, var3);
  }

  @Override
  public void updateCharacterStream(String var1, Reader var2) throws SQLException {
    resultSet.updateCharacterStream(var1, var2);
  }

  @Override
  public void updateCharacterStream(String var1, Reader var2, int var3) throws SQLException {
    resultSet.updateCharacterStream(var1, var2, var3);
  }

  @Override
  public void updateCharacterStream(String var1, Reader var2, long var3) throws SQLException {
    resultSet.updateCharacterStream(var1, var2, var3);
  }

  @Override
  public void updateClob(int var1, Reader var2) throws SQLException {
    resultSet.updateClob(var1, var2);
  }

  @Override
  public void updateClob(int var1, Reader var2, long var3) throws SQLException {
    resultSet.updateClob(var1, var2, var3);
  }

  @Override
  public void updateClob(int var1, Clob var2) throws SQLException {
    resultSet.updateClob(var1, var2);
  }

  @Override
  public void updateClob(String var1, Reader var2) throws SQLException {
    resultSet.updateClob(var1, var2);
  }

  @Override
  public void updateClob(String var1, Reader var2, long var3) throws SQLException {
    resultSet.updateClob(var1, var2, var3);
  }

  @Override
  public void updateClob(String var1, Clob var2) throws SQLException {
    resultSet.updateClob(var1, var2);
  }

  @Override
  public void updateDate(int var1, Date var2) throws SQLException {
    resultSet.updateDate(var1, var2);
  }

  @Override
  public void updateDate(String var1, Date var2) throws SQLException {
    resultSet.updateDate(var1, var2);
  }

  @Override
  public void updateDouble(int var1, double var2) throws SQLException {
    resultSet.updateDouble(var1, var2);
  }

  @Override
  public void updateDouble(String var1, double var2) throws SQLException {
    resultSet.updateDouble(var1, var2);
  }

  @Override
  public void updateFloat(int var1, float var2) throws SQLException {
    resultSet.updateFloat(var1, var2);
  }

  @Override
  public void updateFloat(String var1, float var2) throws SQLException {
    resultSet.updateFloat(var1, var2);
  }

  @Override
  public void updateInt(int var1, int var2) throws SQLException {
    resultSet.updateInt(var1, var2);
  }

  @Override
  public void updateInt(String var1, int var2) throws SQLException {
    resultSet.updateInt(var1, var2);
  }

  @Override
  public void updateLong(int var1, long var2) throws SQLException {
    resultSet.updateLong(var1, var2);
  }

  @Override
  public void updateLong(String var1, long var2) throws SQLException {
    resultSet.updateLong(var1, var2);
  }

  @Override
  public void updateNCharacterStream(int var1, Reader var2) throws SQLException {
    resultSet.updateNCharacterStream(var1, var2);
  }

  @Override
  public void updateNCharacterStream(int var1, Reader var2, long var3) throws SQLException {
    resultSet.updateNCharacterStream(var1, var2, var3);
  }

  @Override
  public void updateNCharacterStream(String var1, Reader var2) throws SQLException {
    resultSet.updateNCharacterStream(var1, var2);
  }

  @Override
  public void updateNCharacterStream(String var1, Reader var2, long var3) throws SQLException {
    resultSet.updateNCharacterStream(var1, var2, var3);
  }

  @Override
  public void updateNClob(int var1, Reader var2) throws SQLException {
    resultSet.updateNClob(var1, var2);
  }

  @Override
  public void updateNClob(int var1, Reader var2, long var3) throws SQLException {
    resultSet.updateNClob(var1, var2, var3);
  }

  @Override
  public void updateNClob(int var1, NClob var2) throws SQLException {
    resultSet.updateNClob(var1, var2);
  }

  @Override
  public void updateNClob(String var1, Reader var2) throws SQLException {
    resultSet.updateNClob(var1, var2);
  }

  @Override
  public void updateNClob(String var1, Reader var2, long var3) throws SQLException {
    resultSet.updateNClob(var1, var2, var3);
  }

  @Override
  public void updateNClob(String var1, NClob var2) throws SQLException {
    resultSet.updateNClob(var1, var2);
  }

  @Override
  public void updateNString(int var1, String var2) throws SQLException {
    resultSet.updateNString(var1, var2);
  }

  @Override
  public void updateNString(String var1, String var2) throws SQLException {
    resultSet.updateNString(var1, var2);
  }

  @Override
  public void updateNull(int var1) throws SQLException {
    resultSet.updateNull(var1);
  }

  @Override
  public void updateNull(String var1) throws SQLException {
    resultSet.updateNull(var1);
  }

  @Override
  public void updateObject(int var1, Object var2) throws SQLException {
    resultSet.updateObject(var1, var2);
  }

  @Override
  public void updateObject(int var1, Object var2, int var3) throws SQLException {
    resultSet.updateObject(var1, var2, var3);
  }

  @Override
  public void updateObject(String var1, Object var2) throws SQLException {
    resultSet.updateObject(var1, var2);
  }

  @Override
  public void updateObject(String var1, Object var2, int var3) throws SQLException {
    resultSet.updateObject(var1, var2, var3);
  }

  @Override
  public void updateRef(int var1, Ref var2) throws SQLException {
    resultSet.updateRef(var1, var2);
  }

  @Override
  public void updateRef(String var1, Ref var2) throws SQLException {
    resultSet.updateRef(var1, var2);
  }

  @Override
  public void updateRow() throws SQLException {
    resultSet.updateRow();
  }

  @Override
  public void updateRowId(int var1, RowId var2) throws SQLException {
    resultSet.updateRowId(var1, var2);
  }

  @Override
  public void updateRowId(String var1, RowId var2) throws SQLException {
    resultSet.updateRowId(var1, var2);
  }

  @Override
  public void updateSQLXML(int var1, SQLXML var2) throws SQLException {
    resultSet.updateSQLXML(var1, var2);
  }

  @Override
  public void updateSQLXML(String var1, SQLXML var2) throws SQLException {
    resultSet.updateSQLXML(var1, var2);
  }

  @Override
  public void updateShort(int var1, short var2) throws SQLException {
    resultSet.updateShort(var1, var2);
  }

  @Override
  public void updateShort(String var1, short var2) throws SQLException {
    resultSet.updateShort(var1, var2);
  }

  @Override
  public void updateString(int var1, String var2) throws SQLException {
    resultSet.updateString(var1, var2);
  }

  @Override
  public void updateString(String var1, String var2) throws SQLException {
    resultSet.updateString(var1, var2);
  }

  @Override
  public void updateTime(int var1, Time var2) throws SQLException {
    resultSet.updateTime(var1, var2);
  }

  @Override
  public void updateTime(String var1, Time var2) throws SQLException {
    resultSet.updateTime(var1, var2);
  }

  @Override
  public void updateTimestamp(int var1, Timestamp var2) throws SQLException {
    resultSet.updateTimestamp(var1, var2);
  }

  @Override
  public void updateTimestamp(String var1, Timestamp var2) throws SQLException {
    resultSet.updateTimestamp(var1, var2);
  }

  @Override
  public boolean wasNull() throws SQLException {
    return resultSet.wasNull();
  }

  public @Nullable UUID getUUID(@NotNull String columnLabel) throws SQLException {
    Validate.Arg.notNull(columnLabel, "columnLabel");
    if (databaseType == DatabaseType.PostgreSQL) {
      return resultSet.getObject(columnLabel, UUID.class);
    } else {
      byte[] bytes = resultSet.getBytes(columnLabel);
      if (bytes != null) {
        return SQLDatabase.asUUID(bytes);
      } else {
        return null;
      }
    }
  }

  public @Nullable JsonElement getJson(@NotNull String columnLabel) throws SQLException {
    Validate.Arg.notNull(columnLabel, "columnLabel");
    String var5;
    if (databaseType == DatabaseType.H2) {
      byte[] var10000 = resultSet.getBytes(columnLabel);
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      var5 = new String(var10000, StandardCharsets.UTF_8);
    } else {
      var5 = resultSet.getString(columnLabel);
    }

    return AurikaGson.fromString(var5);
  }

}
