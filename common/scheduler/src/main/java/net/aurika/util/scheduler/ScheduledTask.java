package net.aurika.util.scheduler;

public interface ScheduledTask extends Task, Cancellable {

  ExecutionContextType getExecutionContextType();

  @Override
  void run();

  boolean cancel();

  boolean isCancelled();

}
