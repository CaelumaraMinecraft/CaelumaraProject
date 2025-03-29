package net.aurika.auspice.configs.messages.placeholders.transformer;

import java.util.function.Function;

@FunctionalInterface
public interface PlaceholderTransformer<I, O> extends Function<I, O> {

  @Override
  O apply(I i);

}
