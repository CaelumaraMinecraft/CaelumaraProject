package net.aurika.dyanasis.api.compiler.expression;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class FnArgs implements Iterable<Expression> {

  protected final @NotNull Expression @NotNull [] args;

  public FnArgs(@NotNull Expression @NotNull [] args) {
    Validate.Arg.nonNullArray(args, "args");
    this.args = args;
  }

  /**
   * Gets the clone of the args array.
   *
   * @return the args copy
   */
  public @NotNull Expression @NotNull [] args() {
    return args.clone();
  }

  @Override
  public @NotNull Iterator<Expression> iterator() {
    return new Itr();
  }

  protected class Itr implements Iterator<Expression> {

    private int index = 0;

    @Override
    public boolean hasNext() {
      return index < args.length;
    }

    @Override
    public Expression next() {
      return args[index++];
    }

  }

}
