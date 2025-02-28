package top.mckingdom.props;

import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.namespace.Namespace;
import top.mckingdom.auspice.util.AddonTemplate;
import top.mckingdom.auspice.util.permission.XKingdomPermission;

import static top.mckingdom.auspice.configs.MsgConst.E_COLOR;
import static top.mckingdom.auspice.configs.MsgConst.S_COLOR;

public class PropsAddon extends AddonTemplate {

    private static PropsAddon instance;

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

    public static @NotNull Namespace buildNS(@Pattern("[A-Z0-9_]{3,100}") final @NotNull String key) {
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
    public void onLoad0() {

    }

    public void onEnable0() {

    }

    @Override
    protected void onDisable0() {

    }

    @Override
    public void reloadAddon0() {

    }

    @Override
    public void uninstall0() {

    }

    @Override
    public @NotNull String getAddonName() {
        return "PropsAddon";
    }

    public void registerEventHandlers() {

    }
}
