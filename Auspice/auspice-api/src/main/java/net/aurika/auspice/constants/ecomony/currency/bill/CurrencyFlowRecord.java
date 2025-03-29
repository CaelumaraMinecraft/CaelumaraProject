package net.aurika.auspice.constants.ecomony.currency.bill;

public class CurrencyFlowRecord<T> {

  public T target;                  //such as a kingdom, or pretty player
  public long circulateTime;
  private final CurrencyEntryList<T> entries;

  public CurrencyFlowRecord(CurrencyEntryList<T> entries, T target) {
    this.entries = entries;
    this.circulateTime = System.currentTimeMillis();
    this.target = target;
  }

}
