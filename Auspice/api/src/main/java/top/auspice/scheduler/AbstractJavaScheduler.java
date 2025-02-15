package top.auspice.scheduler;

import net.aurika.dependency.classpath.BootstrapProvider;
import net.aurika.util.scheduler.*;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.NotNull;
import net.aurika.util.scheduler.Task.ExecutionContextType;
import net.aurika.util.stacktrace.StackTraces;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.stream.Collectors;

public abstract class AbstractJavaScheduler implements TaskScheduleProvider {
    private final BootstrapProvider a;
    private final ScheduledThreadPoolExecutor b;
    private final ForkJoinPool pool;
    private final b d;
    private boolean e;

    public AbstractJavaScheduler(BootstrapProvider var1) {
        this.a = var1;
        this.b = new ScheduledThreadPoolExecutor(1, (var0) -> {
            Thread thread = Executors.defaultThreadFactory().newThread(var0);
            (thread).setName("auspice-scheduler");
            return thread;
        });
        this.b.setRemoveOnCancelPolicy(true);
        this.b.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
        this.b.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
        this.pool = new ForkJoinPool(16, new e(), new d(), false);
        this.d = new b();
    }

    public TaskScheduler async() {
        return this.d;
    }

    public final boolean isShutdown() {
        return this.e;
    }

    @MustBeInvokedByOverriders
    public void shutdown() {
        if (this.e) {
            throw new IllegalStateException(this + " is already shutdown");
        } else {
            this.e = true;
            this.a(this.b, "-scheduler", "scheduler");
            this.a(this.pool, "-worker-", "worker thread pool");
        }
    }

    private void a(AbstractExecutorService var1, String var2, String var3) {
        var1.shutdown();

        try {
            if (!var1.awaitTermination(1L, TimeUnit.MINUTES)) {
                this.a.getLogger().severe("Timed out waiting for the " + var3 + " to terminate");
                this.a((var1x) -> {
                    return var1x.getName().startsWith("auspice" + var2);
                });
            }

        } catch (InterruptedException var4) {
            var4.printStackTrace();
        }
    }

    private void a(Predicate<Thread> var1) {
        Thread.getAllStackTraces().forEach((var2, var3) -> {
            if (var1.test(var2)) {
                this.a.getLogger().log(Level.WARNING, "Thread " + var2.getName() + " is blocked, and may be the reason for the slow shutdown!\n" + Arrays.stream(var3).map((var0) -> {
                    return "  " + var0;
                }).collect(Collectors.joining("\n")));
            }

        });
    }

    private static final class e implements ForkJoinPool.ForkJoinWorkerThreadFactory {
        private static final AtomicInteger a = new AtomicInteger(0);

        private e() {
        }

        public ForkJoinWorkerThread newThread(ForkJoinPool var1) {
            ForkJoinWorkerThread var2;
            (var2 = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(var1)).setDaemon(true);
            var2.setName("kingdoms-worker-" + a.getAndIncrement());
            StackTraces.linkThreads(Thread.currentThread(), var2);
            return var2;
        }
    }

    private final class d implements Thread.UncaughtExceptionHandler {
        private d() {
        }

        public void uncaughtException(Thread var1, Throwable var2) {
            try {
                AbstractJavaScheduler.this.a.getLogger().log(Level.WARNING, "Thread " + var1.getName() + " threw an uncaught exception", var2);
            } catch (Throwable var3) {
                var3.printStackTrace();
            }
        }
    }

    private final class b implements TaskScheduler {
        private b() {
        }

        @NotNull
        public ExecutionContextType getExecutionContextType() {
            return ExecutionContextType.ASYNC;
        }

        public @NotNull Executor getExecutor() {
            return AbstractJavaScheduler.this.pool;
        }

        public @NotNull Task execute(@NotNull Runnable var1) {
            AbstractJavaScheduler.this.pool.execute(new TracedRunnable(var1));
            return new AbstractTask(this.getExecutionContextType(), var1);
        }

        public @NotNull DelayedTask delayed(@NotNull Duration delay, @NotNull Runnable runnable) {
            TracedRunnable var3 = new TracedRunnable(runnable);
            ScheduledFuture<?> var4 = AbstractJavaScheduler.this.b.schedule(() -> {
                AbstractJavaScheduler.this.pool.execute(var3);
            }, delay.toMillis(), TimeUnit.MILLISECONDS);
            return new a(runnable, delay, this.getExecutionContextType(), var4);
        }

        public @NotNull DelayedRepeatingTask repeating(@NotNull Duration var1, @NotNull Duration var2, @NotNull Runnable var3) {
            TracedRunnable var4 = new TracedRunnable(var3);
            ScheduledFuture<?> var5 = AbstractJavaScheduler.this.b.scheduleAtFixedRate(() -> {
                AbstractJavaScheduler.this.pool.execute(var4);
            }, var1.toMillis(), var2.toMillis(), TimeUnit.MILLISECONDS);
            return new c(var3, var1, var2, this.getExecutionContextType(), var5);
        }
    }

    private static final class c extends AbstractDelayedRepeatingTask {
        private final ScheduledFuture<?> a;

        public c(@NotNull Runnable var1, @NotNull Duration var2, @NotNull Duration var3, @NotNull ExecutionContextType var4, ScheduledFuture<?> var5) {
            super(var1, var2, var3, var4);
            this.a = var5;
        }

        public boolean cancel() {
            this.a.cancel(true);
            return super.cancel();
        }
    }

    private static final class a extends AbstractDelayedTask {
        private final ScheduledFuture<?> a;

        public a(@NotNull Runnable var1, @NotNull Duration var2, @NotNull ExecutionContextType var3, ScheduledFuture<?> var4) {
            super(var1, var2, var3);
            this.a = var4;
        }

        public boolean cancel() {
            this.a.cancel(true);
            return super.cancel();
        }
    }
}
