package net.aurika.util.cache;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public final class RunnableCountDownLatch {

  private final @NotNull Consumer<RunnableCountDownLatch> runnable;
  private final @NotNull AtomicInteger countdown;
  private final @NotNull AtomicInteger total;

  public RunnableCountDownLatch(int countdown, @NotNull Consumer<RunnableCountDownLatch> runnable) {
    Validate.Arg.require(countdown > 0, "Countdown number must be greater than zero");
    Validate.Arg.notNull(runnable, "runnable");
    this.countdown = new AtomicInteger(countdown);
    this.total = new AtomicInteger(countdown);
    this.runnable = runnable;
  }

  public void increase() {
    countdown.incrementAndGet();
    total.incrementAndGet();
  }

  public void countDown() {
    if (countdown.get() <= 0) throw new IllegalStateException("Already down to zero: " + countdown.get());
    if (countdown.decrementAndGet() == 0) runnable.accept(this);
  }

  public @NotNull AtomicInteger total() {
    return total;
  }

}
