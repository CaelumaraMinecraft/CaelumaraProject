package net.aurika.auspice.text.compiler;

import org.jetbrains.annotations.Contract;

import java.util.function.Consumer;

public class TextCompilerSettings {

  protected TextTokenHandler[] tokenHandlers;
  /**
   * 是否进行验证
   * 这代表了:
   * <p>
   * 编译一个字符串时是否报告完成过程中产生的问题
   */
  protected boolean validate;
  protected boolean colorize;
  protected boolean plainOnly = true;
  protected boolean translatePlaceholders;
  protected boolean allowNewLines;
  protected Consumer<TextCompiler> errorHandler;
  public static final TextCompilerSettings NONE_SETTINGS = new TextCompilerSettings(
      false, true, false, false, false, null);

  public TextCompilerSettings(boolean validate, boolean plainOnly, boolean colorize, boolean translatePlaceholders, boolean allowNewLines, TextTokenHandler[] tokenHandlers) {
    this.tokenHandlers = tokenHandlers;
    this.validate = validate;
    this.plainOnly = plainOnly;
    this.colorize = colorize;
    this.allowNewLines = allowNewLines;
    this.translatePlaceholders = translatePlaceholders;
  }

  public static TextCompilerSettings all() {
    return new TextCompilerSettings(true, false, true, true, true, null);
  }

  public static TextCompilerSettings none() {
    return new TextCompilerSettings(false, true, false, false, false, null);
  }

  public static TextCompilerSettings command() {
    return new TextCompilerSettings(true, true, false, true, false, null);
  }

  @Contract("-> this")
  public TextCompilerSettings validate() {
    this.validate = true;
    return this;
  }

  @Contract("-> this")
  public TextCompilerSettings dontValidate() {
    this.validate = false;
    return this;
  }

  @Contract("-> this")
  public TextCompilerSettings colorize() {
    this.colorize = true;
    return this;
  }

  @Contract("-> this")
  public TextCompilerSettings colorless() {
    this.colorize = false;
    return this;
  }

  @Contract("-> this")
  public TextCompilerSettings hovers() {
    this.plainOnly = false;
    return this;
  }

  /**
   * Set only plain message (No hover).
   */
  @Contract("-> this")
  public TextCompilerSettings plainOnly() {
    this.plainOnly = true;
    return this;
  }

  @Contract("-> this")
  public TextCompilerSettings translatePlaceholders() {
    this.translatePlaceholders = true;
    return this;
  }

  @Contract("-> this")
  public TextCompilerSettings dontTranslatePlaceholders() {
    this.translatePlaceholders = false;
    return this;
  }

  @Contract("-> this")
  public TextCompilerSettings allowNewLines() {
    this.allowNewLines = true;
    return this;
  }

  @Contract("_ -> this")
  public TextCompilerSettings withTokenHandlers(TextTokenHandler[] tokenHandlers) {
    if (this.tokenHandlers != null) {
      throw new IllegalStateException("Overriding token handlers");
    } else {
      this.tokenHandlers = tokenHandlers;
      return this;
    }
  }

  @Contract("_ -> this")
  public TextCompilerSettings withErrorHandler(Consumer<TextCompiler> errorHandler) {
    this.errorHandler = errorHandler;
    return this;
  }

  @Contract("_ -> this")
  public TextCompilerSettings withTokenHandler(TextTokenHandler... tokenHandlers) {
    return this.withTokenHandlers(tokenHandlers);
  }

  public TextTokenHandler[] getTokenHandlers() {
    return this.tokenHandlers;
  }

}

