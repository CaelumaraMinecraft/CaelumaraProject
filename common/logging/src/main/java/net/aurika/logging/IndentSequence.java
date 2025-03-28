package net.aurika.logging;

import org.jetbrains.annotations.NotNull;

public interface IndentSequence {

  static @NotNull IndentSequence indentSequence(@NotNull Indent @NotNull [] indents) {
    return new IndentSequenceImpl(indents);
  }

  @NotNull Indent @NotNull [] getIndents();

}

class IndentSequenceImpl implements IndentSequence {

  private final @NotNull Indent @NotNull [] indents;

  IndentSequenceImpl(@NotNull Indent @NotNull [] indents) {
    this.indents = indents;
  }

  @Override
  public @NotNull Indent @NotNull [] getIndents() {
    return indents;
  }

}
