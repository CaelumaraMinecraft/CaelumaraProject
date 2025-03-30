package net.aurika.kingdoms.auspice.configs;

import net.aurika.kingdoms.auspice.util.land.LandUtil;
import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.constants.group.Kingdom;
import org.kingdoms.constants.land.Land;
import org.kingdoms.locale.placeholders.*;
import org.kingdoms.locale.placeholders.context.MessagePlaceholderProvider;
import org.kingdoms.utils.compilers.ConditionalCompiler;
import org.kingdoms.utils.conditions.ConditionProcessor;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;

public enum AuspicePlaceholder implements EnumKingdomsPlaceholderTranslator {
  KINGDOM_LANDS_COUNT(
      0, new FunctionalPlaceholder() {
    @PhFn
    public @Nullable Object byFilter(KingdomsPlaceholderTranslationContext context, @PhParam(name = "filter") String filterStr) {
      if (filterStr == null || filterStr.isEmpty()) {
        return null;
      }
      Kingdom kingdom = context.getKingdom();
      if (kingdom == null) {
        return null;
      }
      ConditionalCompiler.LogicalOperand filter;
      try {
        filter = ConditionalCompiler.compile(filterStr).evaluate();
      } catch (Exception e) {
        return null;
      }

      @NotNull List<Land> lands = kingdom.getLands();
      int count = 0;
      for (Land land : lands) {
        if (land == null) continue;
        MessagePlaceholderProvider landContext = context.asMessaegeBuilder().clone();
        landContext.setPrimaryTarget(land);
        LandUtil.addMessageContextEntries(land, landContext);
        ConditionProcessor landVariableProcessor = new ConditionProcessor(landContext);
        if (filter.eval(landVariableProcessor)) {
          count++;
        }
      }
      return count;
    }
  }
  );

  private final @NotNull Function<KingdomsPlaceholderTranslationContext, Object> translator;
  private final @NotNull Object defaultValue;
  private @Nullable Object configuredDefaultValue;

  AuspicePlaceholder(@NotNull Object defaultValue, @NotNull Function<KingdomsPlaceholderTranslationContext, Object> translator) {
    this.translator = translator;
    this.defaultValue = defaultValue;
    KingdomsPlaceholderTranslator.register(this);
  }

  @Override  // val translator get()
  public @NotNull Function<KingdomsPlaceholderTranslationContext, Object> getTranslator() {
    return this.translator;
  }

  @Override  // val name get()
  @SuppressWarnings("PatternValidation")
  @Pattern("[a-z0-9]")
  public @NotNull String getName() {
    return name().toLowerCase(Locale.ENGLISH);
  }

  @Override  // val default get()
  public @NotNull Object getDefault() {
    return defaultValue;
  }

  @Override  // var configuredDefaultValue get()
  public @Nullable Object getConfiguredDefaultValue() {
    return configuredDefaultValue;
  }

  @Override  // var configuredDefaultValue set(Object)
  public void setConfiguredDefaultValue(@Nullable Object ConfiguredDefaultValue) {
    this.configuredDefaultValue = ConfiguredDefaultValue;
  }

  public static void init() {
    // <clinit>
  }
}
