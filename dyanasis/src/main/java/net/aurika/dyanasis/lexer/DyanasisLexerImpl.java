package net.aurika.dyanasis.lexer;

import net.aurika.dyanasis.NamingContract;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntPredicate;

public abstract class DyanasisLexerImpl implements DyanasisLexer {
    protected final @Nullable DyanasisLexer parent;
    protected final @NotNull DyanasisLexerSettings settings;
    protected final @NotNull String original;

    public DyanasisLexerImpl(@Nullable DyanasisLexer parent, @NotNull DyanasisLexerSettings settings, @NotNull String original) {
        Validate.Arg.notNull(settings, "settings");
        Validate.Arg.notNull(original, "original");
        this.parent = parent;
        this.settings = settings;
        this.original = original;
    }

    /**
     * Forward/backward finds a {@linkplain String} that satisfies the {@code str}.
     *
     * @param start   the start index
     * @param reverse is reversed finding
     * @param str     the string to find
     * @return the index of the found string, returns {@code -1} when it cannot found
     * @throws IndexOutOfBoundsException when {@code start} is less than {@code 0} or greater than length of {@link #original}.
     */
    protected int findString(int start, boolean reverse, @NotNull String str) {
        Validate.Arg.notNull(str, "string");
        checkValidIndex(start);
        if (str.isEmpty()) {
            throw new IllegalArgumentException("Can not find an empty str");
        } else {
            if (str.length() == 1) {
                return findChar(str.charAt(0), start, reverse);
            } else {
                if (reverse) {
                    return original.indexOf(str, start);
                } else {
                    return original.lastIndexOf(str, start);
                }
            }
        }
    }

    /**
     * @see #findChar(IntPredicate, boolean, int)
     */
    protected int findChar(char c, int start, boolean reverse) {
        return reverse ? original.lastIndexOf(c, start) : original.indexOf(c, start);
    }

    /**
     * @see #findChar(IntPredicate, boolean, int, int)
     */
    protected int findChar(@NotNull IntPredicate condition, boolean reverse, int start) {
        return findChar(condition, reverse, start, original.length());
    }

    /**
     * Forward/backward finds a {@code char} that satisfies the {@code condition}.
     *
     * @param condition the condition to test a {@code char}
     * @param reverse   is reversed finding
     * @param start     the start index
     * @param distance  the distance
     * @return the index of the found char. {@code -1} when not found
     * @throws IndexOutOfBoundsException when {@code start} is less than {@code 0} or greater than length of {@link #original}.
     */
    protected int findChar(@NotNull IntPredicate condition, boolean reverse, int start, int distance) {
        Validate.Arg.notNull(condition, "condition");
        if (!isValidIndex(start)) {  // out of bound
            throw new IndexOutOfBoundsException("start " + start + " is less than 0 or more than original sting length " + original.length());
        } else {
            int length = original.length();
            for (int i = start; distance > 0 && i >= 0 && i < length; i = reverse ? i - 1 : i + 1, distance--) {
                if (condition.test(original.charAt(i))) {
                    return i;
                }
            }
            return -1;
        }
    }

    protected boolean startWith(@NotNull String prefix, int start) {
        checkValidIndex(start);
        return original.startsWith(prefix, start);
    }

    /**
     * Gets the end index of an invoking after near the index {@code start}.
     * <blockquote><pre>
     * raw ident: "``"
     * string  |string.``starts-with i + 1 P``()
     * index   |       ^--------------------->
     * </pre></blockquote>
     *
     * @param start the start index
     * @return {@code -1} when is not starts with an invoking, or found invoking end index
     */
    protected int findInvoke(int start) {
        if (startWith(settings.idents.rawInvokeLeft, start)) {  // when use raw invoking
            int i = findString(start + settings.idents.rawInvokeLeft.length(), false, settings.idents.rawInvokeRight);
            return i == -1 ? -1 : i + settings.idents.rawInvokeRight.length();  // -1 => unclosed raw invoking name
        } else {
            if (NamingContract.isValidNormalInvokableChar(original.charAt(start), true)) {  // check the indexed char is a valid invoking head char
                int end = findChar((c) -> !NamingContract.isValidNormalInvokableChar((char) c, false), false, start + 1);
                if (end == -1) {
                    return end - 1;
                }
            }
            return -1;
        }
    }

    /**
     * @param index the index to check
     * @throws IndexOutOfBoundsException when the index is uot of bound
     */
    protected void checkValidIndex(int index) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException("Index " + index + " is less than 0 or more than original sting length " + original.length());
        }
    }

    protected boolean isValidIndex(int index) {
        return index >= 0 && index < original.length();
    }

    @Override
    public abstract @NotNull Expression lex();

    @Override
    public final @Nullable DyanasisLexer parent() {
        return parent;
    }

    @Override
    public final @NotNull DyanasisLexerSettings settings() {
        return settings;
    }

    @Override
    public final @NotNull String originalString() {
        return original;
    }
}
