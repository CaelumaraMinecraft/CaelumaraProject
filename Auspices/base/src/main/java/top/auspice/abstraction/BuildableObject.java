package top.auspice.abstraction;

public interface BuildableObject {
    Builder toBuilder();

    interface Builder {
        BuildableObject build();
    }
}
