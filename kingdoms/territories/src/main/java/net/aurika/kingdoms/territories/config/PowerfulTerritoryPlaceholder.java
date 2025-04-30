package net.aurika.kingdoms.territories.config;

import net.aurika.kingdoms.auspice.util.KingdomsNamingContract;
import net.aurika.kingdoms.territories.constant.land.category.StandardLandCategory;
import net.aurika.kingdoms.territories.data.LandCategories;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.constants.land.Land;
import org.kingdoms.locale.placeholders.EnumKingdomsPlaceholderTranslator;
import org.kingdoms.locale.placeholders.KingdomsPlaceholderTranslationContext;
import org.kingdoms.locale.placeholders.KingdomsPlaceholderTranslator;

import java.util.Locale;
import java.util.function.Function;

public enum PowerfulTerritoryPlaceholder implements EnumKingdomsPlaceholderTranslator {
  LAND_CATEGORY(
      StandardLandCategory.NONE, (context) -> {
    @Nullable Land land = context.getLand();
    return land != null ? LandCategories.getCategory(land) : null;
  }
  ),

  ;

  private final @NotNull Function<KingdomsPlaceholderTranslationContext, Object> translator;
  private final @NotNull Object defaultValue;
  private @Nullable Object configuredDefaultValue;

  PowerfulTerritoryPlaceholder(@NotNull Object defaultValue, @NotNull Function<KingdomsPlaceholderTranslationContext, Object> translator) {
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
  @KingdomsNamingContract.PlaceholderName
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
  }
}
