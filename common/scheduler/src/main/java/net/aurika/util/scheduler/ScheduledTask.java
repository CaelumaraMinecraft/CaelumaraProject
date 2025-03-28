package net.aurika.util.scheduler;

public interface ScheduledTask extends Task, Cancellable {

  ExecutionContextType getExecutionContextType();

  void run();

  boolean cancel();

  boolean isCancelled();

}
