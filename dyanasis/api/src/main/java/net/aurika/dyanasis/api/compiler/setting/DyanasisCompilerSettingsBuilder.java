package net.aurika.dyanasis.api.compiler.setting;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * Builder of {@linkplain DyanasisCompilerSettings}.
 * Note:
 */
public class DyanasisCompilerSettingsBuilder implements Cloneable {

  // Idents
  protected @NotNull String stringLeftIdent = "\"";
  protected @NotNull String stringRightIdent = "\"";

  protected @NotNull String rawVariableLeftIdent = "`";
  protected @NotNull String rawVariableRightIdent = "`";

  protected @NotNull String mapLeftIdent = "{";
  protected @NotNull String mapRightIdent = "}";
  protected @NotNull String mapKeyValueColumn = ":";
  protected @NotNull String mapEntryColumn = ",";

  protected @NotNull String arrayLeftIdent = "[";
  protected @NotNull String arrayRightIdent = "]";
  protected @NotNull String arrayElementColumn = ",";

  protected @NotNull String objectLeftIdent = "'";
  protected @NotNull String objectRightIdent = "'";

  // post precessing
  protected @NotNull Function<String, String> stringPostProcessing = String::translateEscapes;

  public DyanasisCompilerSettingsBuilder() {
  }

  @Contract("_ -> this")
  public DyanasisCompilerSettingsBuilder stringLeftIdent(@NotNull String stringLeftIdent) {
    Validate.Arg.notNull(stringLeftIdent, "stringLeftIdent");
    this.stringLeftIdent = stringLeftIdent;
    return this;
  }

  @Contract("_ -> this")
  public DyanasisCompilerSettingsBuilder stringRightIdent(@NotNull String stringRightIdent) {
    Validate.Arg.notNull(stringRightIdent, "stringRightIdent");
    this.stringRightIdent = stringRightIdent;
    return this;
  }

  @Contract("_ -> this")
  public DyanasisCompilerSettingsBuilder stringIdent(@NotNull String stringIdent) {
    Validate.Arg.notNull(stringIdent, "stringIdent");
    this.stringLeftIdent = stringIdent;
    this.stringRightIdent = stringIdent;
    return this;
  }

  @Contract("_ -> this")
  public DyanasisCompilerSettingsBuilder rawVariableLeftIdent(@NotNull String rawVariableLeftIdent) {
    Validate.Arg.notNull(rawVariableLeftIdent, "rawVariableLeftIdent");
    this.rawVariableLeftIdent = rawVariableLeftIdent;
    return this;
  }

  @Contract("_ -> this")
  public DyanasisCompilerSettingsBuilder rawVariableRightIdent(@NotNull String rawVariableRightIdent) {
    Validate.Arg.notNull(rawVariableRightIdent, "rawVariableRightIdent");
    this.rawVariableRightIdent = rawVariableRightIdent;
    return this;
  }

  @Contract("_ -> this")
  public DyanasisCompilerSettingsBuilder mapLeftIdent(@NotNull String mapLeftIdent) {
    Validate.Arg.notNull(mapLeftIdent, "mapLeftIdent");
    this.mapLeftIdent = mapLeftIdent;
    return this;
  }

  @Contract("_ -> this")
  public DyanasisCompilerSettingsBuilder mapRightIdent(@NotNull String mapRightIdent) {
    Validate.Arg.notNull(mapRightIdent, "mapRightIdent");
    this.mapRightIdent = mapRightIdent;
    return this;
  }

  @Contract("_ -> this")
  public DyanasisCompilerSettingsBuilder mapKeyValueColumn(@NotNull String mapKeyValueColumn) {
    Validate.Arg.notNull(mapKeyValueColumn, "mapKeyValueColumn");
    this.mapKeyValueColumn = mapKeyValueColumn;
    return this;
  }

  @Contract("_ -> this")
  public DyanasisCompilerSettingsBuilder mapEntryColumn(@NotNull String mapEntryColumn) {
    Validate.Arg.notNull(mapEntryColumn, "mapEntryColumn");
    this.mapEntryColumn = mapEntryColumn;
    return this;
  }

  @Contract("_ -> this")
  public DyanasisCompilerSettingsBuilder arrayLeftIdent(@NotNull String arrayLeftIdent) {
    Validate.Arg.notNull(arrayLeftIdent, "arrayLeftIdent");
    this.arrayLeftIdent = arrayLeftIdent;
    return this;
  }

  @Contract("_ -> this")
  public DyanasisCompilerSettingsBuilder arrayRightIdent(@NotNull String arrayRightIdent) {
    Validate.Arg.notNull(arrayRightIdent, "arrayRightIdent");
    this.arrayRightIdent = arrayRightIdent;
    return this;
  }

  @Contract("_ -> this")
  public DyanasisCompilerSettingsBuilder arrayElementColumn(@NotNull String arrayElementColumn) {
    Validate.Arg.notNull(arrayElementColumn, "arrayElementColumn");
    this.arrayElementColumn = arrayElementColumn;
    return this;
  }

  @Contract("_ -> this")
  public DyanasisCompilerSettingsBuilder objectLeftIdent(@NotNull String objectLeftIdent) {
    Validate.Arg.notNull(objectLeftIdent, "objectLeftIdent");
    this.objectLeftIdent = objectLeftIdent;
    return this;
  }

  @Contract("_ -> this")
  public DyanasisCompilerSettingsBuilder objectRightIdent(@NotNull String objectRightIdent) {
    Validate.Arg.notNull(objectRightIdent, "objectRightIdent");
    this.objectRightIdent = objectRightIdent;
    return this;
  }

  @Contract("_ -> this")
  public DyanasisCompilerSettingsBuilder stringPostProcessing(@NotNull Function<String, String> stringPostProcessing) {
    Validate.Arg.notNull(stringPostProcessing, "stringPostProcessing");
    this.stringPostProcessing = stringPostProcessing;
    return this;
  }

  @Contract(value = "-> new", pure = true)
  public DyanasisCompilerSettings build() {
    DyanasisCompilerSettings.Idents idents = new DyanasisCompilerSettings.Idents();
    return new DyanasisCompilerSettings(TODO);
  }

  @Override
  public DyanasisCompilerSettingsBuilder clone() {
    return;
  }

}
