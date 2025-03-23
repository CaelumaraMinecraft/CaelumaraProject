package net.aurika.auspice.text.context;

import net.aurika.auspice.configs.messages.placeholders.context.PlaceholderContextBuilder;
import net.aurika.auspice.configs.messages.placeholders.context.PlaceholderContextBuilderImpl;
import net.aurika.common.annotations.Getter;
import net.aurika.common.annotations.Setter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface TextContext extends PlaceholderContextBuilder, Cloneable {

    @Override
    default PlaceholderContextBuilder inheritVariables(@NotNull PlaceholderContextBuilder other) {
        PlaceholderContextBuilder.super.inheritVariables(other);
        if (other instanceof TextContext) {
            this.ignoreColors(((TextContext) other).ignoreColors());
        }
        return this;
    }

    @Contract("_ -> param1")
    default <T extends TextContext> @NotNull T cloneInto(@NotNull T other) {
        PlaceholderContextBuilder.super.cloneInto((PlaceholderContextBuilderImpl) other);
        other.ignoreColors(this.ignoreColors());
        return other;
    }

    @Getter
    boolean ignoreColors();

    @Setter
    TextContext ignoreColors(boolean ignoreColors);
}
