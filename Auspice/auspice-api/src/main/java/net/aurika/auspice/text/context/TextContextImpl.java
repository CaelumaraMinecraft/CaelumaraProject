package net.aurika.auspice.text.context;

import net.aurika.auspice.configs.messages.placeholders.context.PlaceholderContextBuilderImpl;
import net.aurika.common.annotations.Getter;
import net.aurika.common.annotations.Setter;

public class TextContextImpl extends PlaceholderContextBuilderImpl implements TextContext {
    public boolean ignoreColors = false;

    @Getter
    public boolean ignoreColors() {
        return this.ignoreColors;
    }

    @Setter
    public TextContext ignoreColors(boolean ignoreColors) {
        this.ignoreColors = ignoreColors;
        return this;
    }
}
