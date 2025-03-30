package net.aurika.kingdoms.auspice.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.addons.Addon;
import org.kingdoms.main.PluginState;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public abstract class AddonTemplate extends JavaPlugin implements Addon {

  private static final Map<Class<? extends AddonTemplate>, AddonTemplate> subs = new HashMap<>();

  @Contract("null, _ -> null; !null, _ -> !null")
  public static @Nullable String unboxAddonName(@Nullable Addon addon, boolean silent) {
    if (addon == null) return null;
    String addonName = addon.getAddonName();
    if (addonName == null) {
      if (silent) {
        return null;
      } else {
        throw new NullPointerException("addonName is null");
      }
    } else {
      return addonName;
    }
  }

  private @NotNull PluginState state;

  public AddonTemplate() {
    super();
    state = PluginState.INITIATING;
    @Nullable AddonTemplate sub = subs.get(getClass());
    if (sub == null) {
      subs.put(getClass(), this);
    } else {
      throw new IllegalStateException();
    }
    onInit0();
    state = PluginState.INITIATED;
  }

  /**
   * Called when initializing this KingdomsX Addon.
   */
  protected abstract void onInit0();

  @Override
  public final void onLoad() {
    state = PluginState.LOADING;
    if (!isKingdomsLoaded()) return;
    super.onLoad();
    onLoad0();
    state = PluginState.LOADED;
  }

  protected abstract void onLoad0();

  @Override
  public final void onEnable() {
    state = PluginState.ENABLING;
    super.onEnable();
    if (!isKingdomsEnabled()) {
      getLogger().severe("Kingdoms plugin didn't load correctly. Disabling...");
      Bukkit.getPluginManager().disablePlugin(this);
      return;
    }
    registerAddon();
    onEnable0();
    state = PluginState.ENABLED;
  }

  protected abstract void onEnable0();

  @Override
  public final void onDisable() {
    state = PluginState.DISABLING;
    super.onDisable();
    signalDisable();
    onDisable0();
    disableAddon();
    state = PluginState.DISABLED;
  }

  protected abstract void onDisable0();

  /**
   * Called when reload this KingdomsX addon.
   */
  @Override
  public final void reloadAddon() {
    getLogger().info("Reloading addon " + unboxAddonName(this, true));
    reloadAddon0();
    getLogger().info("Addon " + unboxAddonName(this, false) + " reloaded");
  }

  protected abstract void reloadAddon0();

  /**
   * Called when uninstall this KingdomsX addon.
   */
  @Override
  public final void uninstall() {
    getLogger().info("Uninstall addon " + unboxAddonName(this, true));
    Addon.super.uninstall();
    uninstall0();
    getLogger().info("Addon " + unboxAddonName(this, false) + " uninstalled");
  }

  /**
   * Called when uninstall this KingdomsX addon.
   */
  protected void uninstall0() {
  }

  /**
   * Gets the KingdomsX addon name.
   *
   * @return the addon name
   */
  @Override
  public abstract @NotNull String getAddonName();

  @Override
  public @NotNull File getFile() {
    return super.getFile();
  }

  /**
   * Sends a signal indicating that addon has been disabled for KingdomsX datacenter.
   */
  @Override
  public final void signalDisable() {
    Addon.super.signalDisable();
  }

  /**
   * Returns whether the KingdomsX is loaded.
   *
   * @return weather the KingdomsX is loaded
   */
  @Override
  public final boolean isKingdomsLoaded() {
    return Addon.super.isKingdomsLoaded();
  }

  /**
   * Returns whether the KingdomsX is enabled.
   *
   * @return weather the KingdomsX is enabled
   */
  @Override
  public final boolean isKingdomsEnabled() {
    return Addon.super.isKingdomsEnabled();
  }

  /**
   * Registers the kingdomsX addon.
   */
  @Override
  public final void registerAddon() {
    Addon.super.registerAddon();
  }

  /**
   * Gets the plugin state.
   *
   * @return the plugin state
   */
  public @NotNull PluginState getState() {
    return state;
  }

}
