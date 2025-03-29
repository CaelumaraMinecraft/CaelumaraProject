package net.aurika.auspice.configs.messages.placeholders.context;

import net.aurika.util.cache.single.CacheableObject;
import net.aurika.util.cache.single.CachedSupplier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class LazyVariableProvider implements VariableProvider {

  private final CacheableObject<VariableProvider> delegate;

  public LazyVariableProvider(Supplier<VariableProvider> placeholderProviderSupplier) {
    this.delegate = new CachedSupplier<>(placeholderProviderSupplier);
  }

  public Object provideVariable(String name) {
    return this.delegate.get().provideVariable(name);
  }

  public @NotNull String toString() {
    return LazyVariableProvider.class.getSimpleName() + "{" + this.delegate.get().toString() + '}';
  }

}
