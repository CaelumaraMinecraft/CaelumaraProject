package net.aurika.data.api.bundles;

import org.jetbrains.annotations.NotNull;

public interface BundledDataLike {
    /**
     * 返回这个对象的固定数据结构及其值
     */
    @NotNull BundledData simpleData();

    @NotNull DataBundleSchema<? extends BundledDataLike> simpleDataTemplate();
}
