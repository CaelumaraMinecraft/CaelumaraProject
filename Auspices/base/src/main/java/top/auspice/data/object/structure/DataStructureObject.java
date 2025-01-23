package top.auspice.data.object.structure;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Map;

public interface DataStructureObject {
    /**
     * 返回这个对象的固定数据结构
     * <p>
     * 返回的 Map 的值应为 {@linkplain Boolean}, {@linkplain Integer}, {@linkplain Long}, {@linkplain Float}, {@linkplain Double}, {@linkplain String}, {@linkplain DataStructureObject} 类型
     *
     * @return 这个对象的固定数据结构
     */
    @NonNull
    Map<String, Object> getData();
}
