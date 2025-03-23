package net.aurika.auspice.configs.messages.placeholders.context;

/**
 * {@linkplain VariableProvider} is a class used to provide variable for placeholder interpreting.
 */
@FunctionalInterface
public interface VariableProvider {
    VariableProvider EMPTY = (x) -> null;

    /**
     * Provides a variable by name.
     * @param name the name
     * @return a variable
     */
    Object provideVariable(String name);
}
