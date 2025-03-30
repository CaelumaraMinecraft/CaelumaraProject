package net.aurika.kingdoms.auspice.costs.statistics;

public class CurrencyFlowRecord<T> {

  public T target;                  //such as a kingdom, or a player
  public long circulateTime;
  private final CurrencyEntryList<T> entries;

  public CurrencyFlowRecord(CurrencyEntryList<T> entries, T target) {
    this.entries = entries;
    this.circulateTime = System.currentTimeMillis();
    this.target = target;
  }

}
