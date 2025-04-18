package net.aurika.common.util.string;

import net.aurika.common.validate.Validate;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.stream.IntStream;

public class QuantumString implements CharSequence, Cloneable {

  private final @Nullable String original;
  private final @NonNull String quantumValue;

  public static QuantumString of(String original) {
    return new QuantumString(original, true);
  }

  public QuantumString(@NonNull String original, boolean quantum) {
    Validate.Arg.notNull(original, "original", "Quantum original string cannot be null");
    this.original = quantum ? original : null;
    this.quantumValue = quantum ? original.toLowerCase(Locale.ENGLISH) : original;
  }

  public static QuantumString empty() {
    return new QuantumString("", false);
  }

  public boolean isQuantum() {
    return original != null;
  }

  public int hashCode() {
    return quantumValue.hashCode();
  }

  public boolean equals(Object obj) {
    return this == obj || obj instanceof QuantumString && quantumValue.equals(((QuantumString) obj).quantumValue);
  }

  public @NotNull String toString() {
    return "QuantumString:[quantum= " + isQuantum() + ", original=" + original + ", quantumValue=" + quantumValue + ']';
  }

  public int length() {
    return quantumValue.length();
  }

  public boolean isEmpty() {
    return quantumValue.isEmpty();
  }

  public char charAt(int index) {
    return getQuantum().charAt(index);
  }

  @NotNull
  public CharSequence subSequence(int start, int end) {
    return getQuantum().subSequence(start, end);
  }

  @NotNull
  public IntStream chars() {
    return getQuantum().chars();
  }

  @NotNull
  public IntStream codePoints() {
    return getQuantum().codePoints();
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
    return original;
  }

  @NonNull
  public String getQuantumValue() {
    return quantumValue;
  }

  @NonNull
  public String getQuantum() {
    return isQuantum() ? original : quantumValue;
  }

}

