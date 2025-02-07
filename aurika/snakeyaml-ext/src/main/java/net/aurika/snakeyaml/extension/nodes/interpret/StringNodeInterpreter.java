package net.aurika.snakeyaml.extension.nodes.interpret;

import net.aurika.snakeyaml.extension.nodes.NodesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.snakeyaml.engine.v2.common.ScalarStyle;
import org.snakeyaml.engine.v2.nodes.Node;
import org.snakeyaml.engine.v2.nodes.ScalarNode;

import java.util.Set;
import java.util.function.Supplier;

import static net.aurika.snakeyaml.extension.nodes.interpret.NodeInterpretContext.Result.DIFFERENT_TYPE;
import static net.aurika.snakeyaml.extension.nodes.interpret.NodeInterpretContext.Result.SUCCESS;
import static org.snakeyaml.engine.v2.common.ScalarStyle.*;

public class StringNodeInterpreter extends AbstractNodeInterpreter<String> implements NodeInterpreter<String> {

    public static final @Unmodifiable Set<ScalarStyle> ALL_STYLES = Set.of(ScalarStyle.values());
    public static final @Unmodifiable Set<ScalarStyle> NO_PLAIN_STYLES = Set.of(DOUBLE_QUOTED, SINGLE_QUOTED, LITERAL, FOLDED, JSON_SCALAR_STYLE);

    private final @NotNull Set<ScalarStyle> allowedScalarStyles;

    private static final Supplier<String> DEFAULT_WHEN_NULL_NODE = () -> null;

    public StringNodeInterpreter(boolean priorUseCachedValue, @NotNull Set<ScalarStyle> allowedScalarStyles) {
        super(priorUseCachedValue);
        this.allowedScalarStyles = allowedScalarStyles;
    }

    @Override
    public String parse0(@NotNull NodeInterpretContext<String> context) {
        Node node = context.getNode();
        assert node != null;
        if (priorUseCachedValue) {
            Object parsed = NodesKt.getParsed(node);
            if (parsed instanceof CharSequence charSequence) {
                return context.asResult(SUCCESS, charSequence.toString());
            }
        }
        if (node instanceof ScalarNode scalarNode) {
            if (allowedScalarStyles.contains(scalarNode.getScalarStyle())) {
                return context.asResult(SUCCESS, scalarNode.getValue());
            } else {
                return context.asResult(DIFFERENT_TYPE, null);
            }
        } else {
            return context.asResult(DIFFERENT_TYPE, null);
        }
    }

    public @NotNull Set<ScalarStyle> allowedScalarStyles() {
        return allowedScalarStyles;
    }
}
