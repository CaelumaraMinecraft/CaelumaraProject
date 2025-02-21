package net.aurika.common.key.namespace;

public interface Namespaced {
    @NSKey.Namespace
    String getNamespace();
}
