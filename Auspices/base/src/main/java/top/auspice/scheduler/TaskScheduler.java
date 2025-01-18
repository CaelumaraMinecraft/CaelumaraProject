package top.auspice.scheduler;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;

public interface TaskScheduler {
    public abstract @NotNull Task.ExecutionContextType getExecutionContextType();

    public abstract @NotNull Executor getExecutor();

    public abstract @NotNull Task execute(@NotNull Runnable runnable);

    public abstract @NotNull DelayedTask delayed(@NotNull Duration delay, @NotNull Runnable runnable);

    public abstract @NotNull DelayedRepeatingTask repeating(@NotNull Duration var1, @NotNull Duration var2, @NotNull Runnable var3);

    public default @NotNull <T> CompletableFuture<T> supplyFuture(@NotNull Callable<T> var0) {
        Objects.requireNonNull(var0, "");
        CompletableFuture<T> var10000 = CompletableFuture.supplyAsync(() -> {
            Objects.requireNonNull(var0, "");

            try {
                return var0.call();
            } catch (Exception var1) {
                if (var1 instanceof RuntimeException) {
                    throw (RuntimeException) var1;
                } else {
                    throw new CompletionException(var1);
                }
            }
        }, this.getExecutor());
        Objects.requireNonNull(var10000, "");
        return var10000;
    }

    public default @NotNull CompletableFuture<Void> runFuture(@NotNull Runnable future) {
        Objects.requireNonNull(future);
        CompletableFuture<Void> var10000 = CompletableFuture.runAsync(future, this.getExecutor());
        Objects.requireNonNull(var10000);
        return var10000;
    }

}