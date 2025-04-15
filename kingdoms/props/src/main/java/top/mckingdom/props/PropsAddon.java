package top.mckingdom.props;

import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.namespace.Namespace;
import net.aurika.kingdoms.auspice.util.AddonTemplate;
import net.aurika.kingdoms.auspice.util.KingdomsNamingContract;
import net.aurika.kingdoms.auspice.util.permission.XKingdomPermission;

import static net.aurika.kingdoms.auspice.configs.MsgConst.E_COLOR;
import static net.aurika.kingdoms.auspice.configs.MsgConst.S_COLOR;

public class PropsAddon extends AddonTemplate {

  private static PropsAddon instance;

  @KingdomsNamingContract.Namespace.Group
  public static final String NAMESPACE = "Props";

  public static final XKingdomPermission PERMISSION_USE_PROPS = XKingdomPermission.create(
      buildNS("USE_PROPS"),
      "Props",
      S_COLOR + "Permission of using props.",
      E_COLOR + "You don't have permission to use kingdom props."
  );

  public static @NotNull PropsAddon get() {
    if (instance == null) {
      throw new IllegalStateException("Props Addon instance has not been initialized.");
    } else {
      return instance;
    }
  }

  @SuppressWarnings("PatternValidation")
  public static @NotNull Namespace buildNS(@KingdomsNamingContract.Namespace.Key final @NotNull String key) {
    return new Namespace(NAMESPACE, key);
  }

  public PropsAddon() {
    super();
  }

  @Override
  protected void onInit0() {
    instance = this;
  }

  @Override
  protected void onLoad0() {
  }

  protected void onEnable0() {
  }

  @Override
  protected void onDisable0() {
  }

  @Override
  protected void reloadAddon0() {
  }

  @Override
  protected void uninstall0() {
  }

  @Override
  public @NotNull String getAddonName() {
    return "PropsAddon";
  }

  public void registerEventHandlers() {

  }

}
