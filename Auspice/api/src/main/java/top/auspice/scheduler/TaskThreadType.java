package top.auspice.scheduler;

public enum TaskThreadType {
    /**
     * 同步
     */
    SYNC,
    /**
     * 异步
     */
    ASYNC,
    /**
     * 同步或异步
     */
    ANY
}