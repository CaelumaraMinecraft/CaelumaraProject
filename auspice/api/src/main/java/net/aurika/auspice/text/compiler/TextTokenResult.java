package net.aurika.auspice.text.compiler;

import net.aurika.auspice.text.compiler.pieces.TextPiece;

public final class TextTokenResult {

  public final int index;
  public final TextPiece piece;

  public TextTokenResult(int index, TextPiece piece) {
    this.index = index;
    this.piece = piece;
  }

}