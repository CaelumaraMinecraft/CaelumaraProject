package net.aurika.common.snakeyaml.nodes.interpret;

import net.aurika.util.Checker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.snakeyaml.engine.v2.nodes.Node;
import top.auspice.utils.unsafe.Fn;

import java.util.function.Supplier;

import static net.aurika.common.snakeyaml.nodes.interpret.NodeInterpretContext.Result.DIFFERENT_TYPE;
import static net.aurika.common.snakeyaml.nodes.interpret.NodeInterpretContext.Result.NULL;

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
        Checker.Arg.notNull(result, "result");
        this.result = result;
        if (value == null) {
            value = defaultValue;
        }
        if (result.equals(DIFFERENT_TYPE)) {
            if (this.ifDifferentType == null) return value;
            return this.ifDifferentType.get();
        } else if (result.equals(NULL)) {
            if (this.ifNull == null) return value;
            return this.ifNull.get();
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

    public interface Result {
        Result SUCCESS = StdResults.SUCCESS;
        Result COERCED_TYPE = StdResults.COERCED_TYPE;
        Result NULL = StdResults.NULL;
        Result DIFFERENT_TYPE = StdResults.DIFFERENT_TYPE;

        @NotNull String getName();

        boolean isSuccessful();
    }

    enum StdResults implements Result {

        SUCCESS(true),
        COERCED_TYPE(true),
        NULL(false),
        DIFFERENT_TYPE(false),
        ;

        private final boolean isSuccess;

        StdResults(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        @Override
        public @NotNull String getName() {
            return name();
        }

        @Override
        public boolean isSuccessful() {
            return isSuccess;
        }
    }
}
