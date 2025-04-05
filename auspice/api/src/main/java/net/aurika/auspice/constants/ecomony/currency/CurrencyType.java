package net.aurika.auspice.constants.ecomony.currency;

import net.aurika.auspice.constants.ecomony.currency.bill.CurrencyEntry;
import net.aurika.common.key.Key;
import org.jetbrains.annotations.NotNull;

public interface CurrencyType<T, C> extends Keyed {

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

  /**
   * Get the currency type key.
   */
  @Override
  @NotNull Key key();

  /**
   * Return weather can apply the currency to the target
   *
   * @param target The target
   */
  boolean canApply(@NotNull Object target);

  /**
   * 如果形参 amountString 不符合规范
   * 则返回 {@code null}
   */
  CurrencyEntry<T, C> getAmount(String amountString);

  CurrencyEntry<T, C> getAmount(Object amount);

  boolean canExpend(T target, C amount);

  void forceExpend(T target, C amount);

  boolean canRefund(T target, C amount);

  void forceRefund(T target, C amount);

}



