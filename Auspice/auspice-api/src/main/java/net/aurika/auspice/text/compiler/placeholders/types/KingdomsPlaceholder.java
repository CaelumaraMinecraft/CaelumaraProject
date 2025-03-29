package net.aurika.auspice.text.compiler.placeholders.types;

import net.aurika.auspice.configs.messages.placeholders.KingdomsPlaceholderTranslationContext;
import net.aurika.auspice.configs.messages.placeholders.KingdomsPlaceholderTranslator;
import net.aurika.auspice.configs.messages.placeholders.context.VariableProvider;
import net.aurika.auspice.configs.messages.placeholders.target.BasePlaceholderTargetProvider;
import net.aurika.auspice.configs.messages.placeholders.target.PlaceholderTargetProvider;
import net.aurika.auspice.text.compiler.placeholders.functions.PlaceholderFunctionData;
import net.aurika.auspice.text.compiler.placeholders.functions.PlaceholderFunctionInvoker;
import net.aurika.auspice.text.compiler.placeholders.modifiers.PlaceholderModifier;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * AdvancePlaceholder
 */
public class KingdomsPlaceholder extends AbstractPlaceholder {

  public final @NotNull KingdomsPlaceholderTranslator identifier;
  private final @Nullable PlaceholderFunctionData function;

  public KingdomsPlaceholder(String originalString, @NotNull String pointer, @NotNull List<PlaceholderModifier> modifiers, @NonNull KingdomsPlaceholderTranslator identifier, @Nullable PlaceholderFunctionData function) {
    super(originalString, pointer, modifiers);
    this.identifier = identifier;
    this.function = function;
  }

  public @Nullable PlaceholderFunctionData getFunction() {
    return this.function;
  }

  public @NotNull PlaceholderFunctionInvoker requireFunction(String... names) {
    if (this.function == null) {
      throw this.error("Function '" + Arrays.toString(names) + "' required but none was provided for '" + this.asString(
          true) + '\'');
    }

    if (Arrays.stream(names).noneMatch((x) -> this.function.getFunctionName().equalsIgnoreCase(x))) {
      throw this.error("Function '" + Arrays.toString(names) + "' required but none was provided for '" + this.asString(
          true) + '\'');
    }

    return this.function.newSession(this);
  }

  public @Nullable Object request(@NotNull VariableProvider variableProvider) {
    if (!(variableProvider instanceof PlaceholderTargetProvider)) {
      throw this.error("Cannot use kingdoms placeholder on a non-target provider " + variableProvider);
    }

    BasePlaceholderTargetProvider var3 = ((PlaceholderTargetProvider) variableProvider).getTargetProviderFor(
        this.getPointer());
    Object var4 = this.identifier.translate(new KingdomsPlaceholderTranslationContext(var3, this));
    if (var4 == null && this.getModifiers().isEmpty()) {
      var4 = this.identifier.getDefault();
    }

    if (var4 != null) {
      var4 = wrapWithDefaultContextProvider(var4);
    }

    return var4;
  }

  public @NotNull String asString(boolean surround) {
    return this.getCommonString(surround, this.getOriginalString());
  }

  public @NotNull String toString() {
    return "KingdomsPlaceholder{originalString='" + this.getOriginalString() + "', pointer=" + this.getPointer() + ", identifier=" + this.identifier + ", modifiers=" + this.getModifiers().stream().map(
        PlaceholderModifier::getName).toList() + ", function=" + this.function + '}';
  }

}