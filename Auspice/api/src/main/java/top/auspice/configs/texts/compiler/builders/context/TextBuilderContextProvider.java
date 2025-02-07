package top.auspice.configs.texts.compiler.builders.context;

import top.auspice.configs.texts.compiler.pieces.TextPiece;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;

public abstract class TextBuilderContextProvider {
    protected TextPlaceholderProvider settings;

    protected TextBuilderContextProvider(TextPlaceholderProvider settings) {
        this.settings = settings;
    }

    public void setSettings(TextPlaceholderProvider settings) {
        this.settings = settings;
    }

    public TextPlaceholderProvider getSettings() {
        return this.settings;
    }

    public abstract void build(TextPiece piece);
}