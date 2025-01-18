package top.auspice.key;

public interface Namespaced {
    @NSKey.Namespace
    String getNamespace();
}
