package net.aurika.config.placeholders.transformer;

import java.util.function.Function;

@FunctionalInterface
public interface PlaceholderTransformer<I, O> extends Function<I, O> {
}
