package top.mckingdom.auspice.costs.statistics;

import top.mckingdom.auspice.costs.Currency;

/**
 * Such as: kingdom resource points 1232
 *
 * @param <T>
 * @param <C>
 */
public class CurrencyEntry<T, C> {
  public final Currency<T, C> currency;
  public C amount;

  public CurrencyEntry(Currency<T, C> currency, C amount) {
    this.currency = currency;
    this.amount = amount;
  }

  public boolean canCirculate(T target, CurrencyEntryList.TradeType circulateType) {
    switch (circulateType) {
      case EXPEND -> {
        return this.currency.canExpend(target, amount);
      }
      case INCOME -> {
        return this.currency.canRefund(target, amount);
      }
    }
    throw new NullPointerException("The circulateType of currency flow can not be null");
  }

  public void forceCirculate(T target, CurrencyEntryList.TradeType circulateType) {
    switch (circulateType) {
      case EXPEND -> this.currency.forceExpend(target, amount);
      case INCOME -> this.currency.forceRefund(target, amount);
    }
  }

}
