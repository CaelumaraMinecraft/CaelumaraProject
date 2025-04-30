package net.aurika.common.event.test;

import net.aurika.common.event.EventAPI;

import java.util.Arrays;

public class EventTest {

  public static void main(String[] args) {

    try {

      EventAPI.registerReflectListeners(new MyCustomListenerDeclaration(), false);

      MyListenableEvent.CONDUIT.transport(new MyListenableEventImpl());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    System.out.println(Arrays.toString(ReflectionTestSuper.class.getMethods()));

    try {
      System.out.println(ReflectionTestSuper.class.getMethod("publicMethod", Object.class).toGenericString());
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

}

class ReflectionTestSuper {

  public <T> void publicMethod(T t) { }

  protected void protectedMethod() { }

  void packageMethod() { }

}

class ReflectionTestSub extends ReflectionTestSuper {

  @Override
  protected void protectedMethod() {
    super.protectedMethod();
  }

}
