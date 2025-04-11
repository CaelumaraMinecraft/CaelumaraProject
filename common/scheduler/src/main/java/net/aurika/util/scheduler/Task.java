package net.aurika.util.scheduler;

public interface Task extends Runnable {

  ExecutionContextType getExecutionContextType();

  @Override
  void run();

  enum ExecutionContextType {
    /**
     * 同步的
     */
    SYNC,
    /**
     * 异步的
     */
    ASYNC
  }

}
