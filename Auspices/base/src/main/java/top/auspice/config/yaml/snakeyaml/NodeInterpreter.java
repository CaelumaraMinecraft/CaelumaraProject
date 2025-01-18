package top.auspice.config.yaml.snakeyaml;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.snakeyaml.engine.v2.api.LoadSettings;
import org.snakeyaml.engine.v2.common.FlowStyle;
import org.snakeyaml.engine.v2.common.ScalarStyle;
import org.snakeyaml.engine.v2.constructor.BaseConstructor;
import org.snakeyaml.engine.v2.constructor.StandardConstructor;
import org.snakeyaml.engine.v2.nodes.Node;
import org.snakeyaml.engine.v2.nodes.ScalarNode;
import org.snakeyaml.engine.v2.nodes.SequenceNode;
import org.snakeyaml.engine.v2.nodes.Tag;
import org.snakeyaml.engine.v2.resolver.BaseScalarResolver;
import org.snakeyaml.engine.v2.resolver.FailsafeScalarResolver;
import top.auspice.config.yaml.snakeyaml.NodeInterpretContext.Result;
import top.auspice.config.yaml.snakeyaml.node.NodesKt;
import top.auspice.utils.compiler.condition.ConditionCompiler;
import top.auspice.utils.compiler.math.MathCompiler;
import top.auspice.utils.string.Strings;

import java.util.*;

public interface NodeInterpreter<T> {
    @NotNull
    BaseScalarResolver DEFAULT_RESOLVER = new FailsafeScalarResolver();   //todo choose
    @NotNull
    BaseConstructor DEFAULT_CTOR = new StandardConstructor(LoadSettings.builder().build());

    @NotNull
    NodeInterpreter<Boolean> BOOLEAN = NodeInterpreter::b;
    @NotNull
    NodeInterpreter<Integer> INT = NodeInterpreter::i;
    @NotNull
    NodeInterpreter<Long> LONG = NodeInterpreter::l;
    @NotNull
    NodeInterpreter<Float> FLOAT = NodeInterpreter::f;
    @NotNull
    NodeInterpreter<Double> DOUBLE = NodeInterpreter::d;
    @NotNull
    NodeInterpreter<String> STRING = NodeInterpreter::s;
    @NotNull
    NodeInterpreter<MathCompiler.Expression> MATH = NodeInterpreter::math;
    @NotNull
    NodeInterpreter<ConditionCompiler.LogicalOperand> CONDITION = NodeInterpreter::condition;
    @NotNull
    NodeInterpreter<List<Integer>> INT_LIST = NodeInterpreter::il;
    @NotNull
    NodeInterpreter<List<String>> STRING_LIST = NodeInterpreter::sl;


    T parse(@NotNull NodeInterpretContext<T> context);

    default T parse(@Nullable Node node) {
        return this.parse(new NodeInterpretContext<>(node));
    }


    @NotNull
    static Node nodeOfObject(@Nullable Object obj) {
        if (obj == null) {
            return new ScalarNode(Tag.NULL, "~", ScalarStyle.PLAIN);
        }
        if (obj instanceof Node) {
            return (Node)obj;
        }
        if (!(obj instanceof Collection)) {
            if (obj instanceof Map) {
                throw new UnsupportedOperationException("Mapping from objects");
            } else {
                ScalarNode newScaNode = new ScalarNode(NodeInterpreter.DEFAULT_RESOLVER.resolve(obj.toString(), true), obj.toString(), ScalarStyle.DOUBLE_QUOTED);
                NodesKt.cacheConstructed(newScaNode, NodeInterpreter.DEFAULT_CTOR.constructSingleDocument(Optional.of(newScaNode)));
                return newScaNode;
            }
        } else {
            List<Node> var2 = new ArrayList<>(((Collection<?>)obj).size());

            for (Object var3 : (Collection<?>) obj) {
                var2.add(nodeOfObject(var3));
            }

            return new SequenceNode(Tag.SEQ, var2, FlowStyle.AUTO);
        }
    }

    private static String s(NodeInterpretContext<String> context) {
        Objects.requireNonNull(context, "");
        Node node = context.getNode();
        if (node == null) {
            return context.asResult(Result.NULL, null);
        } else {
            Node var1 = node;
            if (!(node instanceof ScalarNode)) {
                return context.asResult(Result.DIFFERENT_TYPE, null);
            } else if (NodesKt.getParsed(var1) == null) {
                return null;
            } else {
                node = context.getNode();
                Objects.requireNonNull(node);
                return ((ScalarNode) node).getValue();
            }
        }
    }

    private static List<Integer> il(NodeInterpretContext<List<Integer>> context) {
        Objects.requireNonNull(context);
        Node node = context.getNode();
        if (node == null) {
            return context.asResult(Result.NULL, new ArrayList<>());
        } else {
            if (!(node instanceof SequenceNode seqNode)) {
                return context.asResult(Result.DIFFERENT_TYPE, new ArrayList<>());
            } else {
                List<Integer> var5 = (new ArrayList<>(seqNode.getValue().size()));

                for (Node elementNode : seqNode.getValue()) {
                    if (NodesKt.getParsed(elementNode) instanceof Number) {
                        Object var10001 = NodesKt.getParsed(elementNode);
                        Objects.requireNonNull(var10001);
                        var5.add(((Number) var10001).intValue());
                    }
                }

                return var5;
            }
        }
    }

    private static List<String> sl(NodeInterpretContext<List<String>> context) {
        Objects.requireNonNull(context);
        Node node = context.getNode();
        if (node == null) {
            return context.asResult(Result.NULL, new ArrayList<>());
        } else {
            if (!(node instanceof SequenceNode seqNode)) {
                return node instanceof ScalarNode ? Strings.split(((ScalarNode) node).getValue(), '\n', true) : context.asResult(Result.DIFFERENT_TYPE, new ArrayList<>());
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

    private static MathCompiler.Expression math(NodeInterpretContext<MathCompiler.Expression> var0) {
        Objects.requireNonNull(var0, "");
        Node node = var0.getNode();
        if (node == null) {
            return var0.asResult(Result.NULL, MathCompiler.DEFAULT_VALUE);
        } else {
            if (!(node instanceof ScalarNode)) {
                return var0.asResult(Result.DIFFERENT_TYPE, MathCompiler.DEFAULT_VALUE);
            } else {
                if (!(NodesKt.getParsed(node) instanceof MathCompiler.Expression)) {
                    ScalarNode var2 = (ScalarNode) node;
                    NodesKt.cacheConstructed(node, MathCompiler.compile(var2.getValue()));
                }

                Object parsed = NodesKt.getParsed(node);

                Intrinsics.checkNotNull(parsed);
                return (MathCompiler.Expression) parsed;
            }
        }
    }


    private static Float f(NodeInterpretContext<Float> context) {
        Objects.requireNonNull(context, "");
        Node node = context.getNode();
        if (node == null) {
            return context.asResult(Result.NULL, 0.0F);
        } else {
            if (!((NodesKt.getParsed(node)) instanceof Number)) {
                return context.asResult(Result.DIFFERENT_TYPE, 0.0F);
            } else {
                Object parsed = NodesKt.getParsed(node);
                Objects.requireNonNull(parsed);
                return ((Number) parsed).floatValue();
            }
        }
    }

    private static Long l(NodeInterpretContext<Long> context) {
        Objects.requireNonNull(context, "");
        Node node = context.getNode();
        if (node == null) {
            return context.asResult(Result.NULL, 0L);
        } else {
            if (!(NodesKt.getParsed(node) instanceof Number)) {
                return context.asResult(Result.DIFFERENT_TYPE, 0L);
            } else {
                Object parsed = NodesKt.getParsed(node);
                Objects.requireNonNull(parsed);
                return ((Number) parsed).longValue();
            }
        }
    }

    private static Double d(NodeInterpretContext<Double> var0) {
        Objects.requireNonNull(var0, "");
        Node node = var0.getNode();
        if (node == null) {
            return var0.asResult(Result.NULL, 0.0);
        } else {
            if (!(NodesKt.getParsed(node) instanceof Number)) {
                return var0.asResult(Result.DIFFERENT_TYPE, 0.0);
            } else {
                Object parsed = NodesKt.getParsed(node);
                Objects.requireNonNull(parsed);
                return ((Number) parsed).doubleValue();
            }
        }
    }


    private static Boolean b(NodeInterpretContext<Boolean> context) {
        Objects.requireNonNull(context, "");
        Node node = context.getNode();
        if (node == null) {
            return context.asResult(Result.NULL, Boolean.FALSE);
        } else {
            if (!(NodesKt.getParsed(node) instanceof Boolean)) {
                return context.asResult(Result.DIFFERENT_TYPE, Boolean.FALSE);
            } else {
                Object parsed = NodesKt.getParsed(node);
                Objects.requireNonNull(parsed);
                return (Boolean) parsed;
            }
        }
    }


    private static Integer i(NodeInterpretContext<Integer> context) {
        Objects.requireNonNull(context, "");
        Node node = context.getNode();
        if (node == null) {
            return context.asResult(Result.NULL, 0);
        } else {
            if (!(NodesKt.getParsed(node) instanceof Number)) {
                return context.asResult(Result.DIFFERENT_TYPE, 0);
            } else {
                Object parsed = NodesKt.getParsed(node);
                Objects.requireNonNull(parsed);
                return ((Number) parsed).intValue();
            }
        }
    }

    private static ConditionCompiler.LogicalOperand condition(NodeInterpretContext<ConditionCompiler.LogicalOperand> context) {
        Objects.requireNonNull(context);
        Node node = context.getNode();
        if (node == null) {
            return context.asResult(Result.NULL, null);
        } else {
            if (!(node instanceof ScalarNode)) {
                return context.asResult(Result.DIFFERENT_TYPE, null);
            } else {
                if (!(NodesKt.getParsed(node) instanceof ConditionCompiler.LogicalOperand)) {
                    ScalarNode var2 = (ScalarNode) node;
                    NodesKt.cacheConstructed(node, ConditionCompiler.compile(var2.getValue()).evaluate());
                }
                Object parsed = NodesKt.getParsed(node);
                Objects.requireNonNull(parsed);
                return (ConditionCompiler.LogicalOperand) parsed;
            }
        }
    }


}



