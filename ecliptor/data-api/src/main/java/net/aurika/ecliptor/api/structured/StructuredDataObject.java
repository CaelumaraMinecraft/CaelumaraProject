package net.aurika.ecliptor.api.structured;

import org.jetbrains.annotations.NotNull;

/**
 * <blockquote><pre>
 *     String -> int
 *               long
 *               float
 *               double
 *               boolean
 *               String
 * </pre></blockquote>
 */
public interface StructuredDataObject {
    /**
     * 返回这个对象的固定数据结构及其值
     */
    @NotNull StructuredData structuredData();

    @NotNull DataStructSchema<? extends StructuredDataObject> DataStructSchema();
}
