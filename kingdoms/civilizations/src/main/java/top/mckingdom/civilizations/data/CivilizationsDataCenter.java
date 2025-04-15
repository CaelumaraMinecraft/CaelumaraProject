package top.mckingdom.civilizations.data;

import org.kingdoms.constants.base.KingdomsObject;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.data.centers.DataCenter;
import org.kingdoms.data.database.base.KingdomsDatabase;
import org.kingdoms.data.database.sql.DatabaseType;
import org.kingdoms.data.handlers.abstraction.DataHandler;

import java.time.Duration;

public class CivilizationsDataCenter extends DataCenter {
  public CivilizationsDataCenter(Namespace namespace, DatabaseType databaseType, Duration duration, boolean b, boolean b1, boolean b2) {
    super(namespace, databaseType, duration, b, b1, b2);
  }

  @Override
  protected <T extends KingdomsObject> KingdomsDatabase<T> constructDatabase0(String s, String s1, DataHandler<T> dataHandler) {
    return null;
  }

}
