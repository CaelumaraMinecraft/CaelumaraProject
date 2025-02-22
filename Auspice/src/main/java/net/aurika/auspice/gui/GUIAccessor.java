package net.aurika.auspice.gui;

import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import net.aurika.auspice.gui.objects.GUIObject;
import net.aurika.auspice.configs.texts.Locale;
import net.aurika.text.placeholders.context.TextPlaceholderProvider;

import java.io.File;

public final class GUIAccessor {
    private static final GUIObject a;

    public GUIAccessor() {
    }

    public static InteractiveGUI prepare(Player var0, String var1) {
        return prepare(var0, var1, (new TextPlaceholderProvider()).withContext(var0));
    }

    public static InteractiveGUI prepare(Player var0, GUIPathContainer var1) {
        return prepare(var0, var1.getGUIPath());
    }

    public static InteractiveGUI prepare(Player var0, GUIPathContainer var1, TextPlaceholderProvider var2) {
        return prepare(var0, var1.getGUIPath(), var2);
    }

    public static InteractiveGUI prepare(Player var0, OfflinePlayer var1, String var2, Object... var3) {
        return prepare(var0, var2, (new TextPlaceholderProvider()).raws(var3).withContext(var1));
    }

    public static InteractiveGUI prepare(Player var0, String var1, TextPlaceholderProvider var2) {
        return prepare(var0, var1, false, var2);
    }

    public static @Nullable InteractiveGUI prepare(Player var0, String var1, boolean var2, TextPlaceholderProvider var3) {
        return prepare(var0, var1, var2, var3, false);
    }

    public static @Nullable InteractiveGUI prepare(Player var0, String var1, boolean var2, TextPlaceholderProvider var3, boolean var4) {
        Locale var5 = LanguageManager.localeOf(var0);
        GUIObject var7;
        if ((var7 = GUIConfig.getGUI(var1, var5, var2)) == null) {
            return null;
        } else {
            if (!var7.hasFormBuilder() || !Platform.BEDROCK.isAvailable() || !FloodgateHandler.isFloodgatePlayer(var0.getUniqueId())) {
                KLogger.debug(KingdomsDebug.BEDROCK$FLOODGATE, () -> {
                    return "Inventory GUI Only: hasFormBuilder=" + var7.hasFormBuilder() + ", Bedrock=" + Platform.BEDROCK.isAvailable() + ", isFloodgatePlayer=" + (Platform.BEDROCK.isAvailable() ? FloodgateHandler.isFloodgatePlayer(var0.getUniqueId()) : "no");
                });
                var4 = true;
            }

            if (CommandAdminTrack.isTracking(var0)) {
                String var8 = CommandAdminOpenFile.sanitize(var7.getConfig().getFile().toPath());
                String var6 = var7.getConfig().getFile().getPath();
                KingdomsLang.COMMAND_ADMIN_TRACK_GUI.sendMessage(var0, new Object[]{"path", var6.replace("/", "{$sep}/{$s}"), "file", var8, "sanitized_path", var8});
            }

            KLogger.debug(KingdomsDebug.GUIS_PREPARE, () -> {
                return "Preparing GUI '" + var1 + "' for player " + var0.getName() + " and edits " + var3;
            });
            KingdomPlayer var9 = KingdomPlayer.getKingdomPlayer(var0);
            if (((PlaceholderContextBuilder)var3).getPrimaryTarget() == null) {
                var3.withContext(var0);
            }

            var3.lang(var9.getLanguage());
            if (var7.isBroken()) {
                var3.raw("gui_path", var7.getConfig().getFile().toString()).raw("error", var7.getMessage().build(new TextPlaceholderProvider()));
                var7 = a;
                Object var11;
                if (var4) {
                    var11 = new InventoryInteractiveGUI(var7, var0, var7.getConfig().getConfig(), var3);
                } else {
                    var11 = new FormInteractiveGUI(var7, var0, var7.getConfig().getConfig(), var3);
                }

                ((InteractiveGUI)var11).open();
                return (InteractiveGUI)var11;
            } else {
                if (!var9.isAdmin()) {
                    if (var7.isCreativeDisallowed() && var0.getGameMode() == GameMode.CREATIVE && !KingdomsDefaultPluginPermission.GUIS_BYPASS_CREATIVE.hasPermission(var0, false)) {
                        KingdomsLang.GUIS_CREATIVE.sendError(var0, new Object[0]);
                        return null;
                    }

                    Messenger var10;
                    if (var7.getOpenConditions() != null && (var10 = (Messenger)var7.getOpenConditions().evaluate(var3, true)) != null) {
                        var10.sendError(var0, var3);
                        return null;
                    }
                }

                return (InteractiveGUI)(var4 ? new InventoryInteractiveGUI(var7, var0, var7.getConfig().getConfig(), var3) : new FormInteractiveGUI(var7, var0, var7.getConfig().getConfig(), var3));
            }
        }
    }

    static {
        YamlResource var10000 = (new YamlResource((File)null, "guis/error.yml")).load();
        Language.getDefault();
        a = GUIParser.a(var10000, "<INTERNAL:ERROR>");
    }
}

