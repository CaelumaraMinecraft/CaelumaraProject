package net.aurika.util.snakeyaml.nodes.interpret;

import org.jetbrains.annotations.NotNull;
import org.snakeyaml.engine.v2.nodes.Node;

import static net.aurika.util.snakeyaml.nodes.interpret.NodeInterpretContext.Result.NULL;

abstract class AbstractNodeInterpreter<T> implements NodeInterpreter<T> {

    protected final boolean priorUseCachedValue;

    public AbstractNodeInterpreter(boolean priorUseCachedValue) {
        this.priorUseCachedValue = priorUseCachedValue;
    }

    public boolean priorUseCachedValue() {
        return priorUseCachedValue;
    }

    @Override
    public T parse(@NotNull NodeInterpretContext<T> context) {
        Node node = context.getNode();
        if (node == null) {
            return context.asResult(NULL, null);
        } else {
            return parse0(context);
        }
    }

    public abstract T parse0(@NotNull NodeInterpretContext<T> context);
}
