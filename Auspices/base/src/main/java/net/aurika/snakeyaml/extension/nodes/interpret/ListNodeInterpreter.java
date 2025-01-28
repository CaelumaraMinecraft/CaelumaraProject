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
    private final boolean translateSingleToList;
    private final boolean requireAllSuccessElements;

    protected ListNodeInterpreter(@NotNull NodeInterpreter<E> elementInterpreter, boolean translateSingleToList, boolean requireAllSuccessElements) {
        Checker.Arg.notNull(elementInterpreter, "elementInterpreter");
        this.elementInterpreter = elementInterpreter;
        this.translateSingleToList = translateSingleToList;
        this.requireAllSuccessElements = requireAllSuccessElements;
    }

    public @NotNull NodeInterpreter<E> elementInterpreter() {
        return elementInterpreter;
    }

    public boolean translateSingleToList() {
        return translateSingleToList;
    }

    public boolean requireAllSuccessElements() {
        return requireAllSuccessElements;
    }

    @Override
    public List<E> parse(@NotNull NodeInterpretContext<List<E>> context) {
        Node node = context.getNode();
        if (node == null) {
            return context.asResult(NULL, null);
        }
        if (node instanceof ScalarNode scalarNode) {
            if (translateSingleToList) {
                List<E> list = new ArrayList<>();
                list.add(elementInterpreter.parse(new NodeInterpretContext<>(scalarNode)));
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
                if (requireAllSuccessElements) {
                    NodeInterpretContext.Result eResult = eContext.getResult();
                    if (eResult == null || !eResult.isSuccess()) {
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
