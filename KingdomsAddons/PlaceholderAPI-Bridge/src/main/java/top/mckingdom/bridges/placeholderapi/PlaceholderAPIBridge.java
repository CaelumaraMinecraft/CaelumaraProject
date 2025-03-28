package top.mckingdom.bridges.placeholderapi;

import org.jetbrains.annotations.NotNull;
import org.kingdoms.locale.compiler.placeholders.Placeholder;
import top.mckingdom.auspice.util.AddonTemplate;

public class PlaceholderAPIBridge extends AddonTemplate {
  private static PlaceholderAPIBridge instance;

  @Override
  protected void onInit0() {
    instance = this;
  }

  @Override
  protected void onLoad0() {
  }

  @Override
  protected void onEnable0() {
    Placeholder
  }

  @Override
  protected void reloadAddon0() {
  }

  @Override
  protected void onDisable0() {
    super.onDisable();
  }

  @Override
  public @NotNull String getAddonName() {
    return "PlaceholderAPI-Bridge";
  }

}