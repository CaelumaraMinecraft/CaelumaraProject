package net.aurika.kingdoms.auspice.util.permission;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.group.Group;
import org.kingdoms.constants.group.model.relationships.RelationAttribute;
import org.kingdoms.constants.group.model.relationships.StandardRelationAttribute;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.locale.LanguageEntry;
import org.kingdoms.locale.messenger.DefaultedMessenger;
import org.kingdoms.locale.messenger.LanguageEntryMessenger;
import org.kingdoms.locale.messenger.Messenger;
import org.kingdoms.locale.messenger.StaticMessenger;
import org.kingdoms.main.Kingdoms;

public class XRelationAttribute extends RelationAttribute {

//    private static int hashCounter = 800;
//
//    private static synchronized int increaseHashCounter() {
//        return hashCounter++;
//    }

  private final @NotNull Messenger name;
  private final @NotNull Messenger description;

  private static final String[] a = new String[]{"relation-attribute", null, null};

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

  public static @NotNull XRelationAttribute create(@NotNull Namespace namespace,
                                                   @NotNull String name,
                                                   @NotNull String description
  ) {
    return create(
        namespace,
        new DefaultedMessenger(new LanguageEntryMessenger(componentEntry(namespace, "name")), () -> new StaticMessenger(name)),
        new DefaultedMessenger(new LanguageEntryMessenger(componentEntry(namespace, "description")), () -> new StaticMessenger(description))
    );
  }

  public static @NotNull XRelationAttribute create(@NotNull Namespace namespace,
                                                   @NotNull Messenger name,
                                                   @NotNull Messenger description
  ) {
    XRelationAttribute attr = new XRelationAttribute(namespace, name, description);
    Kingdoms.get().getRelationAttributeRegistry().register(attr);
    return attr;
  }

  /**
   * Create.
   * It will automatically set a unique hash.
   */
  protected XRelationAttribute(@NotNull Namespace namespace, @NotNull Messenger name, @NotNull Messenger description) {
    super(namespace);
    Validate.Arg.notNull(name, "name");
    Validate.Arg.notNull(description, "description");
    this.name = name;
    this.description = description;
//        setHash(increaseHashCounter());
  }

  @Override
  public boolean hasAttribute(Group primary, Group secondary) {
    return StandardRelationAttribute.hasAttribute(this, primary, secondary);
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

}
