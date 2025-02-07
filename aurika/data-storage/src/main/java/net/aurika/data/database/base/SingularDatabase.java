package net.aurika.data.database.base;

import net.aurika.data.object.DataObject;

public interface SingularDatabase<T extends DataObject> extends Database<T> {
    T load();

    boolean hasData();
}
