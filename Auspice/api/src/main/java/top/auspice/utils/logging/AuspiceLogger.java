package top.auspice.utils.logging;

import org.jetbrains.annotations.ApiStatus;
import top.auspice.configs.globalconfig.AuspiceGlobalConfig;
import top.auspice.configs.messages.MessageHandler;
import top.auspice.permission.DefaultAuspicePluginPermissions;
import top.auspice.server.core.Server;
import top.auspice.utils.debug.DebugNS;
import top.auspice.utils.debug.DebugSettings;
import top.auspice.utils.debug.StacktraceSettings;
import top.auspice.utils.unsafe.stacktrace.StackTraces;

import java.util.logging.Logger;

public final class AuspiceLogger {
    private static Logger logger;

    public static Logger getLogger() {
        return logger;
    }

    @ApiStatus.Internal
    public static void setLogger(Logger logger) {
        AuspiceLogger.logger = logger;
    }


    public static void error(Object message) {
        logger.severe(message.toString());
    }

    public static void warn(Object message) {
        logger.warning(message.toString());
    }

    public static void info(Object message) {
        logger.info(message.toString());
    }

    public static void debug(DebugNS debug, Object message) {
        if (isDebugging()) {
            String debugPrefix = debug == null ? "" : "&8[&5" + debug.getNamespacedKey().asString() + "&8] &6";
            String msg = "&7[&5DEBUG&7] &6" + debugPrefix + message.toString();
            MessageHandler.sendConsolePluginMessage(msg);
            Server.get().getPlayerManager().getOnlinePlayers().stream().filter(DefaultAuspicePluginPermissions.DEBUG::hasPermission).filter((var1x) -> debug == null || DebugSettings.getSettings(var1x).isWhitelist() == DebugSettings.getSettings(var1x).getList().contains(debug)).forEach((var1x) -> MessageHandler.sendPlayerPluginMessage(var1x, msg));
            if (debug != null && StacktraceSettings.INSTANCE.isWhitelist == StacktraceSettings.INSTANCE.list.contains(debug)) {
                StackTraces.printStackTrace();
            }
        }

    }

    public static boolean isDebugging() {
        return AuspiceGlobalConfig.DEBUG.getBoolean();
    }

}
