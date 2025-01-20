package top.mckingdom.auspice.costs.statistics;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class CurrencyEntryList<T> extends LinkedList<CurrencyEntry<T, ?>> {

    private TradeType circulateType;

    public CurrencyEntryList(TradeType circulateType) {
        this.circulateType = circulateType;
    }

    public boolean canCirculate(T target) {
        boolean out = true;
        for (CurrencyEntry<T, ?> ce : this) {
            if (!ce.canCirculate(target, this.circulateType)) {
                out = false;
            }
        }
        return out;
    }

    /**
     * 可以通过返回值是否为null判断是否流通成功
     * 如果返回非null则代表能够流通并流通过了
     * 如果返回null则代表无法流通
     * @return If null, that means it can't circulate.
     */
    public @Nullable CurrencyFlowRecord<T> circulate(T target) {
        if (this.canCirculate(target)) {
            return this.forceCirculate(target);
        } else {
            return null;
        }
    }


    public @NotNull CurrencyFlowRecord<T> forceCirculate(T target) {
        this.forEach((e) -> {
            e.forceCirculate(target, this.circulateType);
        });
        return new CurrencyFlowRecord<>(this, target);
    }

    public TradeType getCirculateType() {
        return circulateType;
    }

    public void setCirculateType(TradeType circulateType) {
        this.circulateType = circulateType;
    }

    @Override
    public String toString() {
        return "CurrencyEntryList: {entries: " + super.toString() + "}";
    }

    public enum TradeType {
        EXPEND,  //expenditure  支出
        INCOME,  //收入
        ;
    }

}
