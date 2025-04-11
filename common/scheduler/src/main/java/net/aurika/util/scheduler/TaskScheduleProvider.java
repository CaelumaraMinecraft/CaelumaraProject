package net.aurika.util.scheduler;

public interface TaskScheduleProvider {

  TaskScheduler async();

  TaskScheduler sync();

  default void run(TaskThreadType type, Runnable runnable) {
    if (type == TaskThreadType.ANY) {
      runnable.run();
    } else {
      boolean isMainThread = Server.get().isMainThread();
      boolean isAsync = type == TaskThreadType.ASYNC;
      if (isMainThread == !isAsync) {
        runnable.run();
      } else {
        (isAsync ? this.async() : this.sync()).execute(runnable);
      }
    }
  }

  boolean isShutdown();

  void shutdown();

}