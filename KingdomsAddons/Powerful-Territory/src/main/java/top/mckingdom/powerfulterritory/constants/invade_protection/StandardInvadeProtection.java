package top.mckingdom.powerfulterritory.constants.invade_protection;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.events.invasion.KingdomPreInvadeEvent;
import org.kingdoms.locale.Language;
import org.kingdoms.locale.messenger.Messenger;
import org.kingdoms.locale.placeholders.context.MessagePlaceholderProvider;
import net.aurika.kingdoms.auspice.util.MessengerUtil;
import top.mckingdom.powerfulterritory.PowerfulTerritoryAddon;

import java.util.Locale;

public class StandardInvadeProtection implements InvadeProtection {

  public static final StandardInvadeProtection VISIBLE_PROTECTION = a(true, "VISIBLE_PROTECTION", "可见保护");
  public static final StandardInvadeProtection INVISIBLE_PROTECTION = a(true, "INVISIBLE_PROTECTION", "隐形保护");
  public static final StandardInvadeProtection NO_PROTECTION = a(false, "NO_PROTECTION", "无保护");

  private final boolean isProtect;
  private final Messenger nameMessenger;
  private final Namespace namespace;

  private static StandardInvadeProtection a(boolean b, String nsKey, String defaultName) {
    StandardInvadeProtection a = new StandardInvadeProtection(
        b,
        MessengerUtil.createMessenger(new String[]{PowerfulTerritoryAddon.CONFIG_HEAD, "invade-protection-name", nsKey.toLowerCase(Locale.ENGLISH).replace('_', '-')}, defaultName),
        new Namespace("PowerfulTerritory", nsKey)
    );
    InvadeProtectionRegistry.get().register(a);
    return a;
  }

  public static void init() {

  }

  public StandardInvadeProtection(boolean isProtect, Messenger nameMessenger, Namespace namespace) {
    this.isProtect = isProtect;
    this.nameMessenger = nameMessenger;
    this.namespace = namespace;
  }

  public boolean isProtect(KingdomPreInvadeEvent event) {
    return this.isProtect;
  }

  @Override
  public @NonNull String getName(@NotNull Language language) {
    return this.nameMessenger.getProvider(language).getMessage().buildPlain(new MessagePlaceholderProvider().lang(language));
  }

  @Override
  public @NonNull Namespace getNamespace() {
    return this.namespace;
  }

}
