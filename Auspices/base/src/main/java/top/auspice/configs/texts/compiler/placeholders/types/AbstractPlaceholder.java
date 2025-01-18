package top.auspice.configs.texts.compiler.placeholders.types;

import kotlin.collections.CollectionsKt;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.configs.texts.compiler.PlaceholderTranslationContext;
import top.auspice.configs.texts.compiler.placeholders.modifiers.PlaceholderModifier;
import top.auspice.configs.texts.compiler.placeholders.Placeholder;
import top.auspice.configs.texts.placeholders.context.PlaceholderProvider;
import top.auspice.utils.Checker;

import java.util.List;
import java.util.Objects;

public abstract class AbstractPlaceholder implements Placeholder {
    @NotNull
    private final String originalString;
    @Nullable
    private final String pointer;
    @NotNull
    private final List<PlaceholderModifier> modifiers;

    public AbstractPlaceholder(@NotNull String originalString, @Nullable String pointer, @NotNull List<PlaceholderModifier> modifiers) {
        Checker.Argument.checkNotNull(originalString, "originalString");
        Checker.Argument.checkNotNull(pointer, "pointer");
        this.originalString = Objects.requireNonNull(originalString);
        this.pointer = Objects.requireNonNull(pointer);
        this.modifiers = Objects.requireNonNull(modifiers);
    }

    @NotNull
    public String getOriginalString() {
        return this.originalString;
    }

    @Nullable
    public String getPointer() {
        return this.pointer;
    }

    @NotNull
    public List<PlaceholderModifier> getModifiers() {
        return this.modifiers;
    }

    @Nullable
    public Object request(@NotNull PlaceholderProvider provider) {
        Objects.requireNonNull(provider, "");
        throw new UnsupportedOperationException("Cannot translate placeholder manually: " + this);
    }

    @NotNull
    public String toString() {
        return this.getClass().getSimpleName() + '{' + this.getOriginalString() + '}';
    }

    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    public boolean equals(@Nullable Object var1) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    protected final String getCommonString(boolean surround, @NotNull String var2) {
        Objects.requireNonNull(var2, "");
        String var3 = "";
        if (this.getPointer() != null) {
            var3 = var3 + this.getPointer() + '*';
        }

        if (!this.getModifiers().isEmpty()) {
            var3 = var3 + CollectionsKt.joinToString(this.getModifiers(), "@", "", "", -1, "", PlaceholderModifier::getName) + '@';
        }

        return surround ? "%" + var3 + var2 + '%' : var3 + var2;
    }

    @Nullable
    @Contract("")
    public static Object wrapWithDefaultContextProvider(@Nullable Object object) {
        return !(object instanceof String) ? object : PlaceholderTranslationContext.withDefaultContext(object);
    }

}

