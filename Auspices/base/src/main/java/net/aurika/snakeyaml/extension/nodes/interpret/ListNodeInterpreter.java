package net.aurika.snakeyaml.extension.nodes.interpret;

import net.aurika.utils.Checker;
import org.jetbrains.annotations.NotNull;
import org.snakeyaml.engine.v2.nodes.Node;
import org.snakeyaml.engine.v2.nodes.ScalarNode;
import org.snakeyaml.engine.v2.nodes.SequenceNode;

import java.util.ArrayList;
import java.util.List;

import static net.aurika.snakeyaml.extension.nodes.interpret.NodeInterpretContext.Result.*;

public class ListNodeInterpreter<E> implements NodeInterpreter<List<E>> {

    private final @NotNull NodeInterpreter<E> elementInterpreter;
    private final boolean translateSingleNodeToList;
    private final boolean requireAllSuccessfulElements;

    public static <E> @NotNull ListNodeInterpreter<E> strictListInterpreter(@NotNull NodeInterpreter<E> elementInterpreter) {
        return listInterpreter(elementInterpreter, false, true);
    }

    public static <E> @NotNull ListNodeInterpreter<E> looseListInterpreter(@NotNull NodeInterpreter<E> elementInterpreter) {
        return listInterpreter(elementInterpreter, true, false);
    }

    public static <E> @NotNull ListNodeInterpreter<E> listInterpreter(@NotNull NodeInterpreter<E> elementInterpreter, boolean translateSingleNodeToList, boolean requireAllSuccessfulElements) {
        Checker.Arg.notNull(elementInterpreter, "elementInterpreter");
        return new ListNodeInterpreter<>(elementInterpreter, translateSingleNodeToList, requireAllSuccessfulElements);
    }

    protected ListNodeInterpreter(@NotNull NodeInterpreter<E> elementInterpreter, boolean translateSingleNodeToList, boolean requireAllSuccessfulElements) {
        Checker.Arg.notNull(elementInterpreter, "elementInterpreter");
        this.elementInterpreter = elementInterpreter;
        this.translateSingleNodeToList = translateSingleNodeToList;
        this.requireAllSuccessfulElements = requireAllSuccessfulElements;
    }

    public @NotNull NodeInterpreter<E> elementInterpreter() {
        return elementInterpreter;
    }

    public boolean translateSingleNodeToList() {
        return translateSingleNodeToList;
    }

    public boolean requireAllSuccessfulElements() {
        return requireAllSuccessfulElements;
    }

    @Override
    public List<E> parse(@NotNull NodeInterpretContext<List<E>> context) {
        Node node = context.getNode();
        if (node == null) {
            return context.asResult(NULL, null);
        }
        if (node instanceof ScalarNode scalarNode) {
            if (translateSingleNodeToList) {
                List<E> list = new ArrayList<>();
                NodeInterpretContext<E> eContext = new NodeInterpretContext<>(scalarNode);
                list.add(elementInterpreter.parse(eContext));
                return context.asResult(COERCED_TYPE, list);
            } else {
                return context.asResult(DIFFERENT_TYPE, null);
            }
        }
        if (node instanceof SequenceNode sequenceNode) {
            List<E> list = new ArrayList<>();
            for (Node eNode : sequenceNode.getValue()) {
                NodeInterpretContext<E> eContext = new NodeInterpretContext<>(eNode);
                E eValue = elementInterpreter.parse(eContext);
                if (requireAllSuccessfulElements) {
                    NodeInterpretContext.Result eResult = eContext.getResult();
                    if (eResult == null || !eResult.isSuccessful()) {
                        return context.asResult(DIFFERENT_TYPE, null);
                    }
                }
                list.add(eValue);
            }
            return context.asResult(SUCCESS, list);
        }
        return context.asResult(DIFFERENT_TYPE, null);
    }
}
