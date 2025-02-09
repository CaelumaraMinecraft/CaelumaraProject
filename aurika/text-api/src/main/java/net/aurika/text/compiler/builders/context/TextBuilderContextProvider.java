package net.aurika.text.compiler.builders.context;

import net.aurika.text.compiler.pieces.TextPiece;
import net.aurika.text.placeholders.context.MessagePlaceholderProvider;

public abstract class TextBuilderContextProvider {
    protected MessagePlaceholderProvider settings;

    protected TextBuilderContextProvider(MessagePlaceholderProvider settings) {
        this.settings = settings;
    }

    public void setSettings(MessagePlaceholderProvider settings) {
        this.settings = settings;
    }

    public MessagePlaceholderProvider getSettings() {
        return this.settings;
    }

    public abstract void build(TextPiece piece);
}