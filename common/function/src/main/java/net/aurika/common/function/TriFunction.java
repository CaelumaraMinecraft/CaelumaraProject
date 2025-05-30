package net.aurika.common.function;

import java.util.Objects;
import java.util.function.Function;

/**
 * Represents a function that accepts three arguments and produces a result. This is the three-arity
 * specialization of {@link Function}.
 *
 * <p>This is a <a href="package-summary.html">function interface</a>
 * whose function method is {@link #apply(Object, Object, Object)}.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <V> the type of the third argument of the function
 * @param <R> the type of the result of the function
 * @see Function
 */
@FunctionalInterface
public interface TriFunction<T, U, V, R> {

  /**
   * Performs this operation on the given arguments.
   *
   * @param t the first input argument
   * @param u the second input argument
   * @param v the third input argument
   */
  R apply(T t, U u, V v);

  /**
   * Returns a composed function that first applies this function to its input, and then applies
   * the {@code after} function to the result. If evaluation of either function throws an
   * exception, it is relayed to the caller of the composed function.
   *
   * @param <S>   the type of output of the {@code after} function, and of the composed function
   * @param after the function to apply after this function is applied
   * @return a composed function that first applies this function and then applies the {@code
   * after} function
   * @throws NullPointerException if after is null
   */
  default <S> TriFunction<T, U, V, S> andThen(Function<? super R, ? extends S> after) {
    Objects.requireNonNull(after);
    return (T t, U u, V v) -> after.apply(apply(t, u, v));
  }

}
