package top.mckingdom.trade_point.constants.trade_point_attribute;

import org.kingdoms.constants.namespace.Namespace;
import top.mckingdom.trade_point.TradePointAddon;

public enum StandardTradePointAttribute implements TradePointAttribute {
  TELEPORT("TELEPORT"),


  ;

  private final Namespace namespace;

  StandardTradePointAttribute(String nsKey) {
    this.namespace = TradePointAddon.buildNS(nsKey);
  }

  @Override
  public Namespace getNamespace() {
    return this.namespace;
  }
}
