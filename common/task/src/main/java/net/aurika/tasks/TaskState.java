package net.aurika.tasks;

public enum TaskState {
    CONTINUE,
    ERROR,
    SHOULD_STOP,
    MUST_STOP;

    public boolean shouldStop() {
        return this != CONTINUE;
    }
}
