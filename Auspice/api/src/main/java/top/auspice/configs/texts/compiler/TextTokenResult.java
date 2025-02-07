package top.auspice.configs.texts.compiler;

import top.auspice.configs.texts.compiler.pieces.TextPiece;

public final class TextTokenResult {

    public final int index;
    public final TextPiece piece;

    public TextTokenResult(int index, TextPiece piece) {
        this.index = index;
        this.piece = piece;
    }

}