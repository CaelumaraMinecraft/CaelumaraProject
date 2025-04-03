package net.aurika.auspice.constants.ecomony.balance;

import net.aurika.auspice.constants.ecomony.currency.NumberCurrency;
import net.aurika.common.data.DataStringRepresentation;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public interface Balance<T> extends Comparable<Number>, DataStringRepresentation {

  double get();

  double set(@NotNull Number value);

  @NotNull NumberCurrency<T> getEconomy();

  default void transfer(@NotNull Number amount, @NotNull Balance to) {
    Validate.Arg.notNull(amount, "amount");
    Validate.Arg.notNull(to, "to");
    this.subtract(amount);
    to.add(amount);
  }

  default boolean has(@NotNull Number amount) {
    Validate.Arg.notNull(amount, "amount");
    return this.get() >= amount.doubleValue();
  }

  default boolean isEmpty() {
    return !this.has(1);
  }

  default double add(@NotNull Number amount) {
    Validate.Arg.notNull(amount, "amount");
    return this.set(this.get() + amount.doubleValue());
  }

  default double subtract(@NotNull Number amount) {
    Validate.Arg.notNull(amount, "amount");
    return this.set(this.get() - amount.doubleValue());
  }

  default int compareTo(@NotNull Number other) {
    Validate.Arg.notNull(other, "other");
    return Double.compare(this.get(), other.doubleValue());
  }

  @NotNull
  default String asDataString() {
    return String.valueOf(this.get());
  }

}
