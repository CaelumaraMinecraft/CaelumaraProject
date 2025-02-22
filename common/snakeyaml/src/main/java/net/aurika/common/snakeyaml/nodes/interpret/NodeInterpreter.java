package net.aurika.common.snakeyaml.nodes.interpret;

import net.aurika.common.snakeyaml.nodes.NodesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.snakeyaml.engine.v2.common.ScalarStyle;
import org.snakeyaml.engine.v2.nodes.Node;
import org.snakeyaml.engine.v2.nodes.ScalarNode;
import org.snakeyaml.engine.v2.nodes.SequenceNode;
import net.aurika.auspice.utils.compiler.condition.ConditionCompiler;
import net.aurika.auspice.utils.compiler.math.MathCompiler;
import net.aurika.auspice.utils.string.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static net.aurika.common.snakeyaml.nodes.interpret.NodeInterpretContext.Result.*;

/**
 * 将 {@link Node} 实例解释成需要的 java 对象.
 *
 * @param <T> 所解析成的 java 对象类型.
 */
public interface NodeInterpreter<T> {

    @NotNull NodeInterpreter<Boolean> BOOLEAN = NodeInterpreter::_boolean;
    @NotNull NodeInterpreter<Integer> INT = NodeInterpreter::_integer;
    @NotNull NodeInterpreter<Long> LONG = NodeInterpreter::_long;
    @NotNull NodeInterpreter<Float> FLOAT = NodeInterpreter::_float;
    @NotNull NodeInterpreter<Double> DOUBLE = NodeInterpreter::_double;
    @NotNull NodeInterpreter<String> STRICT_STRING = NodeInterpreter::strict_string;
    @NotNull NodeInterpreter<String> STRING = NodeInterpreter::string;
    @NotNull NodeInterpreter<MathCompiler.Expression> MATH = NodeInterpreter::math_expr;
    @NotNull NodeInterpreter<ConditionCompiler.LogicalOperand> CONDITION = NodeInterpreter::condition;
    @NotNull NodeInterpreter<List<Integer>> INT_LIST = NodeInterpreter::integer_list;
    @NotNull NodeInterpreter<List<String>> STRING_LIST = NodeInterpreter::string_list;

    T parse(@NotNull NodeInterpretContext<T> context);

    default T parse(@Nullable Node node) {
        return this.parse(new NodeInterpretContext<>(node));
    }

    /**
     * 标点严格, 且不使用 {@link NodesKt#parsed(Node)}
     */
    private static String strict_string(NodeInterpretContext<String> context) {
        Checker.Arg.notNull(context, "context");
        Node node = context.getNode();
        if (node == null) {
            return context.asResult(NULL, null);
        } else {
            if (node instanceof ScalarNode scalarNode) {
                if (scalarNode.getScalarStyle() == ScalarStyle.PLAIN) {
                    return context.asResult(DIFFERENT_TYPE, null);
                }
                return scalarNode.getValue();
            } else {
                return context.asResult(DIFFERENT_TYPE, null);
            }
        }
    }

    /**
     * 标点不严格, 且当 {@link NodesKt#parsed(Node)} 返回 {@link CharSequence} 时优先使用
     */
    private static String string(NodeInterpretContext<String> context) {
        Objects.requireNonNull(context, "context");
        Node node = context.getNode();
        if (node == null) {
            return context.asResult(NULL, null);
        } else {
            if (node instanceof ScalarNode scalarNode) {
                if (NodesKt.parsed(node) instanceof CharSequence chSeqParsed) {
                    return context.asResult(SUCCESS, chSeqParsed.toString());
                }
                return scalarNode.getValue();
            } else {
                return context.asResult(DIFFERENT_TYPE, null);
            }
        }
    }

    private static List<Integer> integer_list(NodeInterpretContext<List<Integer>> context) {
        Objects.requireNonNull(context, "context");
        Node node = context.getNode();
        if (node == null) {
            return context.asResult(NULL, new ArrayList<>());
        } else {
            if (!(node instanceof SequenceNode seqNode)) {
                return context.asResult(DIFFERENT_TYPE, new ArrayList<>());
            } else {
                List<Integer> var5 = (new ArrayList<>(seqNode.getValue().size()));

                for (Node elementNode : seqNode.getValue()) {
                    if (NodesKt.parsed(elementNode) instanceof Number) {
                        Object var10001 = NodesKt.parsed(elementNode);
                        Objects.requireNonNull(var10001);
                        var5.add(((Number) var10001).intValue());
                    }
                }

                return var5;
            }
        }
    }

    private static List<String> string_list(NodeInterpretContext<List<String>> context) {
        Objects.requireNonNull(context, "context");
        Node node = context.getNode();
        if (node == null) {
            return context.asResult(NULL, new ArrayList<>());
        } else {
            if (!(node instanceof SequenceNode seqNode)) {
                return node instanceof ScalarNode ? Strings.split(((ScalarNode) node).getValue(), '\n', true) : context.asResult(DIFFERENT_TYPE, new ArrayList<>());
            } else {
                List<String> var6 = (new ArrayList<>(seqNode.getValue().size()));

                for (Node var2 : seqNode.getValue()) {
                    String var7 = STRING.parse(new NodeInterpretContext<>(var2));
                    if (var7 != null) {
                        var6.add(var7);
                    }
                }

                return var6;
            }
        }
    }

    private static MathCompiler.Expression math_expr(NodeInterpretContext<MathCompiler.Expression> context) {
        Objects.requireNonNull(context, "context");
        Node node = context.getNode();
        if (node == null) {
            return context.asResult(NULL, MathCompiler.DEFAULT_VALUE);
        } else {
            if (!(node instanceof ScalarNode)) {
                return context.asResult(DIFFERENT_TYPE, MathCompiler.DEFAULT_VALUE);
            } else {
                if (!(NodesKt.parsed(node) instanceof MathCompiler.Expression)) {
                    ScalarNode var2 = (ScalarNode) node;
                    NodesKt.cacheConstructed(node, MathCompiler.compile(var2.getValue()));
                }

                Object parsed = NodesKt.parsed(node);

                Intrinsics.checkNotNull(parsed);
                return (MathCompiler.Expression) parsed;
            }
        }
    }

    private static Float _float(NodeInterpretContext<Float> context) {
        Objects.requireNonNull(context, "context");
        Node node = context.getNode();
        if (node == null) {
            return context.asResult(NULL, 0.0F);
        } else {
            if (!((NodesKt.parsed(node)) instanceof Number)) {
                return context.asResult(DIFFERENT_TYPE, 0.0F);
            } else {
                Object parsed = NodesKt.parsed(node);
                Objects.requireNonNull(parsed);
                return ((Number) parsed).floatValue();
            }
        }
    }

    private static Long _long(NodeInterpretContext<Long> context) {
        Objects.requireNonNull(context, "context");
        Node node = context.getNode();
        if (node == null) {
            return context.asResult(NULL, 0L);
        } else {
            if (!(NodesKt.parsed(node) instanceof Number)) {
                return context.asResult(DIFFERENT_TYPE, 0L);
            } else {
                Object parsed = NodesKt.parsed(node);
                Objects.requireNonNull(parsed);
                return ((Number) parsed).longValue();
            }
        }
    }

    private static Double _double(NodeInterpretContext<Double> var0) {
        Objects.requireNonNull(var0, "context");
        Node node = var0.getNode();
        if (node == null) {
            return var0.asResult(NULL, 0.0);
        } else {
            if (!(NodesKt.parsed(node) instanceof Number)) {
                return var0.asResult(DIFFERENT_TYPE, 0.0);
            } else {
                Object parsed = NodesKt.parsed(node);
                Objects.requireNonNull(parsed);
                return ((Number) parsed).doubleValue();
            }
        }
    }

    private static Boolean _boolean(NodeInterpretContext<Boolean> context) {
        Objects.requireNonNull(context, "context");
        Node node = context.getNode();
        if (node == null) {
            return context.asResult(NULL, Boolean.FALSE);
        } else {
            if (!(NodesKt.parsed(node) instanceof Boolean)) {
                return context.asResult(DIFFERENT_TYPE, Boolean.FALSE);
            } else {
                Object parsed = NodesKt.parsed(node);
                Objects.requireNonNull(parsed);
                return (Boolean) parsed;
            }
        }
    }

    private static Integer _integer(NodeInterpretContext<Integer> context) {
        Objects.requireNonNull(context, "context");
        Node node = context.getNode();
        if (node == null) {
            return context.asResult(NULL, 0);
        } else {
            if (!(NodesKt.parsed(node) instanceof Number)) {
                return context.asResult(DIFFERENT_TYPE, 0);
            } else {
                Object parsed = NodesKt.parsed(node);
                Objects.requireNonNull(parsed);
                return ((Number) parsed).intValue();
            }
        }
    }

    private static ConditionCompiler.LogicalOperand condition(NodeInterpretContext<ConditionCompiler.LogicalOperand> context) {
        Objects.requireNonNull(context, "context");
        Node node = context.getNode();
        if (node == null) {
            return context.asResult(NULL, null);
        } else {
            if (!(node instanceof ScalarNode)) {
                return context.asResult(DIFFERENT_TYPE, null);
            } else {
                if (!(NodesKt.parsed(node) instanceof ConditionCompiler.LogicalOperand)) {
                    ScalarNode var2 = (ScalarNode) node;
                    NodesKt.cacheConstructed(node, ConditionCompiler.compile(var2.getValue()).evaluate());
                }
                Object parsed = NodesKt.parsed(node);
                Objects.requireNonNull(parsed);
                return (ConditionCompiler.LogicalOperand) parsed;
            }
        }
    }
}
