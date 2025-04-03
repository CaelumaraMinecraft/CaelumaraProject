package net.aurika.auspice.configs.messages.placeholders.context;

import net.aurika.common.annotations.Getter;
import net.aurika.common.annotations.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class PlaceholderContextBuilderImpl implements PlaceholderContextBuilder {

  protected Map<String, Object> variables;
  protected Map<String, VariableProvider> children;
  protected VariableProvider unknownPlaceholderHandler;
  protected Object primaryTarget;
  protected Object secondaryTarget;
  protected Map<String, Object> targets;

  public PlaceholderContextBuilderImpl() {
  }

  @Override
  @Setter
  public PlaceholderContextBuilderImpl children(Map<String, VariableProvider> children) {
    this.children = children;
    return this;
  }

  @Override
  @Getter
  public Map<String, VariableProvider> children() {
    return this.children;
  }

  @Override
  @Getter
  public Map<String, Object> variables() {
    return this.variables;
  }

  @Override
  @Setter
  public PlaceholderContextBuilderImpl variables(Map<String, Object> variables) {
    this.variables = variables;
    return this;
  }

  @Override
  @Getter
  public VariableProvider unknownPlaceholderHandler() {
    return this.unknownPlaceholderHandler;
  }

  @Override
  @Setter
  public VariableProvider onUnknownPlaceholder(VariableProvider unknownPlaceholderHandler) {
    this.unknownPlaceholderHandler = unknownPlaceholderHandler;
    return this;
  }

  @Override
  @Getter
  public @Nullable Object primaryTarget() {
    return this.primaryTarget;
  }

  @Override
  @Setter
  public PlaceholderContextBuilder primaryTarget(Object target) {
    return null;
  }

  @Override
  @Getter
  public @Nullable Object secondaryTarget() {
    return this.secondaryTarget;
  }

  @Override
  @Setter
  public PlaceholderContextBuilder secondaryTarget(Object target) {
    return null;
  }

  @Override
  @Getter
  public @NotNull Map<String, Object> targets() {
    if (this.targets == null) {
      this.targets = new HashMap<>(3);
    }

    return this.targets;
  }

  @Override
  public PlaceholderContextBuilderImpl clone() {
    return this.cloneInto(new PlaceholderContextBuilderImpl());
  }

  @Override
  public String toString() {
    return
        this.getClass().getSimpleName() +
            "{ contextOf=" + this.primaryTarget +
            ", other=" + this.secondaryTarget +
            ", variables=" + (this.variables == null ? "{}" : this.variables.entrySet().stream().map(
            (entry) -> entry.getKey() + '=' + entry.getValue()).toList()) +
            " }";
  }

}
