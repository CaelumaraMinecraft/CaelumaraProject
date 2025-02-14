package top.mckingdom.auspice.costs;

import org.jetbrains.annotations.NotNull;
import top.mckingdom.auspice.costs.statistics.CurrencyEntry;

public interface Currency<T, C> {

//    private final String key;
//    private final Class<T> targetClass;                                //用于验证此货币是否可以用于目标对象的
//
//    public Currency(@NotNull String key, @NotNull Class<T> targetClass) {
//        Objects.requireNonNull(key, "key can not be null");
//        Objects.requireNonNull(targetClass, "targetClass can not be null");
//        this.key = key;
//        this.targetClass = targetClass;
//        CurrencyRegistry.register(this);
//    }
//
//    public @NotNull String getKey() {
//        return this.key;
//    }
//
//    public Class<T> getTargetClass() {
//        return targetClass;
//    }

    @NotNull String getKey();

    @NotNull Class<T> getTargetClass();

    /**
     * 如果形参amountString不符合规范
     * 则返回null
     */
    CurrencyEntry<T, C> getAmount(String amountString);
    CurrencyEntry<T, C> getAmount(Object amount);


    boolean canExpend(T target, C amount);
    void forceExpend(T target, C amount);

    boolean canRefund(T target, C amount);
    void forceRefund(T target, C amount);

}



