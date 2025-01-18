package top.auspice.config.placeholder;

import kotlin.collections.ArraysKt;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import top.auspice.utils.string.Strings;

/**
 * 分析占位符包围内的字符串 ("%%"内)
 */
public final class PlaceholderParser {
    private final int start;
    private final int end;
    private final char[] chars;
    private int index;

    private String parsed_Indenfier;
    private PlaceholderType type;

    public PlaceholderParser(String string) {
        this(0, string.length(), string.toCharArray());
    }

    public PlaceholderParser(int start, int end, char[] chars) {
        this.start = start;
        this.end = end;
        this.chars = chars;
        this.index = start;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }

    public char[] getChars() {
        return this.chars;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private char getPointerChar() {
        return this.chars[this.index];
    }

    @SuppressWarnings("UnusedReturnValue")
    private int increaseIndex() {
        return this.index++;
    }

    @NotNull
    public String subString(int offset, int toffset) {
        if (toffset < offset) {
            throw new IllegalArgumentException("End is less than than start: " + offset + " > " + toffset);
        } else {
            return new String(ArraysKt.sliceArray(this.chars, RangesKt.until(offset, toffset)));
        }
    }

    public int indexOf(char ch, int startIndex) {

        for (int var3 = this.chars.length; startIndex < var3; ++startIndex) {
            if (ch == this.chars[startIndex]) {
                return startIndex;
            }
        }

        return -1;
    }

    public void skipWhitespace() {
        PlaceholderParser lexer = this;

        while (Strings.isWhitespace(lexer.getPointerChar())) {
            lexer.increaseIndex();
            if (lexer.index >= lexer.chars.length) {
                break;
            }
        }

    }

    public void skipIllogicalCharacter() {
        PlaceholderParser lexer = this;
        while ()
    }

    private void process_type() {

    }




    public enum PlaceholderType {
        AUSPICE_PLACEHOLDER,
        EXTERNAL_PLACEHOLDER,
        LOCAL_PLACEHOLDER,
        MACRO_PLACEHOLDER,
        CONFIG_PATH_PLACEHOLDER
    }
    public enum ActionType {

        GET_ATTR("."),
        SAFE_GET_ATTR("?."),
        INVOKE(". ()"),
        SAFE_INVOKE("?. ()"),
        INVOKE_MACRO("@ ()?");

        public final String value;

        ActionType(String value) {
            this.value = value;
        }

    }


}
