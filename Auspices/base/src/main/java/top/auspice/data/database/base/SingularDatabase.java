package top.auspice.data.database.base;

import top.auspice.data.object.DataObject;

public interface SingularDatabase<T extends DataObject.Impl> extends Database<T> {
    T load();

    boolean hasData();
}
