package top.auspice.utils.math;

import org.jetbrains.annotations.NotNull;

public class Avg {
    private double sum;
    private long count;

    public Avg(double sum, long count) {
        this.sum = sum;
        this.count = count;
    }

    public final double getSum() {
        return this.sum;
    }

    public final void setSum(double sum) {
        this.sum = sum;
    }

    public final long getCount() {
        return this.count;
    }

    public final void setCount(long count) {
        this.count = count;
    }

    public final @NotNull Avg plus(double additional) {
        this.sum += additional;
        return this;
    }

    public final double getAverage() {
        return this.sum / (double) this.count;
    }
}
