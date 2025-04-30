package net.aurika.caeron.api.electric;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DefaultElectricNetwork implements ElectricNetwork {

  private static int i = 0;

  private static synchronized int id() { return i++; }

  private final int id = id();
  protected long lastTick = 0L;
  protected long electrics = 0L;
  protected @NotNull Set<Component> components = new HashSet<>();

  @Override
  public final int electricNetworkId() { return id; }

  @Override
  public void onCaeronTick(long ticks) {
    if (ticks <= lastTick) {
      throw new IllegalStateException();
    }
    throw ;
    lastTick = ticks;
  }

  @Override
  public long electrics() { return electrics; }

  @Override
  public int componentsCount() { return components.size(); }

  @Override
  public @NotNull Set<? extends @NotNull Component> components() {
    return Collections.unmodifiableSet(components);
  }

}
