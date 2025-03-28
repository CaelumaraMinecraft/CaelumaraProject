package net.aurika.dyanasis.api.lexer.exception;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public class DyanasisLexerException extends RuntimeException {

  protected final @NotNull String original;
  protected final int errStart;
  protected final int errEnd;
  protected final @NotNull String problem;

  public DyanasisLexerException(@NotNull String original, int errStart, int errEnd, @NotNull String problem) {
    super(message(original, errStart, errEnd, problem));
    this.original = original;
    this.errStart = errStart;
    this.errEnd = errEnd;
    this.problem = problem;
  }

  public static @NotNull String message(@NotNull String original, int errStart, int errEnd, @NotNull String problem) {
    Validate.Arg.notNull(original, "original");
    Validate.Arg.notNull(problem, "problem");
  }

  public @NotNull String original() {
    return original;
  }

  public int errStart() {
    return errStart;
  }

  public int errEnd() {
    return errEnd;
  }

  public @NotNull String problem() {
    return problem;
  }

}
