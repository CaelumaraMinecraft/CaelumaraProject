package net.aurika.auspice.translation.message.manager;

import net.aurika.auspice.translation.diversity.Diversity;
import net.aurika.auspice.translation.messenger.DefinedMessenger;
import net.aurika.common.key.Ident;
import net.aurika.common.key.namespace.NSedKey;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

public class StandardMessageManager implements MessageManager {

  private final @NotNull Ident id;
  private final @NotNull Diversity defaultDiversity;
  protected final @NotNull Map<@NotNull NSedKey, DefinedMessenger[]> defaultMessengers;

  public static final StandardMessageManager STANDARD = new StandardMessageManager();

  protected StandardMessageManager(@NotNull Ident id, @NotNull Diversity defaultDiversity, @NotNull Map<NSedKey, DefinedMessenger[]> defaultMessengers) {
    Validate.Arg.notNull(id, "id");
    Validate.Arg.notNull(defaultDiversity, "defaultDiversity");
    Validate.Arg.notNull(defaultMessengers, "defaultMessengers");
    this.id = id;
    this.defaultDiversity = defaultDiversity;
    this.defaultMessengers = defaultMessengers;
  }

  public void register(@NotNull NSedKey id, @NotNull DefinedMessenger @NotNull [] defaultMessengers) {
    Validate.Arg.notNull(id, "id");
    Validate.Arg.nonNullArray(defaultMessengers, "defaultMessengers");
    if (defaultMessengers.)
  }

  @Override
  public @NotNull Ident ident() {
    return id;
  }

  public @NotNull Diversity getDefaultDiversity() {
    return defaultDiversity;
  }

  public @NotNull Map<NSedKey, DefinedMessenger[]> getDefaultMessengers() {
    return defaultMessengers;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || getClass() != obj.getClass()) return false;
    StandardMessageManager that = (StandardMessageManager) obj;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

}
