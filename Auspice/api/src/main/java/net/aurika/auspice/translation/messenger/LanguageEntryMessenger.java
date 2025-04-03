package net.aurika.auspice.translation.messenger;

import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import net.aurika.auspice.translation.diversity.Diversity;
import net.aurika.auspice.translation.message.provider.MessageProvider;
import net.aurika.config.path.ConfigEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * 使用配置路径去读取一个消息
 */
public class LanguageEntryMessenger implements Messenger {

  private final @NotNull ConfigEntry path;

  public LanguageEntryMessenger(@NotNull ConfigEntry path) {
    this.path = Objects.requireNonNull(path);
  }

  public final @NotNull ConfigEntry getEntry() {
    return this.path;
  }

  public LanguageEntryMessenger(@NotNull String... path) {
    this(new ConfigEntry(Objects.requireNonNull(path)));
  }

  public @Nullable MessageProvider getProvider(@NotNull Diversity diversity) {
    Objects.requireNonNull(diversity);
    return diversity.getMessage(this.path, false);
  }

  public @NotNull String toString() {
    return "LanguageEntryMessenger{" + String.join(" -> ", this.path.getPath()) + '}';
  }

  public final @NotNull OptionalMessenger optional() {
    return new OptionalMessenger(this);
  }

  public final @NotNull DefaultedMessenger or(@NotNull Supplier<Messenger> messengerSupplier) {
    Objects.requireNonNull(messengerSupplier, "");
    return new DefaultedMessenger(this, messengerSupplier);
  }

  public final @NotNull DefaultedMessenger safe() {
    return this.or(() -> {
      StringBuilder var10002 = new StringBuilder(
          "&8[&4Error&8] &eMessage path not defined even for EN language &quantum");
      String[] var10003 = this.path.getPath();
      Intrinsics.checkNotNullExpressionValue(var10003, "");
      return new StaticMessenger(var10002.append(
          ArraysKt.joinToString((Object[]) var10003, " &7-> &quantum", "", "", -1, "", null)).toString());
    });
  }

  public int hashCode() {
    return this.path.hashCode();
  }

  public boolean equals(@Nullable Object obj) {
    return obj instanceof LanguageEntryMessenger && Intrinsics.areEqual(this.path, ((LanguageEntryMessenger) obj).path);
  }

  public static @NotNull LanguageEntryMessenger of(@NotNull String path) {
    return new LanguageEntryMessenger(ConfigEntry.fromString(Objects.requireNonNull(path)));
  }

}

