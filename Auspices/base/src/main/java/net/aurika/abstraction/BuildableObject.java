package net.aurika.abstraction;

public interface BuildableObject {
    Builder toBuilder();

    interface Builder {
        BuildableObject build();
    }
}
