package net.aurika.util.unsafe.fn;

import java.util.function.Supplier;

/**
 * This is simply a copy of {@link Supplier} to be used in method parameters
 * to bypass Java's type erasure backwards compatibility.
 */
@FunctionalInterface
public interface Provider<T> extends Supplier<T> {

  @Override
  T get();

}
