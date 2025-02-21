package net.aurika.auspice.configs.messages;

import net.aurika.auspice.text.TextObject;
import net.aurika.auspice.text.compiler.pieces.TextPiece;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MessageObject {

    protected final @NotNull TextObject text;
    protected final @NotNull PrefixProvider prefixProvider;
    protected final @Nullable Boolean usePrefix;

    public MessageObject(@NotNull TextObject text, @NotNull PrefixProvider prefixProvider, @Nullable Boolean usePrefix) {
        Validate.Arg.notNull(text, "text");
        Validate.Arg.notNull(prefixProvider, "prefixProvider");
        this.text = text;
        this.prefixProvider = prefixProvider;
        this.usePrefix = usePrefix;
    }

    public @NotNull TextObject text() {
        return this.text;
    }

    public @NotNull PrefixProvider prefixProvider() {
        return this.prefixProvider;
    }

    public @Nullable TextObject prefix() {
        return prefixProvider.providePrefix(usePrefix);
    }

    public @Nullable Boolean usePrefix() {
        return this.usePrefix;
    }

    public TextPiece[] pieces() {
        return text.pieces();
    }

    public static @Nullable("messages.length == 0") MessageObject combine(@NotNull MessageObject @NotNull ... messages) {
        Validate.Arg.nonNullArray(messages, "messages");
        int length = messages.length;
        if (length == 0) {
            return null;
        } else {
            MessageObject first = messages[0];
            if (length == 1) {
                return first;
            } else {
                @NotNull TextObject[] texts = new TextObject[length];
                for (int i = 0; i < length; i++) {
                    texts[i] = messages[i].text();
                }
                //noinspection DataFlowIssue
                return new MessageObject(TextObject.combine(texts), first.prefixProvider(), first.usePrefix());
            }
        }
    }
}
