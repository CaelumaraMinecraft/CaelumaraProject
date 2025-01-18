package top.auspice.constants.ecomony.balance;

import com.google.common.util.concurrent.AtomicDouble;
import org.jetbrains.annotations.NotNull;
import top.auspice.constants.ecomony.currency.NumberCurrency;

import java.util.Objects;

public final class AbstractBalance<T> implements Balance<T> {
    @NotNull
    private NumberCurrency<T> economy;
    @NotNull
    private AtomicDouble value;

    public AbstractBalance(@NotNull NumberCurrency<T> economy, @NotNull AtomicDouble value) {
        Objects.requireNonNull(economy, "economy");
        Objects.requireNonNull(value, "value");
        this.economy = economy;
        this.value = value;
    }

    public double get() {
        return this.value.get();
    }

    public double set(@NotNull Number value) {
        Objects.requireNonNull(value, "value");
        this.value.set(value.doubleValue());
        return this.value.get();
    }

    @NotNull
    public NumberCurrency<T> getEconomy() {
        return this.economy;
    }
}
