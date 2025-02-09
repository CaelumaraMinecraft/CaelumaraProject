package top.auspice.commands;

import top.auspice.configs.messages.messenger.Messenger;

public class CommandUserError extends RuntimeException {
    private final CommandContext context;
    private final Messenger error;

    public CommandUserError(String message, CommandContext context, Messenger error) {
        super(message);
        this.context = context;
        this.error = error;
    }

    public CommandUserError(String message, Throwable cause, CommandContext context, Messenger error) {
        super(message, cause);
        this.context = context;
        this.error = error;
    }

    public CommandUserError(Throwable cause, CommandContext context, Messenger error) {
        super(cause);
        this.context = context;
        this.error = error;
    }

    public CommandContext getContext() {
        return this.context;
    }

    public Messenger getError() {
        return this.error;
    }
}