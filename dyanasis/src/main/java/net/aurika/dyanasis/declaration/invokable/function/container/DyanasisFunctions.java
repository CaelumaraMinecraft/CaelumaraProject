package net.aurika.dyanasis.declaration.invokable.function.container;

import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunction;
import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunctionKey;
import net.aurika.dyanasis.declaration.invokable.property.container.EmptyDyanasisProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.HashMap;
import java.util.Map;

public interface DyanasisFunctions {
    /**
     * Returns a {@linkplain DyanasisFunctions} that contains none functions.
     *
     * @return the empty functions
     */
    static @NotNull EmptyDyanasisProperties empty() {
        return EmptyDyanasisProperties.INSTANCE;
    }

    /**
     * Returns {@code true} if this has function that has the same {@code key}.
     *
     * @param key the function key
     * @return {@code true} if it has the function
     */
    boolean hasFunction(@NotNull DyanasisFunctionKey key);

    /**
     * Gets a dyanasis function by the {@code key}.
     *
     * @param key the function key
     * @return the function
     */
    @Nullable DyanasisFunction getFunction(@NotNull DyanasisFunctionKey key);

    /**
     * Gets a mapping of all dyanasis functions.
     *
     * @return the functions mapping
     */
    @Unmodifiable
    @NotNull Map<DyanasisFunctionKey, DyanasisFunction> allFunctions();

    /**
     * Gets dyanasis functions that have the same name {@code name}.
     *
     * @return the mapping of functions args length and function
     */
    default @NotNull Map<Integer, DyanasisFunction> getSameNameFunction(@NotNull String name) {
        HashMap<Integer, DyanasisFunction> sameNameFns = new HashMap<>();
        Map<DyanasisFunctionKey, DyanasisFunction> fns = this.allFunctions();
        for (Map.Entry<DyanasisFunctionKey, DyanasisFunction> entry : fns.entrySet()) {
            DyanasisFunctionKey fnKey = entry.getKey();
            if (fnKey != null) {
                if (fnKey.name().equals(name)) {
                    sameNameFns.put(fnKey.argLen(), entry.getValue());
                }
            }
        }
        return sameNameFns;
    }
}
