package net.aurika.config.placeholders.context;

@FunctionalInterface
public interface PlaceholderProvider {
    PlaceholderProvider EMPTY = (x) -> null;

    Object providePlaceholder(String name);
}
