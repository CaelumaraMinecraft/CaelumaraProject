package net.aurika.namespace;

public interface Namespaced {
    @NSKey.Namespace
    String getNamespace();
}
