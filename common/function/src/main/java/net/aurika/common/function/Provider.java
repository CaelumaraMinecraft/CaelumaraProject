package net.aurika.common.function;

/**
 * Represents a provider of results.
 *
 * <p>There is no requirement that a new or distinct result be returned each
 * time the provider is invoked.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #get()}.
 *
 * @param <T> the type of results supplied by this provider
 */
@FunctionalInterface
public interface Provider<T> {

  /**
   * Gets a result.
   *
   * @return a result
   */
  T get();

}
