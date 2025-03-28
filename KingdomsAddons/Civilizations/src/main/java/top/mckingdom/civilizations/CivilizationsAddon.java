package top.mckingdom.civilizations;

import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.namespace.Namespace;
import top.mckingdom.auspice.util.AddonTemplate;
import top.mckingdom.auspice.util.KingdomsNamingContract;
import top.mckingdom.civilizations.config.CivilizationsAddonConfig;
import top.mckingdom.civilizations.constants.civilization.member.CivilizationMemberTypeRegistry;
import top.mckingdom.civilizations.constants.civilization.relation.StandardCivilizationRelation;

import java.io.File;

public class CivilizationsAddon extends AddonTemplate {

  private static CivilizationsAddon instance;

  public CivilizationsAddon() {
    super();
  }

  @Override
  protected void onInit0() {
    instance = this;
  }

  @Override
  public void onLoad0() {
    CivilizationsAddonConfig.init();
    init();
    reloadConfigurationThings();
  }

  @Override
  public void onEnable0() {
  }

  @Override
  public void onDisable0() {
  }

  @Override
  public void reloadAddon0() {
  }

  @Override
  public void uninstall0() {
  }

  @Override
  public @NotNull String getAddonName() {
    return "civilizations";
  }

  @NotNull
  @Override
  public File getFile() {
    return super.getFile();
  }

  @SuppressWarnings("PatternValidation")
  public static @NotNull Namespace buildNS(@KingdomsNamingContract.Namespace.Key final @NotNull String key) {
    return new Namespace("Civilizations", key);
  }

  private static void init() {
    CivilizationMemberTypeRegistry.registerDefaults();
    GroupExt.init();
  }

  private static void reloadConfigurationThings() {
    StandardCivilizationRelation.init();
  }

  public static @NotNull CivilizationsAddon get() {
    if (instance == null) {
      throw new IllegalStateException("");
    }
    return instance;
  }

}
