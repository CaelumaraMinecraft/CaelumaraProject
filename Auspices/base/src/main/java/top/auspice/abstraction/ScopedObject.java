package top.auspice.abstraction;

public interface ScopedObject {
    Scope getScope();

    interface Scope {
    }
}
