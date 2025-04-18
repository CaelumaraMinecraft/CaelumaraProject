package net.aurika.auspice.translation.diversity;

import net.aurika.auspice.configs.messages.context.MessageContextImpl;
import net.aurika.auspice.text.TextObject;
import net.aurika.auspice.translation.TranslationEntry;
import net.aurika.auspice.translation.message.provider.MessageProvider;
import net.aurika.common.key.namespace.NSedKey;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class AbstractDiversity implements Diversity {

  protected final @NotNull NSedKey id;
  protected final @NotNull String folderName;
  protected final @NotNull String lowercaseName;
  protected final @NotNull String nativeName;
  protected final @NotNull Locale locale;
  protected final @NotNull TimeZone timeZone;

  public AbstractDiversity(@NotNull NSedKey id) {
    Validate.Arg.notNull(id, "id");
    this.id = id;
  }

  @Override
  public @NotNull NSedKey getNamespacedKey() {
    return this.id;
  }

  @Override
  public @NotNull String folderName() {
    return folderName;
  }

  @Override
  public @NotNull String lowerCaseName() {
    return lowercaseName;
  }

  @Override
  public @NotNull String nativeName() {
    return nativeName;
  }

  @Override
  public @Nullable Locale getLocal() {
    return locale;
  }

  @Override
  public @NotNull TimeZone getTimeZone() {
    return timeZone;
  }

  @Override
  public @Nullable MessageProvider getMessage(@NotNull TranslationEntry path, boolean useDefault) {
    return null;
  }

  @Override
  public Map<TranslationEntry, MessageProvider> getMessages() {
    Map<TranslationEntry, MessageProvider>
  }

  @Override
  public TextObject getVariableRaw(@Nullable String name) {
    return null;
  }

  @Override
  public @Nullable TextObject getVariable(@Nullable MessageContextImpl context, @Nullable String variable, boolean noDefault) {
    return null;
  }

}
