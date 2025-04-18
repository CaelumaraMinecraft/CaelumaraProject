package net.aurika.common.key.test;

import net.aurika.common.ident.Ident;
import net.aurika.common.ident.Identified;
import net.aurika.common.ident.registry.AbstractIdentifiedRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class KeyTest {

  public static void main(String[] args) {

    Ident testIdent01 = Ident.ident("net.aurika.common.key.test:ss_dsd/sd");
    Ident testIdent02 = Ident.ident("net.aurika.common.key.test:success/sss");

    A.INSTANCE.register(new B(testIdent01));
    A.INSTANCE.register(new B(testIdent02));

    System.out.println(Arrays.toString(A.INSTANCE.getClass().getTypeParameters()));

    System.out.println(AbstractIdentifiedRegistry.class.toGenericString());
  }

  static class A extends AbstractIdentifiedRegistry<B> {

    public static final A INSTANCE = new A();

    private A() { super(); }

  }

  static class B implements Identified {

    private final Ident id;

    B(Ident id) { this.id = id; }

    @Override
    public @NotNull Ident ident() {
      return id;
    }

  }

}
