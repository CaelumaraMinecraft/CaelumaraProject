package net.aurika.auspice.text.compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class TextCompilerException extends RuntimeException {
    private final String target;
    private final String problem;
    private final int index;

    public TextCompilerException(String target, String problem, int index, String message) {
        super(message);
        this.target = target;
        this.problem = problem;
        this.index = index;
    }

    public String getTarget() {
        return this.target;
    }

    public int getIndex() {
        return this.index;
    }

    public String getProblem() {
        return this.problem;
    }

    /**
     * 返回一个全部为空格的字符串.
     * @param length 字符串的长度
     * @return 生成的字符串
     */
    protected static String spaces(int length) {
        char[] chars = new char[length];
        Arrays.fill(chars, ' ');
        return new String(chars);
    }

    protected static Collection<Integer> pointerToName(int pointer, String str) {
        if (str == null) {
            return new ArrayList<>();
        } else {
            ArrayList<Integer> var2 = new ArrayList<>(str.length());

            for (int var3 = 1; var3 < str.length(); ++var3) {
                var2.add(pointer + var3);
            }

            return var2;
        }
    }
}
