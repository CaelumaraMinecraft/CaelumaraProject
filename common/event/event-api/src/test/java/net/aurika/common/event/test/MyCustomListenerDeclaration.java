package net.aurika.common.event.test;

import net.aurika.common.event.EventListener;

public class MyCustomListenerDeclaration {

  @EventListener(id = "net.aurika:test")
  public void onMyCustomEvent(MyListenableEvent event) {
    System.out.println(event);
    System.out.println("MyCustomListenerDeclaration onMyCustomEvent");
  }

}
