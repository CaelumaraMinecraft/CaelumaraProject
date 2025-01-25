package net.aurika.abstraction;

public interface Cancellable {
    /**
     * @return 是否在取消后为取消状态
     */
    boolean cancel();

    boolean isCancelled();
}
