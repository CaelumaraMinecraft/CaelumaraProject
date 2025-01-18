package top.auspice.data.database.dataprovider;

import org.jetbrains.annotations.NotNull;

public interface MappingSetterHandler<K, V> {
    void map(K var1, @NotNull MappedIdSetter var2, V var3);
}
