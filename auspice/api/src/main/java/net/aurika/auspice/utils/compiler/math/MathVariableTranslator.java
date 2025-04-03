package net.aurika.auspice.utils.compiler.math;

import net.aurika.auspice.utils.compiler.base.VariableTranslator;

import java.util.function.Function;

@FunctionalInterface
public interface MathVariableTranslator extends VariableTranslator<Double>, Function<String, Double> {

  Double apply(String variableName);

}
