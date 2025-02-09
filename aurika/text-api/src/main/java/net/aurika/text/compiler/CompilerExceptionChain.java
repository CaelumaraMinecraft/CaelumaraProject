package net.aurika.text.compiler;

import top.auspice.utils.string.Strings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CompilerExceptionChain {
    private final char[] chars;
    private final List<TextCompilerException> exceptions;

    public CompilerExceptionChain(char[] chars) {
        this(chars, new ArrayList<>());
    }

    public CompilerExceptionChain(char[] chars, List<TextCompilerException> exceptions) {
        this.chars = chars;
        this.exceptions = exceptions;
    }

    public List<TextCompilerException> getExceptions() {
        return this.exceptions;
    }

    public boolean hasErrors() {
        return !this.exceptions.isEmpty();
    }

    public String joinExceptions() {
        StringBuilder builder = new StringBuilder(this.exceptions.size() * 60);
        int count = this.exceptions.size();

        for (TextCompilerException exception : this.exceptions) {
            builder.append(exception.getMessage());
            --count;
            if (count > 0) {
                builder.append('\n');
            }
        }

        return builder.toString();
    }

    /**
     * 增加一条问题
     *
     * @param offset  问题在待完成字符串数组中的位置
     * @param problem 出现的问题
     * @param target  在哪出了问题
     */
    public void exception(int offset, String problem, String target) {
        String var4 = new String(this.chars);
        String message = problem + " at offset " + offset;
        int var6 = 0;
        List<String> raws = Strings.split(var4, '\n', true);

        int var7;
        for (var7 = 0; var7 < raws.size(); ++var7) {
            String var8 = raws.get(var7);
            if (var6 + var8.length() >= offset) {
                if (raws.size() == 1) {
                    message = message + " in message:\n";
                } else {
                    message = message + " in " + Strings.toOrdinalNumeral(var7 + 1) + " line of message:\n";
                }

                if (var7 != 0) {
                    message = message + "...\n";
                }

                message = message + '"' + var8 + '"';
                break;
            }

            var6 += var8.length() + 1;
        }

        int var14 = 0;
        Collection<Integer> var9 = TextCompilerException.pointerToName(offset - var6, target);
        var9.add(offset - var6);

        for (Integer integer : var9) {
            if (integer > var14) {
                var14 = integer;
            }
        }

        StringBuilder var13 = new StringBuilder(TextCompilerException.spaces(var14 + 2));
        var9.forEach((var1x) -> var13.setCharAt(var1x + 1, '^'));
        message = message + '\n' + var13;
        if (var7 + 1 != raws.size()) {
            message = message + "\n...";
        }

        this.exceptions.add(new TextCompilerException(target, problem, offset, message));
    }

    public void mergeExceptions(int i, CompilerExceptionChain other) {
        if (other.hasErrors()) {

            for (TextCompilerException exception : other.exceptions) {
                this.exception(i + exception.getIndex(), exception.getProblem(), exception.getTarget());
            }

        }
    }

}
