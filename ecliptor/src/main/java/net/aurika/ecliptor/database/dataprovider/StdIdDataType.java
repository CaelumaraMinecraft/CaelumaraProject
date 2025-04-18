package net.aurika.ecliptor.database.dataprovider;

import kotlin.jvm.internal.Intrinsics;
import net.aurika.auspice.constants.location.SimpleChunkLocation;
import net.aurika.auspice.utils.unsafe.uuid.FastUUID;
import net.aurika.ecliptor.database.sql.statements.getters.SimpleResultSetQuery;
import net.aurika.ecliptor.database.sql.statements.setters.SimplePreparedStatement;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

public class StdIdDataType {

  public static final @NotNull UUIDKey UUID = new UUIDKey("id");
  public static final @NotNull SimpleChunkLocationKey SIMPLE_CHUNK_LOCATION = new SimpleChunkLocationKey("id");

  private StdIdDataType() {
  }

  public static class NoKey extends IdDataTypeHandler<String> {

    @NotNull
    public static final NoKey INSTANCE = new NoKey();

    private NoKey() {
      super("NoKey", String.class, new String[0]);
    }

    public void setSQL(@NotNull SimplePreparedStatement var1, @NotNull String var2) {
      throw new UnsupportedOperationException();
    }

    @NotNull
    public String fromSQL(@NotNull SimpleResultSetQuery var1) {
      throw new UnsupportedOperationException();
    }

    @NotNull
    public String fromString(@NotNull String str) {
      throw new UnsupportedOperationException();
    }

    @NotNull
    public String toString(@NotNull String var1) {
      throw new UnsupportedOperationException();
    }

  }

  public static class UUIDKey extends IdDataTypeHandler<UUID> {

    public UUIDKey(@NotNull String var1) {
      super(var1, UUID.class, new String[0]);
    }

    public void setSQL(@NotNull SimplePreparedStatement var1, @NotNull UUID var2) {
      Objects.requireNonNull(var1, "");
      Objects.requireNonNull(var2, "");
      var1.setUUID(this.getPrefix$core(), var2);
    }

    @NotNull
    public UUID fromSQL(@NotNull SimpleResultSetQuery var1) throws SQLException {
      Objects.requireNonNull(var1, "");
      UUID var10000 = var1.getUUID(this.getPrefix$core());
      Intrinsics.checkNotNull(var10000);
      return var10000;
    }

    @NotNull
    public UUID fromString(@NotNull String str) {
      Objects.requireNonNull(str, "");
      UUID var10000 = FastUUID.fromString(str);
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      return var10000;
    }

    @NotNull
    public String toString(@NotNull UUID var1) {
      Objects.requireNonNull(var1, "");
      String var10000 = FastUUID.toString(var1);
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      return var10000;
    }

  }

  public static class SimpleChunkLocationKey extends IdDataTypeHandler<SimpleChunkLocation> {

    public SimpleChunkLocationKey(@NotNull String var1) {
      super(var1, SimpleChunkLocation.class, new String[]{"world", "x", "z"});
    }

    public void setSQL(@NotNull SimplePreparedStatement var1, @NotNull SimpleChunkLocation var2) {
      Objects.requireNonNull(var1);
      Objects.requireNonNull(var2);
      SimplePreparedStatement var3;
      (var3 = var1).setString(this.getPrefix$core() + "_world", var2.getWorld());
      var3.setInt(this.getPrefix$core() + "_x", var2.getX());
      var3.setInt(this.getPrefix$core() + "_z", var2.getZ());
    }

    @NotNull
    public SimpleChunkLocation fromSQL(@NotNull SimpleResultSetQuery var1) throws SQLException {
      Objects.requireNonNull(var1);
      return new SimpleChunkLocation(
          var1.getString(this.getPrefix$core() + "_world"), var1.getInt(this.getPrefix$core() + "_x"),
          var1.getInt(this.getPrefix$core() + "_z")
      );
    }

    @NotNull
    public SimpleChunkLocation fromString(@NotNull String str) {
      Objects.requireNonNull(str);
      SimpleChunkLocation var10000 = SimpleChunkLocation.fromString(str);
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      return var10000;
    }

    @NotNull
    public String toString(@NotNull SimpleChunkLocation var1) {
      Objects.requireNonNull(var1, "");
      String var10000 = var1.toString();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      return var10000;
    }

  }

}
