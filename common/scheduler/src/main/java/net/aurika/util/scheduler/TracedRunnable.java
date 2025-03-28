package net.aurika.util.scheduler;

import net.aurika.util.collection.ArrayUtils;
import net.aurika.util.stacktrace.StackTraces;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class TracedRunnable implements Runnable {

  private final Thread currentThread = Thread.currentThread();
  private final StackTraceElement[] stackTraceElements;
  private final Runnable runnable;

  public TracedRunnable(Runnable runnable) {
    this.stackTraceElements = this.currentThread.getStackTrace();
    this.runnable = Objects.requireNonNull(runnable);
  }

  public void run() {
    try {
      StackTraces.linkThreads(this.currentThread, Thread.currentThread());
      this.runnable.run();
    } catch (Throwable var4) {

      try {
        StackTraceElement[] var2 = Arrays.stream(this.stackTraceElements).skip(2L).toArray(StackTraceElement[]::new);
        var4.setStackTrace(ArrayUtils.merge(var4.getStackTrace(), var2));
      } catch (Throwable var3) {
        (new RuntimeException("Fatal exception", var3)).printStackTrace();
      }

      throw var4;
    }
  }

  public String toString() {
    return "TracedRunnable(" + this.runnable + ", " + Arrays.stream(this.stackTraceElements).map(
        StackTraceElement::toString).collect(Collectors.joining("\n")) + ')';
  }

}
