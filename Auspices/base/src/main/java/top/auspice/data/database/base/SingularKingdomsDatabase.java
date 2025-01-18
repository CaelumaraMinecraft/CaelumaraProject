package top.auspice.data.database.base;

import top.auspice.constants.base.AuspiceObject;

public interface SingularKingdomsDatabase<T extends AuspiceObject> extends KingdomsDatabase<T> {
    T load();

    boolean hasData();
}
