package top.auspice.utils.string;

import org.jetbrains.annotations.NotNull;
import top.auspice.utils.Checker;

import java.util.Arrays;
import java.util.StringJoiner;

public final class CommaDataSplitStrategy {
    private final String data;
    private final int len;
    private int index;
    private final int expectedComponentCount;
    private final String[] components;

    public CommaDataSplitStrategy(@NotNull String data, int expectedComponentCount) {
        Checker.Argument.checkNotEmptyString(data, "data", "Data cannot be null or empty");
        if (expectedComponentCount < 1)
            throw new IllegalArgumentException("expectedComponentCount must be greater than zero: " + expectedComponentCount);

        this.expectedComponentCount = expectedComponentCount;
        this.components = new String[expectedComponentCount];
        this.data = data;
        this.len = data.length();
        this.parseAllComponents();
    }

    public void missing() {
        throw new IllegalStateException("Components missing. Expected " + expectedComponentCount + ": " + data);
    }

    private void parseAllComponents() {
        int i = 0;
        int start = 0;

        for (int j = 0; j < expectedComponentCount; j++) {
            String component = null;
            if (i >= len) missing();

            while (i < len) {
                if (data.charAt(i) == ',') {
                    component = data.substring(start, i);

                    // Skip the space after comma, and we know that our data is at least one character long.
                    start = i += 2;
                    break;
                }
                i++;
            }

            if (component == null) component = data.substring(start, len);
            if (component == null || component.isEmpty()) missing();
            this.components[j] = component;
        }

        if (i < len)
            throw new IllegalStateException("Only expected " + expectedComponentCount + " componenets but got more: " + data);
    }

    public String nextString() {
        int nextIndex = index++;
        if (nextIndex >= components.length) {
            throw new IllegalStateException("Only expected " + expectedComponentCount + " componenets but requested more: " + data);
        }
        return components[nextIndex];
    }

    private RuntimeException numberError(String str) {
        return new IllegalStateException("Expected an integer for " + index + " but got '" + str + "' in: " + data);
    }

    public int nextInt() {
        String str = nextString();
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException ex) {
            throw numberError(str);
        }
    }

    public double nextDouble() {
        String str = nextString();
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException ex) {
            throw numberError(str);
        }
    }

    public float nextFloat() {
        String str = nextString();
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException ex) {
            throw numberError(str);
        }
    }

    public static String toString(Object[] components) {
        StringJoiner joiner = new StringJoiner(", ");
        for (Object component : components) {
            String str = component.toString();
            if (str == null || str.isEmpty()) {
                throw new IllegalArgumentException("Data component is null or empty '" + str + "' in: " + Arrays.toString(components));
            }
            joiner.add(str);
        }
        return joiner.toString();
    }
}
