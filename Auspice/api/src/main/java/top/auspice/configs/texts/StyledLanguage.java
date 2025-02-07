package top.auspice.configs.texts;

import org.jetbrains.annotations.NotNull;

@Deprecated
public interface StyledLanguage extends Language, Styled {
    @Override
    default @NotNull String getName() {
        return this.getLanguageName() + '-' + this.getStyleName();
    }
}
