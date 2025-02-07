package top.auspice.configs.texts.placeholders.context;

@FunctionalInterface
public interface PlaceholderProvider {
    PlaceholderProvider EMPTY = (x) -> null;

    Object providePlaceholder(String name);
}
