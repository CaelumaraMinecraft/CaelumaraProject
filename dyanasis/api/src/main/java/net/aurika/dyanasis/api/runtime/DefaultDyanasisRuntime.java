package net.aurika.dyanasis.api.runtime;

import net.aurika.dyanasis.api.runtime.environment.DyanasisRuntimeEnvironment;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public class DefaultDyanasisRuntime implements DyanasisRuntime {

  private static int counter = 0;

  private static synchronized int counterID() { return counter++; }

  private final int runtimeID = counterID();
  private final @NotNull DyanasisRuntimeEnvironment environment;
  protected String label;

  public DefaultDyanasisRuntime(@NotNull DyanasisRuntimeEnvironment environment) {
    Validate.Arg.notNull(environment, "environment");
    this.environment = environment;
  }

  @Override
  public int runtimeID() { return runtimeID; }

  @Override
  public @NotNull DyanasisRuntimeEnvironment environment() { return environment; }

  @Override
  public String label() { return label; }

  @Override
  public void label(String label) { this.label = label; }

  @Override
  public int hashCode() {
    return runtimeID;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof DefaultDyanasisRuntime) {
      DefaultDyanasisRuntime other = (DefaultDyanasisRuntime) obj;
      return runtimeID() == other.runtimeID();
    }
    return false;
  }

  @Override
  public String toString() {
    return "DefaultDyanasisRuntime{" +
        "runtimeID=" + runtimeID +
        '}';
  }

}
