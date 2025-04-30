package net.aurika.common.metalang;

import net.aurika.common.annotation.container.NullOnAbsent;
import net.aurika.common.annotation.container.ThrowOnAbsent;
import net.aurika.common.metalang.flow.Variables;
import net.aurika.common.validate.Validate;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import net.kyori.examination.string.StringExaminer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public interface MetalangObject {

  @Contract("_ -> new")
  static @NotNull MetalangObject metalangObject(@NotNull Variables variables) {
    return new MetalangObjectImpl(variables);
  }

  String VAR_NULL = "<null>";

  Variables variables();

  boolean hasVariable(@NotNull String varName);

  @NullOnAbsent
  @Nullable Object findVariable(@NotNull String varName);

  @ThrowOnAbsent
  @Nullable Object getVariable(@NotNull String varName);

  @ThrowOnAbsent  // when doesn't has the key
  @Nullable Object setVariable(@NotNull String varName, @Nullable Object value);

}

final class MetalangObjectImpl implements MetalangObject, Examinable {

  private final @NotNull Map<String, Object> vars;
  private final Variables variables;

  MetalangObjectImpl(Variables variables) {
    this(new HashMap<>(variables.value().length), variables);
  }

  MetalangObjectImpl(@NotNull Map<String, Object> vars, Variables variables) {
    Validate.Arg.notNull(vars, "vars");
    Validate.Arg.notNull(variables, "variables");
    this.vars = vars;
    this.variables = variables;
    vars.put(VAR_NULL, null);
    for (String varName : variables.value()) {
      vars.put(varName, null);
    }
  }

  @Override
  public Variables variables() {
    return variables;
  }

  @Override
  public boolean hasVariable(@NotNull String varName) {
    return vars.containsKey(varName);
  }

  @Override
  public @Nullable Object findVariable(@NotNull String varName) {
    return vars.get(varName);
  }

  @Override
  public @Nullable Object getVariable(@NotNull String varName) {
    if (vars.containsKey(varName)) {
      return vars.get(varName);
    } else {
      throw new NoSuchElementException(varName);
    }
  }

  @Override
  public @Nullable Object setVariable(@NotNull String varName, @Nullable Object value) {
    if (vars.containsKey(varName)) {
      return vars.put(varName, value);
    } else {
      throw new NoSuchElementException(varName);
    }
  }

  @Override
  public @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Stream.of(ExaminableProperty.of("vars", vars));
  }

  @Override
  public @NotNull String toString() {
    return StringExaminer.simpleEscaping().examine(this);
  }

}
