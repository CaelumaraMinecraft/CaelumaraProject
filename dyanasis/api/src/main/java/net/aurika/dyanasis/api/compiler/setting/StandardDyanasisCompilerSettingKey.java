package net.aurika.dyanasis.api.compiler.setting;

import org.jetbrains.annotations.NotNull;

public enum StandardDyanasisCompilerSettingKey implements DyanasisCompilerSettingKey {
  // logics
  KEYWORD_IF(String.class, "if"),
  KEYWORD_ELSE(String.class, "else"),
  KEYWORD_WHILE(String.class, "while"),
  KEYWORD_FOR(String.class, "for"),
  KEYWORD_RETURN(String.class, "return"),
  // declarations
  KEYWORD_CONSTRUCTOR(String.class, "constructor"),
  /**
   * Function declaration keyword.
   */
  KEYWORD_FUNCTION(String.class, "fun"),
  KEYWORD_PROPERTY_FINAL(String.class, "val"),
  KEYWORD_PROPERTY_MUTABLE(String.class, "var"),
  KEYWORD_PROPERTY_GETTER(String.class, "get"),
  KEYWORD_PROPERTY_SETTER(String.class, "set"),
  KEYWORD_NAMESPACE(String.class, "namespace"),
  KEYWORD_LOCAL_VARIABLE_MUTABLE(String.class, "var"),
  KEYWORD_LOCAL_VARIABLE_FINAL(String.class, "val"),

  PARAMETER_TYPE_DECLARATION_STRATEGY(TypeDeclarationStrategy.class, TypeDeclarationStrategy.SUFFIX),
  PROPERTY_TYPE_DECLARATION_STRATEGY(TypeDeclarationStrategy.class, TypeDeclarationStrategy.SUFFIX),
  FUNCTION_TYPE_DECLARATION_STRATEGY(TypeDeclarationStrategy.class, TypeDeclarationStrategy.SUFFIX),
  ;
  private final @NotNull Class<?> type;
  private final @NotNull Object defaultValue;

  <T> StandardDyanasisCompilerSettingKey(@NotNull Class<T> type, @NotNull T defaultValue) {
    this.type = type;
    this.defaultValue = defaultValue;
  }

  public @NotNull Class<?> type() {
    return this.type;
  }

  public @NotNull Object defaultValue() {
    return this.defaultValue;
  }
}
