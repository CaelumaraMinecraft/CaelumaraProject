package net.aurika.text.compiler;

import net.aurika.text.compiler.pieces.TextPiece;

public final class TextTokenResult {

    public final int index;
    public final TextPiece piece;

    public TextTokenResult(int index, TextPiece piece) {
        this.index = index;
        this.piece = piece;
    }

}