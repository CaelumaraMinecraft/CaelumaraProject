package net.aurika.config.yaml.common;

import org.snakeyaml.engine.v2.common.Anchor;

public class NodeReference {
    private final Type type;
    private final Anchor reference;

    public NodeReference(Type type, Anchor reference) {
        this.type = type;
        this.reference = reference;
    }

    public Anchor getReference() {
        return this.reference;
    }

    public Type getType() {
        return this.type;
    }

    public String toString() {
        return "NodeReference(" + this.type + ':' + this.reference + ')';
    }

    public enum Type {
        ANCHOR,
        ALIAS
    }
}