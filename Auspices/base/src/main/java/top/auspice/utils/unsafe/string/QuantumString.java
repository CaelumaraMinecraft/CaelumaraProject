package top.auspice.utils.unsafe.string;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.utils.Checker;

import java.util.Locale;
import java.util.stream.IntStream;

public class QuantumString implements CharSequence, Cloneable {
    private final @Nullable String original;
    private final @NonNull String quantumValue;

    public static QuantumString of(String original) {
        return new QuantumString(original, true);
    }

    public QuantumString(@NonNull String original, boolean quantum) {
        Checker.Argument.checkNotNull(original, "original", "Quantum original string cannot be null");
        this.original = quantum ? original : null;
        this.quantumValue = quantum ? original.toLowerCase(Locale.ENGLISH) : original;
    }

    public static QuantumString empty() {
        return new QuantumString("", false);
    }

    public boolean isQuantum() {
        return this.original != null;
    }

    public int hashCode() {
        return this.quantumValue.hashCode();
    }

    public boolean equals(Object obj) {
        return this == obj || obj instanceof QuantumString && this.quantumValue.equals(((QuantumString) obj).quantumValue);
    }

    @NotNull
    public String toString() {
        return "QuantumString:[quantum= " + this.isQuantum() + ", original=" + this.original + ", quantumValue=" + this.quantumValue + ']';
    }

    public int length() {
        return this.quantumValue.length();
    }

    public boolean isEmpty() {
        return this.quantumValue.isEmpty();
    }

    public char charAt(int index) {
        return this.getQuantum().charAt(index);
    }

    @NotNull
    public CharSequence subSequence(int start, int end) {
        return this.getQuantum().subSequence(start, end);
    }

    @NotNull
    public IntStream chars() {
        return this.getQuantum().chars();
    }

    @NotNull
    public IntStream codePoints() {
        return this.getQuantum().codePoints();
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException exc) {
            throw new AssertionError();
        }
    }

    @Nullable
    public String getOriginal() {
        return this.original;
    }

    @NonNull
    public String getQuantumValue() {
        return this.quantumValue;
    }

    @NonNull
    public String getQuantum() {
        return this.isQuantum() ? this.original : this.quantumValue;
    }
}

