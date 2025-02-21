package net.aurika.auspice.game.bukkit.scheduler;

import org.bukkit.scheduler.BukkitTask;

import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;

public final class AsyncScheduledTasks {
    private static final Map<BukkitTask, Runnable> a = new WeakHashMap<>();

    private AsyncScheduledTasks() {
    }

    public static void addTask(BukkitTask var0, Runnable var1) {
        Objects.requireNonNull(var0);
        Objects.requireNonNull(var1);
        if (a.put(var0, var1) != null) {
            throw new IllegalArgumentException("Task was already added");
        }
    }

    public static Map<BukkitTask, Runnable> getTasks() {
        return a;
    }
}
