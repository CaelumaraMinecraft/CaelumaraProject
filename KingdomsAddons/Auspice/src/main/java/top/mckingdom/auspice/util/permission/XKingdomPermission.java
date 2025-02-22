package top.mckingdom.auspice.util.permission;

import org.bukkit.entity.Player;
import org.kingdoms.config.KingdomsConfig;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.constants.player.KingdomPermission;
import org.kingdoms.locale.messenger.DefinedMessenger;
import org.kingdoms.main.Kingdoms;

public class XKingdomPermission extends KingdomPermission {
    private final DefinedMessenger deniedMessage;
    private final String defaultMessage;
    private final String defaultLore;
    public XKingdomPermission(Namespace namespace, DefinedMessenger deniedMessage, String defaultMessage, String defaultLore) {
        super(namespace);
        this.deniedMessage = deniedMessage;
        this.defaultMessage = defaultMessage;
        this.defaultLore = defaultLore;
    }

    protected static XKingdomPermission reg(Namespace namespace, DefinedMessenger messenger, String defaultMessage, String defaultLore, int hash) {
        XKingdomPermission perm = new XKingdomPermission(namespace, messenger, defaultMessage, defaultLore);
        perm.setHash(hash);
        Kingdoms.get().getPermissionRegistery().register(perm);
        return perm;
    }

    public final void sendDeniedMessage(Player player) {
        this.deniedMessage.sendMessage(player);
        KingdomsConfig.errorSound(player);
    }

    public final DefinedMessenger getDeniedMessage$core() {
        return this.deniedMessage;
    }

    public DefinedMessenger getDeniedMessage() {
        return deniedMessage;
    }

    public String getDefaultMessage() {
        return this.defaultMessage;
    }
}
