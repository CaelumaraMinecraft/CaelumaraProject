package net.aurika.dyanasis.api.compiler.setting;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class DyanasisCompilerSettings {

  public static final DyanasisCompilerSettings DEFAULT = DyanasisCompilerSettings.builder().build();

  public static DyanasisCompilerSettingsBuilder builder() {
    return new DyanasisCompilerSettingsBuilder();
  }

  protected final @NotNull Idents idents;

  public @NotNull Idents idents() { return idents; }

  public DyanasisCompilerSettings(@NotNull Idents idents) { this.idents = idents; }

  public class Idents {

    // Operators
    protected final @NotNull String transfer;               // transfer to a function         default "@"
    protected final @NotNull String reflection;             // reflection                     default "::"
    protected final @NotNull String gotoLabel;              // goto a label                   default "@"
    protected final @NotNull String labelDeclaration;       // label declaration              default ":";
    protected final @NotNull String domainAnalysis;         // property function namespace    default "."
    protected final @NotNull String functionInputLeft;      // function input left            default "("
    protected final @NotNull String functionInputRight;     // function input right           default ")"

    // Logics
    protected final @NotNull String IF;                     // if                             default "if"
    protected final @NotNull String ELSE;                   // else                           default "else"
    protected final @NotNull String DO;                     // do                             default "do"
    protected final @NotNull String WHILE;                  // while                          default "while"
    protected final @NotNull String SWITCH;                 // switch                         default "switch"
    protected final @NotNull String CASE;                   // case                           default "case"
    protected final @NotNull String DEFAULT;                // default                        default "default"
    protected final @NotNull String FOR;                    // for                            default "for"
    protected final @NotNull String IN;                     // in                             default "in"
    protected final @NotNull String BREAK;                  // break                          default "break"
    protected final @NotNull String CONTINUE;               // continue                       default "continue"
    protected final @NotNull String RETURN;                 // return                         default "return"
    protected final @NotNull String TRY;                    // try                            default "try"
    protected final @NotNull String CATCH;                  // catch                          default "catch"
    protected final @NotNull String FINALLY;                // finally                        default "finally"
    protected final @NotNull String THROW;                  // throw                          default "throw"

    // Parameter Declarations
    protected final @NotNull String parameterListLeft;      // parameter list left            default "("
    protected final @NotNull String parameterListRight;     // parameter list right           default ")"
    protected final @NotNull String parameterSeparator;     // parameter separator            default ","
    protected final @NotNull String parameterTypeSeparator; // parameter type declaration     default ":"
    @ApiStatus.Experimental
    protected final @NotNull String parameterDefault;       // parameter default              default "="

    protected final @NotNull String self;                   // get this                       default "this"

    protected final @NotNull String reflectType;            // reflect type of an object      default "type"

    // Constants
    protected final @NotNull String nil;                    // null                           default "null"
    protected final @NotNull String trueValue;              // true                           default "true"
    protected final @NotNull String falseValue;             // false                          default "false"

    // Objects
    protected final @NotNull String stringLeft;             // string left surround           default "\""
    protected final @NotNull String stringRight;            // string right surround          default "\""

    protected final @NotNull String rawInvokeLeft;          // raw invoke left                default "`"
    protected final @NotNull String rawInvokeRight;         // raw invoke right               default "`"

    protected final @NotNull String mapLeft;                // map left surround              default "{"
    protected final @NotNull String mapRight;               // map right surround             default "}"
    protected final @NotNull String mapKeyValueColumn;      // map key-value column           default ":"
    protected final @NotNull String mapEntryColumn;         // map entry column               default ","

    protected final @NotNull String arrayLeft;              // array left surround            default "["
    protected final @NotNull String arrayRight;             // array right surround           default "]"
    protected final @NotNull String arrayElementColumn;     // array element column           default ","

    protected final @NotNull String customObjectLeft;       // custom object left surround    default "'"
    protected final @NotNull String customObjectRight;      // custom object right surround   default "'"

    public Idents(
        @NotNull String stringLeft,
        @NotNull String stringRight,
        @NotNull String rawInvokeLeft,
        @NotNull String rawInvokeRight,
        @NotNull String mapLeft,
        @NotNull String mapRight,
        @NotNull String mapKeyValueColumn,
        @NotNull String mapEntryColumn,
        @NotNull String arrayLeft,
        @NotNull String arrayRight,
        @NotNull String arrayElementColumn,
        @NotNull String customObjectLeft,
        @NotNull String customObjectRight
    ) {
      this.stringLeft = stringLeft;
      this.stringRight = stringRight;
      this.rawInvokeLeft = rawInvokeLeft;
      this.rawInvokeRight = rawInvokeRight;
      this.mapLeft = mapLeft;
      this.mapRight = mapRight;
      this.mapKeyValueColumn = mapKeyValueColumn;
      this.mapEntryColumn = mapEntryColumn;
      this.arrayLeft = arrayLeft;
      this.arrayRight = arrayRight;
      this.arrayElementColumn = arrayElementColumn;
      this.customObjectLeft = customObjectLeft;
      this.customObjectRight = customObjectRight;
    }

    public @NotNull String self() { return self; }

    public @NotNull String invoke() { return domainAnalysis; }

    public @NotNull String transfer() { return transfer; }

    public @NotNull String functionInputLeft() { return functionInputLeft; }

    public @NotNull String functionInputRight() { return functionInputRight; }

    public @NotNull String nil() { return nil; }

    public @NotNull String stringLeft() { return stringLeft; }

    public @NotNull String stringRight() { return stringRight; }

    public @NotNull String rawInvokeLeft() { return rawInvokeLeft; }

    public @NotNull String rawInvokeRight() { return rawInvokeRight; }

    public @NotNull String mapLeft() { return mapLeft; }

    public @NotNull String mapRight() { return mapRight; }

    public @NotNull String mapKeyValueColumn() { return mapKeyValueColumn; }

    public @NotNull String mapEntryColumn() { return mapEntryColumn; }

    public @NotNull String arrayLeft() { return arrayLeft; }

    public @NotNull String arrayRight() { return arrayRight; }

    public @NotNull String arrayElementColumn() { return arrayElementColumn; }

    public @NotNull String objectLeft() { return customObjectLeft; }

    public @NotNull String objectRight() { return customObjectRight; }

    public @NotNull DyanasisCompilerSettings parentSettings() { return DyanasisCompilerSettings.this; }

  }

  public class Lexers {

    protected final @NotNull DyanasisStatementCompiler expression;

    public Lexers(@NotNull DyanasisStatementCompiler expression) {
      this.expression = expression;
    }

    public @NotNull DyanasisCompilerSettings parentSettings() {
      return DyanasisCompilerSettings.this;
    }

  }

}
