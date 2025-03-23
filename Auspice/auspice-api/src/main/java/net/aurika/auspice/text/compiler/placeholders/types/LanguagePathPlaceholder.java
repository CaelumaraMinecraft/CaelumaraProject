package net.aurika.auspice.text.compiler.placeholders.types;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.auspice.text.compiler.placeholders.modifiers.PlaceholderModifier;
import net.aurika.text.messenger.DefaultedMessenger;
import net.aurika.text.messenger.LanguageEntryMessenger;
import net.aurika.text.placeholders.context.PlaceholderProvider;

import java.util.List;
import java.util.Objects;

public final class LanguagePathPlaceholder extends AbstractPlaceholder {
    @NotNull
    private final LanguageEntryMessenger messenger;
    @NotNull
    private final DefaultedMessenger safe;

    public LanguagePathPlaceholder(@NotNull String var1, @NotNull LanguageEntryMessenger pathMessenger, @Nullable String var3, @NotNull List<PlaceholderModifier> var4) {
        super(var1, var3, var4);
        Objects.requireNonNull(pathMessenger);
        this.messenger = pathMessenger;
        this.safe = this.messenger.safe();
    }

    @NotNull
    public DefaultedMessenger getSafe() {
        return this.safe;
    }

    @NotNull
    public String asString(boolean surround) {
        String var10002 = this.messenger.getEntry().asString();
        Intrinsics.checkNotNullExpressionValue(var10002, "");
        String var2 = this.getCommonString(false, var10002);
        return surround ? "{$$" + var2 + '}' : var2;
    }

    @NotNull
    public Object request(@NotNull PlaceholderProvider provider) {
        return this.safe;
    }
}
