package top.auspice.configs.texts.compiler.container;

import top.auspice.configs.texts.compiler.TextObject;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;

public class SimpleTextContainer implements TextContainer {
    private final TextObject text;

    public SimpleTextContainer(TextObject text) {
        this.text = text;
    }

    public TextObject get(TextPlaceholderProvider messageContext) {
        return this.text;
    }
}
