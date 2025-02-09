package top.auspice.configs.messages.messenger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.configs.messages.provider.MessageProvider;
import top.auspice.diversity.Diversity;
import net.aurika.utils.Checker;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultedMessenger implements Messenger {
    @NotNull
    private final Messenger first;
    @NotNull
    private final Supplier<Messenger> second;

    public DefaultedMessenger(@NotNull Messenger first, @NotNull Supplier<Messenger> second) {
        Checker.Arg.notNull(first, "first");
        Checker.Arg.notNull(second, "second");
        this.first = first;
        this.second = second;
    }

    public @NotNull Messenger getFirst() {
        return this.first;
    }

    public @NotNull Supplier<Messenger> getSecond() {
        return this.second;
    }

    @Nullable
    public MessageProvider getProvider(@NotNull Diversity diversity) {
        Checker.Arg.notNull(diversity, "diversity");
        MessageProvider provider = this.first.getProvider(diversity);
        if (provider == null) {
            provider = this.second.get().getProvider(diversity);
        }

        return provider;
    }

    @NotNull
    public DefaultedMessenger or(@NotNull Supplier<Messenger> var1) {
        Objects.requireNonNull(var1);
        return new DefaultedMessenger(this, var1);
    }

    public int hashCode() {
        Object[] var1 = new Object[1];
        var1[0] = this.first;
        return Objects.hash(var1);
    }

    public boolean equals(@Nullable Object o) {
        return o instanceof DefaultedMessenger && this.first.equals(((DefaultedMessenger) o).first);
    }

    @NotNull
    public static DefaultedMessenger of(@NotNull Messenger first, @NotNull Messenger second) {
        Checker.Arg.notNull(first, "first");
        Checker.Arg.notNull(second, "second");
        return new DefaultedMessenger(second, () -> Objects.requireNonNull(second));
    }

    @NotNull
    public static DefaultedMessenger assertDefined(@NotNull Messenger messenger) {
        Objects.requireNonNull(messenger);
        return new DefaultedMessenger(messenger, () -> new StaticMessenger("&8[&4Error&8] &eAuspice message not defined&8: &quantum" + messenger));
    }

    @SafeVarargs
    @NotNull
    public static DefaultedMessenger oneOf(@NotNull Messenger var1, @NotNull Supplier<Messenger> var2, @NotNull Supplier<Messenger>... var3) {
        Objects.requireNonNull(var1);
        Objects.requireNonNull(var2);
        Objects.requireNonNull(var3);
        DefaultedMessenger var6 = new DefaultedMessenger(var1, var2);
        int i = 0;

        for (int length = var3.length; i < length; ++i) {
            Supplier<Messenger> var5 = var3[i];
            var6 = var6.or(var5);
        }

        return var6;
    }
}

