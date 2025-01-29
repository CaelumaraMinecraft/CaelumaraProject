package net.aurika.tasks;

public enum TaskState {
    CONTINUE,
    ERROR,
    SHOULD_STOP,
    MUST_STOP,

    ;

    public final boolean shouldStop() {
        return this != CONTINUE;
    }
}
