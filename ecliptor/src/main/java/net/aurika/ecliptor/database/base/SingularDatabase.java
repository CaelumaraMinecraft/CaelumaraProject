package net.aurika.ecliptor.database.base;

import net.aurika.ecliptor.api.DataObject;

public interface SingularDatabase<T extends DataObject> extends Database<T> {

  T load();

  boolean hasData();

}
