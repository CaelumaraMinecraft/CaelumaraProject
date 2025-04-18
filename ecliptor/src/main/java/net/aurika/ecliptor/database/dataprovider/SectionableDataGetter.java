package net.aurika.ecliptor.database.dataprovider;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SectionableDataGetter extends DataGetter, SectionableDataProvider {

  @NotNull SectionableDataGetter get(@NotNull String key);

  /**
   * 将此 {@link DataGetter} 转为 {@link SectionableDataGetter}
   */
  @NotNull SectionableDataGetter asSection();

  default int getInt(@NotNull String key) {
    Validate.Arg.notNull(key, "key");
    return this.get(key).asInt();
  }

  default long getLong(@NotNull String key) {
    Validate.Arg.notNull(key, "key");
    return this.get(key).asLong();
  }

  default float getFloat(@NotNull String key) {
    Validate.Arg.notNull(key, "key");
    return this.get(key).asFloat();
  }

  default double getDouble(@NotNull String key) {
    Validate.Arg.notNull(key, "key");
    return this.get(key).asDouble();
  }

  default boolean getBoolean(@NotNull String key) {
    Validate.Arg.notNull(key, "key");
    return this.get(key).asBoolean();
  }

  default @Nullable String getString(@NotNull String key) {
    Validate.Arg.notNull(key, "key");
    return this.get(key).asString();
  }

}