package top.auspice.config.yaml.snakeyaml;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.snakeyaml.engine.v2.nodes.Node;
import top.auspice.utils.internal.Fn;

import java.util.Objects;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class NodeInterpretContext<V> {
    @Nullable
    private Node node;
    @Nullable
    private Supplier<V> ifDifferentType;
    @Nullable
    private Supplier<V> ifNull;
    @Nullable
    private Result result;
    @Nullable
    private V defaultValue;

    public NodeInterpretContext(@Nullable Node node, @Nullable Supplier<V> supplier, @Nullable Supplier<V> supplier2) {
        this.node = node;
        this.ifDifferentType = supplier;
        this.ifNull = supplier2;
    }

    @Nullable
    public Node getNode() {
        return this.node;
    }

    public void setNode(@Nullable Node node) {
        this.node = node;
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
    public Result getResult() {
        return this.result;
    }

    public void setResult(@Nullable Result result) {
        this.result = result;
    }

    public NodeInterpretContext(@Nullable Node node) {
        this(node, null, null);
    }

    @NotNull
    public NodeInterpretContext<V> withNode(@Nullable Node node) {
        this.node = node;
        return this;
    }

    @NotNull
    public NodeInterpretContext<V> default$(@Nullable V v) {
        this.defaultValue = v;
        return this;
    }

    @Nullable
    public V asResult(@NotNull Result result, @Nullable V v) {
        Objects.requireNonNull(result);
        this.result = result;
        V v2 = this.defaultValue;
        if (v2 == null) {
            v2 = v;
        }
        v = v2;
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
        return v;
    }

    public static <V> V asResult$default(NodeInterpretContext<V> context, Result result, V object, int n, Object object2) {
        if ((n & 2) != 0) {
            object = context.defaultValue;
        }
        return context.asResult(result, object);
    }

    @NotNull
    public static <T> NodeInterpretContext<T> empty() {
        return new NodeInterpretContext<>(null);
    }

    @NotNull
    public static <T> NodeInterpretContext<T> nullable() {
        return new NodeInterpretContext<>(null, Fn.nullSupplier(), Fn.nullSupplier());
    }

    @NotNull
    public static <T> NodeInterpretContext<T> withDefault(T t) {
        return new NodeInterpretContext<>(null, () -> t, () -> t);
    }

    public enum Result {
        SUCCESS,
        DIFFERENT_TYPE,
        NULL,
        COERCED_TYPE
    }


}