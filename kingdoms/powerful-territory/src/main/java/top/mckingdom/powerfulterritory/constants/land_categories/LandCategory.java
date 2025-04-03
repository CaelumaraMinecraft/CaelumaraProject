package top.mckingdom.powerfulterritory.constants.land_categories;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.constants.namespace.Namespaced;
import org.kingdoms.locale.Language;

public abstract class LandCategory implements Namespaced {

  private static int hashCount = 0;

  private synchronized static int hash() {
    return hashCount++;
  }

  private final @NotNull Namespace namespace;
  private final boolean editable;
  private final int hash;

  protected LandCategory(@NotNull Namespace namespace, boolean editable) {
    Validate.Arg.notNull(namespace, "namespace");
    this.namespace = namespace;
    this.editable = editable;
    this.hash = hash();
  }

  /**
   * Gets this {@linkplain LandCategory} as a config key representation. Such
   * as {@code "PowerfulTerritory:invading"}.
   *
   * @return the config key representation
   */
  public final @NotNull String toConfigKey() {
    return this.namespace.getConfigOptionName();
  }

  @ApiStatus.Experimental
  public abstract @NotNull String getName(@NotNull Language lang);

  /**
   * Gets weather this {@linkplain LandCategory} can edited by player.
   *
   * @return weather this land category is editable
   */
  @ApiStatus.Experimental
  public boolean isEditable() {
    return editable;
  }

  @Override
  public final @NotNull Namespace getNamespace() {
    return this.namespace;
  }

  @Override
  public final int hashCode() {
    return this.hash;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof LandCategory)) {
      return false;
    } else {
      return this.namespace.equals(((LandCategory) o).namespace);
    }
  }

  @Override
  public @NotNull String toString() {
    return "LandCategory:{" + this.toConfigKey() + '}';
  }

}
