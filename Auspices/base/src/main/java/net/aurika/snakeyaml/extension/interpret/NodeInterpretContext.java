package net.aurika.snakeyaml.extension.interpret;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.snakeyaml.engine.v2.nodes.Node;
import top.auspice.utils.unsafe.Fn;

import java.util.Objects;
import java.util.function.Supplier;

public class NodeInterpretContext<V> {

    private @Nullable Node node;
    private @Nullable Supplier<V> ifDifferentType;
    private @Nullable Supplier<V> ifNull;
    private @Nullable Result result;
    private @Nullable V defaultValue;

    public NodeInterpretContext(@Nullable Node node) {
        this(node, null, null);
    }

    public NodeInterpretContext(@Nullable Node node, @Nullable Supplier<V> ifDifferentType, @Nullable Supplier<V> ifNull) {
        this.node = node;
        this.ifDifferentType = ifDifferentType;
        this.ifNull = ifNull;
    }

    public @Nullable Node getNode() {
        return this.node;
    }

    public void setNode(@Nullable Node node) {
        this.node = node;
    }

    public @Nullable Supplier<V> getIfDifferentType() {
        return this.ifDifferentType;
    }

    public void setIfDifferentType(@Nullable Supplier<V> ifDifferentType) {
        this.ifDifferentType = ifDifferentType;
    }

    public @Nullable Supplier<V> getIfNull() {
        return this.ifNull;
    }

    public void setIfNull(@Nullable Supplier<V> supplier) {
        this.ifNull = supplier;
    }

    public @Nullable Result getResult() {
        return this.result;
    }

    public void setResult(@Nullable Result result) {
        this.result = result;
    }

    public @NotNull NodeInterpretContext<V> withNode(@Nullable Node node) {
        this.node = node;
        return this;
    }

    public @NotNull NodeInterpretContext<V> withDefaultValue(@Nullable V defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public @Nullable V asResult(@NotNull Result result, @Nullable V value) {
        Objects.requireNonNull(result, "result");
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
                return this.ifDifferentType.get();
            }
            case NULL: {
                if (this.ifNull == null) {
                    break;
                }
                return this.ifNull.get();
            }
        }
        return value;
    }

    public static <T> @NotNull NodeInterpretContext<T> empty() {
        return new NodeInterpretContext<>(null);
    }

    public static <T> @NotNull NodeInterpretContext<T> nullable() {
        return new NodeInterpretContext<>(null, Fn.nullSupplier(), Fn.nullSupplier());
    }

    public static <T> @NotNull NodeInterpretContext<T> withDefault(T value) {
        return new NodeInterpretContext<>(null, () -> value, () -> value);
    }

    public enum Result {
        SUCCESS,
        DIFFERENT_TYPE,
        NULL,
        COERCED_TYPE
    }
}
