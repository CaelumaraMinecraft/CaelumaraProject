package net.aurika.auspice.translation.diversity;

import net.aurika.auspice.configs.messages.context.MessageContextImpl;
import net.aurika.auspice.text.TextObject;
import net.aurika.auspice.translation.message.provider.MessageProvider;
import net.aurika.auspice.user.Auspice;
import net.aurika.common.key.namespace.NSedKey;
import net.aurika.configuration.path.ConfigEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class StandardDiversity extends AbstractDiversity implements Diversity {

  /**
   * 简体中文
   */
  public static final StandardDiversity SIMPLIFIED_CHINESE = new StandardDiversity(
      Auspice.createKey("diversity/chinese_simplified"), );

  public static final StandardDiversity TRADITIONAL_CHINESE = new StandardDiversity(
      Auspice.createKey("diversity/chinese_traditional"), )

  /**
   * 默认英语
   */
  public static final StandardDiversity ENGLISH = new StandardDiversity(Auspice.createKey("DEFAULT_ENGLISH"), );

  protected final @NotNull Path pluginFolder;
  protected final @NotNull String folderName;
  protected final @NotNull String lowercaseName;
  protected final @NotNull String nativeName;
  protected final @Nullable Locale locale;
  protected final @NotNull TimeZone timeZone;

  public StandardDiversity(
      NSedKey NSedKey,
      @NotNull Path pluginFolder,
      @Nullable Locale locale,
      @NotNull TimeZone timeZone,
      @NotNull String folderName,
      @NotNull String lowercaseName,
      @NotNull String nativeName
  ) {
    super(NSedKey);
    this.pluginFolder = pluginFolder;
    this.folderName = folderName;
    this.lowercaseName = lowercaseName;
    this.nativeName = nativeName;
    this.locale = locale;
    this.timeZone = timeZone;
    DiversityRegistry.INSTANCE.register(this);
  }

  @Override
  public @NotNull String folderName() {
    return this.folderName;
  }

  @Override
  public @NotNull String lowerCaseName() {
    return this.lowercaseName;
  }

  @Override
  public @NotNull String nativeName() {
    return this.nativeName;
  }

  @Override
  public @Nullable Locale getLocal() {
    return this.locale;
  }

  @Override
  public @NotNull TimeZone getTimeZone() {
    return this.timeZone;
  }

  @Override
  public @Nullable MessageProvider getMessage(@NotNull ConfigEntry path, boolean useDefault) {
    return null;
  }

  @Override
  public Map<ConfigEntry, MessageProvider> getMessages() {

  }

  @Override
  public TextObject getVariableRaw(@Nullable String name) {
    return null;
  }

  @Override
  public @Nullable TextObject getVariable(@Nullable MessageContextImpl context, @Nullable String variable, boolean noDefault) {
    return null;
  }

  public static void init() { }

}
