package net.aurika.data.api.structure;

import org.jetbrains.annotations.NotNull;

public interface SimpleDataObject {
    /**
     * 返回这个对象的固定数据结构及其值
     */
    @NotNull SimpleData simpleData();

    @NotNull SimpleDataObjectTemplate<? extends SimpleDataObject> simpleDataTemplate();
}
