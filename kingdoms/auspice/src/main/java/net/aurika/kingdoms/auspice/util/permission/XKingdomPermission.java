package net.aurika.kingdoms.auspice.util.permission;

import net.aurika.validate.Validate;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.constants.player.KingdomPermission;
import org.kingdoms.locale.LanguageEntry;
import org.kingdoms.locale.messenger.DefaultedMessenger;
import org.kingdoms.locale.messenger.LanguageEntryMessenger;
import org.kingdoms.locale.messenger.Messenger;
import org.kingdoms.locale.messenger.StaticMessenger;
import org.kingdoms.main.Kingdoms;

public class XKingdomPermission extends KingdomPermission {
//    private static int hashCounter = 180;
//
//    private static synchronized int increaseHashCounter() {
//        return hashCounter++;
//    }

  private final @NotNull Messenger name;
  private final @NotNull Messenger description;
  private final @NotNull Messenger deniedMessage;

  private static final String[] a = new String[]{"kingdom-permission", null, null};

  @Contract(value = "_, _ -> new", pure = true)
  protected static @NotNull String @NotNull [] componentPath(@NotNull Namespace ns, @NotNull String sec) {
    String[] _a = a.clone();
    _a[1] = ns.getConfigOptionName();
    _a[2] = sec;
    return _a;
  }

  protected static @NotNull LanguageEntry componentEntry(@NotNull Namespace ns, @NotNull String sec) {
    return new LanguageEntry(componentPath(ns, sec));
  }

  public static @NotNull XKingdomPermission create(
      @NotNull Namespace namespace,
      @NotNull String name,
      @NotNull String description,
      @NotNull String deniedMessage
  ) {
    return create(
        namespace,
        new DefaultedMessenger(
            new LanguageEntryMessenger(componentEntry(namespace, "name")),
            () -> new StaticMessenger(name)
        ),
        new DefaultedMessenger(
            new LanguageEntryMessenger(componentEntry(namespace, "description")),
            () -> new StaticMessenger(description)
        ),
        new DefaultedMessenger(
            new LanguageEntryMessenger(componentEntry(namespace, "denied")),
            () -> new StaticMessenger(deniedMessage)
        )
    );
  }

  public static @NotNull XKingdomPermission create(
      @NotNull Namespace namespace,
      @NotNull Messenger name,
      @NotNull Messenger description,
      @NotNull Messenger deniedMessage
  ) {
    XKingdomPermission perm = new XKingdomPermission(namespace, name, description, deniedMessage);
    Kingdoms.get().getPermissionRegistery().register(perm);
    return perm;
  }

  /**
   * Create.
   * It will automatically set a unique hash.
   */
  protected XKingdomPermission(@NotNull Namespace namespace, @NotNull Messenger name, @NotNull Messenger description, @NotNull Messenger deniedMessage) {
    super(namespace);
    Validate.Arg.notNull(name, "name");
    Validate.Arg.notNull(description, "description");
    Validate.Arg.notNull(deniedMessage, "deniedMessage");
    this.name = name;
    this.description = description;
    this.deniedMessage = deniedMessage;
//        setHash(increaseHashCounter()); // fixed in KingdomsX 1.17.10-BETA
  }

  public final void sendDeniedMessage(@NotNull Player player) {
    this.deniedMessage.sendError(player);
  }

  public final void sendDeniedMessage(@NotNull Player player, Object... edits) {
    this.deniedMessage.sendError(player, edits);
  }

  /**
   * Gets the name messenger.
   *
   * @return the name messenger
   */
  public final @NotNull Messenger name() {
    return this.name;
  }

  /**
   * Gets the description messenger.
   *
   * @return the description messenger
   */
  public final @NotNull Messenger description() {
    return this.description;
  }

  /**
   * Gets the denied message messenger.
   *
   * @return the denied message messenger
   */
  public final @NotNull Messenger deniedMessage() {
    return this.deniedMessage;
  }

}
