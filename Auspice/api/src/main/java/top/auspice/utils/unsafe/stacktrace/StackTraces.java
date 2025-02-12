package top.auspice.utils.unsafe.stacktrace;

import net.aurika.checker.Checker;
import net.aurika.util.cache.caffeine.CacheHandler;
import org.jetbrains.annotations.NotNull;
import top.auspice.configs.messages.MessageHandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

public final class StackTraces {
    private StackTraces() {
    }

    public static String stacktrace() {
        StringWriter var0 = new StringWriter();
        PrintWriter var1 = new PrintWriter(var0);
        (new Exception()).printStackTrace(var1);
        return var0.toString();
    }

    public static void linkThreads(Thread var0, Thread var1) {
    }

    public static StackTraceElement createInfoElement(String var0) {
        return new StackTraceElement(var0, "", null, 0);
    }

    public static <T extends Throwable> T absoluteStackTrace(T var0) {
        return var0;
    }

    public static @NotNull List<Throwable> getCausationChain(@NotNull Throwable throwable) {
        Checker.Arg.notNull(throwable, "throwable");
        ArrayList<Throwable> chain = new ArrayList<>(5);
        Set<Throwable> var2 = Collections.newSetFromMap(new IdentityHashMap<>(5));
        Throwable var3 = throwable;

        while (var3 != null && !var2.contains(var3)) {
            chain.add(throwable);
            var3 = throwable.getCause();
            var2.add(var3);
        }

        return chain;
    }

    public static void printStackTrace() {
        printStackTrace(Thread.currentThread().getStackTrace(), null, 1);
    }

    public static void printStackTrace(Throwable throwable) {
        throwable.printStackTrace();
    }

    public static void printStackTrace(String message) {
        printStackTrace(Thread.currentThread().getStackTrace(), message, 1);
    }

    public static void printStackTrace(StackTraceElement[] elements, String message, int skipped) {
        MessageHandler.sendConsolePluginMessage("&f--------------------------------------------");
        if (message != null) {
            MessageHandler.sendConsolePluginMessage(message);
        }

        Arrays.stream(elements).skip(skipped).forEach((var0x) -> {
            String var1 = var0x.getClassName();
            String color;
            if (var1.startsWith("net.minecraft")) {
                color = "&6";
            } else if (var1.startsWith("org.bukkit")) {
                color = "&d";
            } else if (!var1.startsWith("co.aikar") && !var1.startsWith("io.papermc") && !var1.startsWith("com.destroystokyo")) {
                if (var1.startsWith("java")) {
                    color = "&c";
                } else {
                    color = "&2";
                }
            } else {
                color = "&d";
            }

            MessageHandler.sendConsolePluginMessage(color + var0x.getClassName() + "&8.&9" + var0x.getMethodName() + "&8: &5" + var0x.getLineNumber());
        });
        MessageHandler.sendConsolePluginMessage("&f--------------------------------------------");
    }

    public static boolean isCalledFromClass(Class<?> var0) {
        String var1 = var0.getSimpleName().concat(".java");
        return Arrays.stream(Thread.currentThread().getStackTrace()).skip(2L).anyMatch((var1x) -> var1.equalsIgnoreCase(var1x.getFileName()));
    }

    static {
        CacheHandler.newBuilder().weakKeys().build();
    }
}
