package top.auspice.constants.ecomony.balance;

import org.jetbrains.annotations.NotNull;
import top.auspice.constants.ecomony.currency.NumberCurrency;
import net.aurika.ecliptor.object.DataStringRepresentation;

import java.util.Objects;

public interface Balance<T> extends Comparable<Number>, DataStringRepresentation {
    double get();

    double set(@NotNull Number var1);

    @NotNull
    NumberCurrency<T> getEconomy();

    default void transfer(@NotNull Number amount, @NotNull Balance to) {
        Objects.requireNonNull(amount, "amount");
        Objects.requireNonNull(to, "to");
        this.subtract(amount);
        to.add(amount);
    }

    default boolean has(@NotNull Number amount) {
        Objects.requireNonNull(amount, "amount");
        return this.get() >= amount.doubleValue();
    }

    default boolean isEmpty() {
        return !this.has(1);
    }

    default double add(@NotNull Number amount) {
        Objects.requireNonNull(amount, "amount");
        return this.set(this.get() + amount.doubleValue());
    }

    default double subtract(@NotNull Number amount) {
        Objects.requireNonNull(amount, "amount");
        return this.set(this.get() - amount.doubleValue());
    }

    default int compareTo(@NotNull Number other) {
        Objects.requireNonNull(other, "other");
        return Double.compare(this.get(), other.doubleValue());
    }

    @NotNull
    default String asDataString() {
        return String.valueOf(this.get());
    }
}
