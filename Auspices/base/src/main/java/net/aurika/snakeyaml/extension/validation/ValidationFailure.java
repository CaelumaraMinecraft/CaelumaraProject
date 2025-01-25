package net.aurika.snakeyaml.extension.validation;

import org.snakeyaml.engine.v2.exceptions.Mark;
import org.snakeyaml.engine.v2.nodes.Node;

@SuppressWarnings("unused")
public class ValidationFailure {
    private final Node node;
    private Mark marker;
    private final Severity severity;
    private String message;

    public ValidationFailure(Severity severity, Node node, Mark marker, String message) {
        this.severity = severity;
        this.node = node;
        this.marker = marker == null ? node.getStartMark().get() : marker;  //todo null?
        this.message = message;
    }

    public Mark getMarker() {
        return this.marker;
    }

    public ValidationFailure withMarker(Mark marker) {
        this.marker = marker;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Severity getSeverity() {
        return this.severity;
    }

    public Node getNode() {
        return this.node;
    }

    public String getMessage() {
        return this.message;
    }

    public enum Severity {
        ERROR,
        WARNING

    }
}
