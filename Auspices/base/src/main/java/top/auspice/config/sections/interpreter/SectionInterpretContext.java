package top.auspice.config.sections.interpreter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.config.sections.ConfigSection;
import top.auspice.utils.unsafe.Fn;

import java.util.Objects;
import java.util.function.Supplier;

public class SectionInterpretContext<V> {
    @Nullable
    private ConfigSection section;
    @Nullable
    private Supplier<V> ifDifferentType;
    @Nullable
    private Supplier<V> ifNull;
    @Nullable
    private Result result;
    @Nullable
    private V defaultValue;

    public SectionInterpretContext(@Nullable ConfigSection section, @Nullable Supplier<V> whenDifferentType, @Nullable Supplier<V> whenNull) {
        this.section = section;
        this.ifDifferentType = whenDifferentType;
        this.ifNull = whenNull;
    }

    @Nullable
    public ConfigSection getSection() {
        return this.section;
    }

    public void setSection(@Nullable ConfigSection section) {
        this.section = section;
    }

    @Nullable
    public Supplier<V> getIfDifferentType() {
        return this.ifDifferentType;
    }

    public void setIfDifferentType(@Nullable Supplier<V> ifDifferentType) {
        this.ifDifferentType = ifDifferentType;
    }

    @Nullable
    public Supplier<V> getIfNull() {
        return this.ifNull;
    }

    public void setIfNull(@Nullable Supplier<V> supplier) {
        this.ifNull = supplier;
    }

    @Nullable
    public SectionInterpretContext.Result getResult() {
        return this.result;
    }

    public void setResult(@Nullable Result result) {
        this.result = result;
    }

    public SectionInterpretContext(@Nullable ConfigSection section) {
        this(section, null, null);
    }

    @NotNull
    public SectionInterpretContext<V> withSection(@Nullable ConfigSection section) {
        this.section = section;
        return this;
    }

    @NotNull
    public SectionInterpretContext<V> withDefaultValue(@Nullable V v) {
        this.defaultValue = v;
        return this;
    }

    /**
     * 将一个对象作为本次配置节翻译的结果
     */
    @Nullable
    public V asResult(@NotNull Result result, @Nullable V value) {
        Objects.requireNonNull(result);
        this.result = result;
        V v2 = this.defaultValue;
        if (v2 == null) {
            v2 = value;
        }
        value = v2;
        switch (result) {
            case DIFFERENT_TYPE: {
                if (this.ifDifferentType == null) {
                    break;
                }
                Supplier<V> supplier = this.ifDifferentType;
                Objects.requireNonNull(supplier);
                return supplier.get();
            }
            case NULL: {
                if (this.ifNull == null) {
                    break;
                }
                Supplier<V> supplier = this.ifNull;
                Objects.requireNonNull(supplier);
                return supplier.get();
            }
        }
        return value;
    }

    @NotNull
    public static <T> SectionInterpretContext<T> empty() {
        return new SectionInterpretContext<>(null);
    }

    @NotNull
    public static <T> SectionInterpretContext<T> nullable() {
        return new SectionInterpretContext<>(null, Fn.nullSupplier(), Fn.nullSupplier());
    }

    @NotNull
    public static <T> SectionInterpretContext<T> withDefault(T t) {
        return new SectionInterpretContext<>(null, () -> t, () -> t);
    }

    public enum Result {
        /**
         * 成功
         */
        SUCCESS,
        /**
         * 类型完全不相容, 无法转换
         */
        DIFFERENT_TYPE,
        /**
         * 空
         */
        NULL,
        /**
         * 强制转换了类型
         */
        COERCED_TYPE
    }

}
