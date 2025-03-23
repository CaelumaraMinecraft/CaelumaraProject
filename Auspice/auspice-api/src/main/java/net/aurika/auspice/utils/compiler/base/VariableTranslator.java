package net.aurika.auspice.utils.compiler.base;

import java.util.function.Function;

public interface VariableTranslator<T> extends Function<String, T> {
    T apply(String variableName);
}
