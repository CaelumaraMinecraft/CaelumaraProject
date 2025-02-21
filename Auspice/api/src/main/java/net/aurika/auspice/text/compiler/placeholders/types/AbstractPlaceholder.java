package net.aurika.auspice.text.compiler.placeholders.types;

import kotlin.collections.CollectionsKt;
import net.aurika.auspice.configs.messages.placeholders.context.VariableProvider;
import net.aurika.auspice.text.compiler.PlaceholderTranslationContext;
import net.aurika.auspice.text.compiler.placeholders.Placeholder;
import net.aurika.auspice.text.compiler.placeholders.modifiers.PlaceholderModifier;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        Validate.Arg.notNull(originalString, "originalString");
        Validate.Arg.notNull(pointer, "pointer");
        this.originalString = originalString;
        this.pointer = pointer;
        this.modifiers = Objects.requireNonNull(modifiers);
    }

    @NotNull
    public String getOriginalString() {
        return this.originalString;
    }

    public @Nullable String getPointer() {
        return this.pointer;
    }

    @NotNull
    public List<PlaceholderModifier> getModifiers() {
        return this.modifiers;
    }

    @Nullable
    public Object request(@NotNull VariableProvider variableProvider) {
        Objects.requireNonNull(variableProvider, "variableProvider");
        throw new UnsupportedOperationException("Cannot translate placeholder manually: " + this);
    }

    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    public boolean equals(@Nullable Object var1) {
        throw new UnsupportedOperationException();
    }

    protected final @NotNull String getCommonString(boolean surround, @NotNull String var2) {
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

    public @NotNull String toString() {
        return this.getClass().getSimpleName() + '{' + this.getOriginalString() + '}';
    }
}

