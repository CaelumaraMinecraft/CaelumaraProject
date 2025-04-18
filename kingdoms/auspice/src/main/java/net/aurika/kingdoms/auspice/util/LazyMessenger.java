package net.aurika.kingdoms.auspice.util;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.locale.Language;
import org.kingdoms.locale.messenger.Messenger;
import org.kingdoms.locale.provider.MessageProvider;

import java.util.function.Supplier;

@ApiStatus.Experimental
public class LazyMessenger implements Messenger {

  private final @NotNull Supplier<Messenger> supplier;

  public LazyMessenger(@NotNull Supplier<Messenger> supplier) {
    Validate.Arg.notNull(supplier, "supplier");
    this.supplier = supplier;
  }

  @Override
  public @Nullable MessageProvider getProvider(@NotNull Language language) {
    return supplier.get().getProvider(language);
  }

  /**
   * Gets the messenger supplier.
   *
   * @return the messenger supplier
   */
  public @NotNull Supplier<Messenger> supplier() {
    return supplier;
  }

}
