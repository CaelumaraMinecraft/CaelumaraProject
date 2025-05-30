package net.aurika.ecliptor.database.sql.base;

import kotlin.jvm.internal.Intrinsics;
import net.aurika.ecliptor.api.DataObject;
import net.aurika.ecliptor.database.DatabaseType;
import net.aurika.ecliptor.database.base.SingularDatabase;
import net.aurika.ecliptor.database.sql.SQLDataGetterProvider;
import net.aurika.ecliptor.database.sql.connection.SQLConnectionProvider;
import net.aurika.ecliptor.database.sql.statements.getters.SimpleResultSetQuery;
import net.aurika.ecliptor.handler.SingularDataHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SingularSQLDatabase<T extends DataObject> extends SQLDatabase<T> implements SingularDatabase<T> {

  @NotNull
  private final SingularDataHandler<T> dataHandler;

  public SingularSQLDatabase(@NotNull DatabaseType var1, @NotNull String var2, @NotNull SingularDataHandler<T> var3, @NotNull SQLConnectionProvider var4) {
    super(var1, var2, var4);
    Intrinsics.checkNotNullParameter(var3, "");
    this.dataHandler = var3;
  }

  protected @NotNull SingularDataHandler<T> getDataHandler() {
    return this.dataHandler;
  }

  public @Nullable T load() {
    String var1 = "SELECT * FROM `" + this.getTable() + '`';
    SQLDatabase<T> var2 = this;
    var1 = var2.handleQuery(var1);

    try {
      Connection var3 = var2.getConnection();
      T var42;
      PreparedStatement var41 = var3.prepareStatement(var1);
      T var44;
      ResultSet var43 = var41.executeQuery();
      T var10000;
      label182:
      {
        label181:
        {
          T var46;
          ResultSet var9;
          if (!(var9 = var43).next()) {
            break label181;
          }

          DatabaseType var10002 = this.getDatabaseType();
          String var10003 = this.getTable();
          DatabaseType var10009 = this.getDatabaseType();
          Intrinsics.checkNotNull(var9);
          SQLDataGetterProvider var45 = new SQLDataGetterProvider(
              var10002, var10003, null, false, false, new SimpleResultSetQuery(var10009, var9));
          var46 = this.getDataHandler().load(var45);

          var10000 = var46;
          break label182;
        }

        var10000 = null;
      }
      var44 = var10000;
      var42 = var44;

      return var42;
    } catch (Throwable var40) {
      throw new RuntimeException(
          "Error while handling data with query: " + var1 + " with " + var2.getConnectionProvider().getMetaString(),
          var40
      );
    }
  }

  public boolean hasData() {
    String var1 = "SELECT 1 FROM `" + this.getTable() + '`';
    SQLDatabase<T> var2 = this;
    var1 = var2.handleQuery(var1);

    try {
      Connection var3 = var2.getConnection();
      Throwable var4 = null;
      boolean var13 = false;

      boolean var26;
      try {
        var13 = true;
        PreparedStatement var25 = var3.prepareStatement(var1);
        Throwable var6 = null;
        boolean var19 = false;

        try {
          var19 = true;
          var26 = var25.executeQuery().next();
          var19 = false;
        } catch (Throwable var20) {
          var6 = var20;
          throw var20;
        } finally {
          if (var19) {
            AutoCloseableUtils.closeFinally(var25, var6);
          }
        }

        AutoCloseableUtils.closeFinally(var25, null);
        var13 = false;
      } catch (Throwable var22) {
        var4 = var22;
        throw var22;
      } finally {
        if (var13) {
          AutoCloseableUtils.closeFinally(var3, var4);
        }
      }

      AutoCloseableUtils.closeFinally(var3, null);
      return var26;
    } catch (Throwable var24) {
      throw new RuntimeException(
          "Error while handling data with query: " + var1 + " with " + var2.getConnectionProvider().getMetaString(),
          var24
      );
    }
  }

}
