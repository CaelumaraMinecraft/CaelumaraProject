package net.aurika.auspice.craftbukkit.managers.chat;

import org.bukkit.event.player.AsyncPlayerChatEvent;
import net.aurika.auspice.craftbukkit.managers.abstraction.MoveSensitiveAction;

import java.util.function.Function;

public class ChatInputHandler<T> extends MoveSensitiveAction {
    protected Function<AsyncPlayerChatEvent, Boolean> onInput;
    protected Runnable onCancel;
    protected boolean sync;
    protected final T session;

    public ChatInputHandler(T session) {
        this.session = session;
    }

    public ChatInputHandler() {
        this(null);
    }

    public T getSession() {
        return this.session;
    }

    public void sync() {
        this.sync = true;
    }

    public void onInput(Function<AsyncPlayerChatEvent, Boolean> action) {
        this.onInput = action;
    }

    public void onCancel(Runnable onCancel) {
        this.onCancel = onCancel;
    }
}