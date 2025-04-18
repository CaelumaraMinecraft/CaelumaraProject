package net.aurika.auspice.text.compiler;

import net.aurika.auspice.configs.messages.context.MessageContext;
import net.aurika.auspice.configs.messages.placeholders.transformer.PlaceholderTransformerRegistry;
import net.aurika.auspice.translation.messenger.Messenger;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class PlaceholderTranslationContext {

  public static final TextCompilerSettings PLACEHOLDER_SETTINGS = new TextCompilerSettings(
      false, false, true, true, true, null);

  private final Object value;
  private final TextCompilerSettings settings;

  public PlaceholderTranslationContext(Object value, TextCompilerSettings settings) {
    Validate.Arg.notNull(value, "value", "Raw value cannot be null");
    this.value = value;
    this.settings = settings;
  }

  @Contract("_ -> new")
  public static @NotNull PlaceholderTranslationContext withDefaultContext(Object var0) {
    return new PlaceholderTranslationContext(var0, PLACEHOLDER_SETTINGS);
  }

  @Contract("_ -> new")
  public @NotNull PlaceholderTranslationContext copyTo(Object rawValue) {
    return new PlaceholderTranslationContext(rawValue, this.settings);
  }

  public TextCompilerSettings settings() {
    return this.settings;
  }

  public String toString() {
    return PlaceholderTranslationContext.class.getSimpleName() + "{" + this.value + '}';
  }

  public Object getValue() {
    return unwrapPlaceholder(this.value);
  }

  public static Object unwrapPlaceholder(Object obj) {
    return PlaceholderTransformerRegistry.INSTANCE.applyTransformation(obj);
  }

  public static Object unwrapContextualPlaceholder(Object var0, MessageContext context) {
    if ((var0 = unwrapPlaceholder(var0)) instanceof Messenger) {
      var0 = ((Messenger) var0).getMessageObject(context.diversity());
    }

    return var0;
  }

}
