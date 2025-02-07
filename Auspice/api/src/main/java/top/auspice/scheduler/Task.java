package top.auspice.scheduler;

public interface Task extends Runnable {
    public abstract ExecutionContextType getExecutionContextType();

    public abstract void run();

    public static enum ExecutionContextType {
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
