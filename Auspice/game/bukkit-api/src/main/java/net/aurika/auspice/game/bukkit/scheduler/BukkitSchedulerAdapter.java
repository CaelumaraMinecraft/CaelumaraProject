package net.aurika.auspice.game.bukkit.scheduler;

import net.aurika.auspice.scheduler.AbstractJavaScheduler;
import net.aurika.util.scheduler.*;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;
import net.aurika.auspice.api.user.AuspiceUser;
import net.aurika.auspice.game.bukkit.loader.PluginAuspiceLoader;
import net.aurika.common.dependency.classpath.BootstrapProvider;
import net.aurika.auspice.scheduler.*;
import net.aurika.util.scheduler.Task.ExecutionContextType;
import net.aurika.auspice.utils.unsafe.time.TickTemporalUnit;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.Executor;

public class BukkitSchedulerAdapter extends AbstractJavaScheduler {
    private final Executor a;
    private final BukkitScheduler b;
    private final PluginAuspiceLoader c;
    private final c d;

    public BukkitSchedulerAdapter(PluginAuspiceLoader var1, BootstrapProvider var2) {
        super(var2);
        Objects.requireNonNull(var1);
        Objects.requireNonNull(var2);
        this.c = var1;
        this.b = var1.getServer().getScheduler();
        this.a = (var1x) -> {
            AuspiceUser.State state = var1.getState();
            if (state != AuspiceUser.State.DISABLING && state != AuspiceUser.State.DISABLED) {
                var1.getServer().getScheduler().scheduleSyncDelayedTask(var1.loaderPlugin(), new TracedRunnable(var1x));
            }
        };
        this.d = new c();
    }

    public TaskScheduler sync() {
        return this.d;
    }

    private final class c implements TaskScheduler {
        private c() {
        }

        @NotNull
        public ExecutionContextType getExecutionContextType() {
            return ExecutionContextType.SYNC;
        }

        @NotNull
        public Executor getExecutor() {
            return BukkitSchedulerAdapter.this.a;
        }

        @NotNull
        public Task execute(@NotNull Runnable runnable) {
            BukkitSchedulerAdapter.this.a.execute(runnable);
            return new AbstractTask(this.getExecutionContextType(), runnable);
        }

        @NotNull
        public DelayedTask delayed(@NotNull Duration delay, @NotNull Runnable runnable) {
            int var3 = BukkitSchedulerAdapter.this.b.scheduleSyncDelayedTask(BukkitSchedulerAdapter.this.c, new TracedRunnable(runnable), TickTemporalUnit.toTicks(delay));
            return new b(runnable, delay, this.getExecutionContextType(), var3, BukkitSchedulerAdapter.this.b);
        }

        @NotNull
        public DelayedRepeatingTask repeating(@NotNull Duration var1, @NotNull Duration var2, @NotNull Runnable var3) {
            int var4 = BukkitSchedulerAdapter.this.b.scheduleSyncRepeatingTask(BukkitSchedulerAdapter.this.c, new TracedRunnable(var3), TickTemporalUnit.toTicks(var1), TickTemporalUnit.toTicks(var2));
            return new a(var3, var1, var2, this.getExecutionContextType(), var4, BukkitSchedulerAdapter.this.b);
        }
    }

    private static final class a extends AbstractDelayedRepeatingTask {
        private final int a;
        private final BukkitScheduler b;

        private a(Runnable var1, Duration var2, Duration var3, ExecutionContextType var4, int var5, BukkitScheduler var6) {
            super(var1, var2, var3, var4);
            this.a = var5;
            this.b = var6;
        }

        public boolean cancel() {
            this.b.cancelTask(this.a);
            return super.cancel();
        }
    }

    private static final class b extends AbstractDelayedTask {
        private final int a;
        private final BukkitScheduler b;

        private b(Runnable var1, Duration var2, ExecutionContextType var3, int var4, BukkitScheduler var5) {
            super(var1, var2, var3);
            this.a = var4;
            this.b = var5;
        }

        public boolean cancel() {
            this.b.cancelTask(this.a);
            return super.cancel();
        }
    }
}

