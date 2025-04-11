package net.aurika.common.key.test;

import net.aurika.common.key.Key;
import net.aurika.common.key.Keyed;
import net.aurika.common.key.registry.AbstractKeyedRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class KeyTest {

  public static void main(String[] args) {

    Key testKey01 = Key.key("net.aurika.common.key.test:ss_dsd/sd");
    Key testKey02 = Key.key("net.aurika.common.key.test:success/sss");

    A.INSTANCE.register(new B(testKey01));
    A.INSTANCE.register(new B(testKey02));

    System.out.println(Arrays.toString(A.INSTANCE.getClass().getTypeParameters()));

    System.out.println(AbstractKeyedRegistry.class.toGenericString());

  }

  static class A extends AbstractKeyedRegistry<B> {

    public static final A INSTANCE = new A();

    private A() { super(); }

  }

  static class B implements Keyed {

    private final Key id;

    B(Key id) { this.id = id; }

    @Override
    public @NotNull Key key() {
      return id;
    }

  }

}
