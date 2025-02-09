package top.auspice.configs.messages;

import net.aurika.text.TextObject;
import org.jetbrains.annotations.Nullable;

public interface PrefixProvider {
    @Nullable TextObject providePrefix(@Nullable Boolean usePrefix);

    static PrefixProvider of(TextObject prefix, boolean globalSetUsePrefix) {
        return new PrefixProvider() {
            @Override
            public @Nullable TextObject providePrefix(@Nullable Boolean usePrefix) {
                if (usePrefix == null) {
                    return globalSetUsePrefix ? prefix : null;
                } else {
                    return usePrefix ? prefix : null;
                }
            }
        };
    }

    static PrefixProvider alwaysEmpty() {
        class AlwaysEmpty implements PrefixProvider {

            public static final AlwaysEmpty INSTANCE = new AlwaysEmpty();

            private AlwaysEmpty() {
            }

            @Override
            public @Nullable TextObject providePrefix(@Nullable Boolean usePrefix) {
                return null;
            }
        }
        return AlwaysEmpty.INSTANCE;
    }
}
