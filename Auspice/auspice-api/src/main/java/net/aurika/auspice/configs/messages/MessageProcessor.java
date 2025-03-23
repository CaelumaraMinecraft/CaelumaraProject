package net.aurika.auspice.configs.messages;

import net.aurika.validate.Validate;
import net.aurika.auspice.text.TextObject;
import net.aurika.auspice.text.compiler.TextCompiler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MessageProcessor {
    protected final @NotNull String originalString;
    protected final @NotNull PrefixProvider prefixProvider;
    protected final @NotNull String textCompilerString;
    protected final @Nullable Boolean usePrefix;
    // compiling (late init)
    protected TextCompiler textCompiler;
    protected TextObject compiledTextObject;

    public MessageProcessor(@NotNull String originalString, @NotNull PrefixProvider prefixProvider) {
        this(originalString, prefixProvider, "PREFIX|", "NOPREFIX|");
    }

    public MessageProcessor(@NotNull String originalString, @NotNull PrefixProvider prefixProvider, @NotNull String usePrefixSign, @NotNull String noPrefixSign) {
        Validate.Arg.notNull(originalString, "originalString");
        Validate.Arg.notNull(prefixProvider, "prefixProvider");
        Validate.Arg.notNull(usePrefixSign, "usePrefixSign");
        Validate.Arg.notNull(noPrefixSign, "noPrefixSign");

        this.originalString = originalString;
        this.prefixProvider = prefixProvider;
        if (originalString.startsWith(usePrefixSign)) {
            this.usePrefix = true;
            this.textCompilerString = originalString.substring(usePrefixSign.length());
        } else if (originalString.startsWith(noPrefixSign)) {
            this.usePrefix = false;
            this.textCompilerString = originalString.substring(noPrefixSign.length());
        } else {
            this.usePrefix = null;
            this.textCompilerString = originalString;
        }
    }

    public @NotNull MessageObject toMessageObject() {
        if (this.textCompiler == null || this.compiledTextObject == null) {
            throw new IllegalStateException("Message compiler was not already compiled");
        }
        return new MessageObject(this.compiledTextObject, this.prefixProvider, this.usePrefix);
    }

    public void compile() {
        this.textCompiler = new TextCompiler(this.textCompilerString);
        this.compiledTextObject = this.textCompiler.compileObject();
    }

    public static @NotNull MessageObject compile(@NotNull String originalString, @NotNull PrefixProvider prefixProvider) {
        MessageProcessor messageProcessor = new MessageProcessor(originalString, prefixProvider);
        messageProcessor.compile();
        return messageProcessor.toMessageObject();
    }
}
