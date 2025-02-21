package net.aurika.auspice.text.compiler;

import net.aurika.auspice.text.compiler.pieces.TextPiece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ColorAccessor {
    private final TextPiece[] pieces;
    private final List<TextPiece> b = new ArrayList<>(3);
    private final List<TextPiece> c = new ArrayList<>(3);
    private int index;
    private final boolean e;
    private TextPiece pointerPiece;

    private ColorAccessor(TextPiece[] var1, int range, boolean var3) {
        this.pieces = var1;
        this.index = range;
        this.e = var3;
    }

    private boolean a() {
        if (--this.index < 0) {
            return true;
        } else {
            this.b.clear();
            this.c.clear();
            this.b.add(this.pointerPiece);
            return false;
        }
    }

    private boolean b() {
        this.b.clear();
        if (!this.c.isEmpty()) {
            this.b.addAll(this.c);
            this.c.clear();
        }

        this.b.add(this.pointerPiece);
        return --this.index <= 0;
    }

    public static List<TextPiece> of(TextPiece[] pieces, int range, boolean var2) {
        ColorAccessor accessor = new ColorAccessor(pieces, range, var2);
        int var10000 = accessor.e ? accessor.pieces.length - 1 : 0;

        while (true) {
            int var6 = var10000;
            if (accessor.e) {
                if (var6 < 0) {
                    break;
                }
            } else if (var6 >= accessor.pieces.length) {
                break;
            }

            accessor.pointerPiece = accessor.pieces[var6];
            if (accessor.pointerPiece instanceof TextPiece.Color) {
                if (accessor.pointerPiece instanceof TextPiece.HexTextColor) {
                    if (accessor.e) {
                        if (accessor.b()) {
                            break;
                        }
                    } else if (accessor.a()) {
                        break;
                    }
                } else if (accessor.pointerPiece instanceof TextPiece.SimpleFormat) {
                    if (((TextPiece.SimpleFormat) accessor.pointerPiece).getColor().isColor()) {
                        if (accessor.e) {
                            if (accessor.b()) {
                                break;
                            }
                        } else if (accessor.a()) {
                            break;
                        }
                    } else {
                        accessor.c.add(accessor.pointerPiece);
                    }
                }
            }

            var10000 = var6 + (accessor.e ? -1 : 1);
        }

        if (!accessor.e) {
            accessor.b.addAll(accessor.c);
        }

        if (accessor.e) {
            Collections.reverse(accessor.b);
        }

        return accessor.b;
    }
}

