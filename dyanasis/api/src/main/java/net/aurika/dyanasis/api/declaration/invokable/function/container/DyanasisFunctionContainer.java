package net.aurika.dyanasis.api.declaration.invokable.function.container;

import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunction;
import net.aurika.dyanasis.api.declaration.invokable.function.key.DyanasisFunctionSignature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.HashMap;
import java.util.Map;

public interface DyanasisFunctionContainer<F extends DyanasisFunction> {

  /**
   * Returns a {@linkplain DyanasisFunctionContainer} that contains none functions.
   *
   * @return the empty functions
   */
  static @NotNull EmptyDyanasisFunctionContainer empty() {
    return EmptyDyanasisFunctionContainer.INSTANCE;
  }

  /**
   * Returns {@code true} if this has function that has the same {@code key}.
   *
   * @param key the function key
   * @return {@code true} if it has the function
   */
  boolean hasFunction(@NotNull DyanasisFunctionSignature key);

  /**
   * Gets a dyanasis function by the {@code key}.
   *
   * @param key the function key
   * @return the function
   */
  @Nullable F getFunction(@NotNull DyanasisFunctionSignature key);

  /**
   * Gets a mapping of all dyanasis functions.
   *
   * @return the functions mapping
   */
  @Unmodifiable
  @NotNull Map<DyanasisFunctionSignature, ? extends F> allFunctions();

  /**
   * Gets dyanasis functions that have the same name {@code name}.
   *
   * @return the mapping of functions args length and function
   */
  @Deprecated
  default @NotNull Map<Integer, DyanasisFunction> getSameNameFunction(@NotNull String name) {
    HashMap<Integer, DyanasisFunction> sameNameFns = new HashMap<>();
    Map<DyanasisFunctionSignature, ? extends F> fns = this.allFunctions();
    for (Map.Entry<DyanasisFunctionSignature, ? extends F> entry : fns.entrySet()) {
      DyanasisFunctionSignature fnKey = entry.getKey();
      if (fnKey != null) {
        if (fnKey.name().equals(name)) {
          sameNameFns.put(fnKey.arity(), entry.getValue());
        }
      }
    }
    return sameNameFns;
  }

}
