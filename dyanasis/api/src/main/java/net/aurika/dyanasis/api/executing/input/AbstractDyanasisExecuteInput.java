package net.aurika.dyanasis.api.executing.input;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Objects;

public abstract class AbstractDyanasisExecuteInput implements DyanasisExecuteInput {

  protected final @NotNull String original;

  public AbstractDyanasisExecuteInput(@NotNull String original) {
    Validate.Arg.notNull(original, "original");
    this.original = original;
  }

  /**
   * Gets an argument from {@code index}.
   *
   * @param index the index
   * @return the argument
   * @throws IndexOutOfBoundsException when the index is out of bound for the arguments
   */
  public @NotNull String stringAtArg(@Range(from = 0, to = 127) int index) throws IndexOutOfBoundsException {
    return argStrings()[index];
  }

  /**
   * Gets the arguments of the dyanasis function input.
   *
   * @return the arguments
   */
  public abstract @NotNull String @NotNull [] argStrings();

  public final @NotNull String originalString() {
    return original;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(original);
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "original='" + original + '\'' +
        '}';
  }

}
