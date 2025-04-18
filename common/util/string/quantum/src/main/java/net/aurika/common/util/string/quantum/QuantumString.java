package net.aurika.common.util.string.quantum;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.stream.IntStream;

public class QuantumString implements CharSequence, Cloneable {

  private final @Nullable String original;
  private final @NotNull String quantumValue;

  @Contract("_ -> new")
  public static @NotNull QuantumString of(String original) {
    return new QuantumString(original, true);
  }

  @Contract(" -> new")
  public static @NotNull QuantumString empty() {
    return new QuantumString("", false);
  }

  /**
   * @param original the string to wrap.
   * @param quantum  if this wrapper should be case-insensitive, otherwise case-sensitive.
   */
  protected QuantumString(@NotNull String original, boolean quantum) {
    Validate.Arg.notNull(original, "original", "Quantum original string cannot be null");
    this.original = quantum ? original : null;

    // The local doesn't matter as long as it's consistent.
    this.quantumValue = quantum ? original.toLowerCase(Locale.ENGLISH) : original;
  }

  public boolean isQuantum() {
    return original != null;
  }

  @Override
  public int hashCode() {
    return quantumValue.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return this == obj ||
        (obj instanceof QuantumString && this.quantumValue.equals(((QuantumString) obj).quantumValue));
  }

  @Override
  public @NotNull String toString() {
    return "QuantumString:[quantum= " + isQuantum() + ", original=" + original + ", quantumValue=" + quantumValue + ']';
  }

  @Override
  public int length() {
    return quantumValue.length();
  }

  @SuppressWarnings("Since15")
  public boolean isEmpty() {
    return quantumValue.isEmpty();
  }

  @Override
  public char charAt(int index) {
    return getQuantum().charAt(index);
  }

  @NotNull
  @Override
  public CharSequence subSequence(int start, int end) {
    return getQuantum().subSequence(start, end);
  }

  @NotNull
  @Override
  public IntStream chars() {
    return getQuantum().chars();
  }

  @NotNull
  @Override
  public IntStream codePoints() {
    return getQuantum().codePoints();
  }

  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }

  @Nullable
  public String getOriginal() {
    return original;
  }

  @NotNull
  public String getQuantumValue() {
    return quantumValue;
  }

  @NotNull
  public String getQuantum() {
    return isQuantum() ? original : quantumValue;
  }

}
