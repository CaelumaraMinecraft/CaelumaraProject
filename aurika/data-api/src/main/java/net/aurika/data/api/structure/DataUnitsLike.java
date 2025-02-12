package net.aurika.data.api.structure;

import org.jetbrains.annotations.NotNull;

public interface DataUnitsLike {
    /**
     * 返回这个对象的固定数据结构及其值
     */
    @NotNull DataUnits simpleData();

    @NotNull SimpleDataMapObjectTemplate<? extends DataUnitsLike> simpleDataTemplate();
}
