package top.auspice.configs.messages;

import net.aurika.validate.Validate;
import net.aurika.text.TextObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MessageObject {

    protected final @NotNull TextObject text;
    protected final @NotNull PrefixProvider prefixProvider;
    protected @Nullable Boolean usePrefix;

    public MessageObject(@NotNull TextObject text, @NotNull PrefixProvider prefixProvider, @Nullable Boolean usePrefix) {
        Validate.Arg.notNull(text, "text");
        Validate.Arg.notNull(prefixProvider, "prefixProvider");
        this.text = text;
        this.prefixProvider = prefixProvider;
        this.usePrefix = usePrefix;
    }

    public @NotNull TextObject getText() {
        return this.text;
    }

    public @NotNull PrefixProvider getPrefixProvider() {
        return this.prefixProvider;
    }

    public @Nullable Boolean getUsePrefix() {
        return this.usePrefix;
    }

    public void setUsePrefix(@Nullable Boolean usePrefix) {
        this.usePrefix = usePrefix;
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
                    texts[i] = messages[i].getText();
                }
                //noinspection DataFlowIssue
                return new MessageObject(TextObject.combine(texts), first.getPrefixProvider(), first.getUsePrefix());
            }
        }
    }
}
