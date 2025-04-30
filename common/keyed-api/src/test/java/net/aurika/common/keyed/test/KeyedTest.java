package net.aurika.common.keyed.test;

import net.aurika.common.keyed.annotation.KeyedBy;
import net.aurika.common.keyed.annotation.KeyedReflection;
import net.aurika.util.reflection.AnnotationReflect;
import org.jetbrains.annotations.NotNull;

public final class KeyedTest {

  public static void main(String[] args) {
    try {
      Object keyed = new SubKeyed("AD");
      Object key = KeyedReflection.getKey(keyed);
      System.out.println("Key is: " + key);

      System.out.println("KeyedBy ann: " + AnnotationReflect.findAnnotationHierarchy(keyed.getClass(),KeyedBy.class));
    } catch (Exception e) {
      e.printStackTrace(System.err);
    }
  }

}

@KeyedBy(value = "ident")
interface AbstractKeyed {

  @NotNull String ident();

}

class SubKeyed extends SuperKeyed implements AbstractKeyed {

  public SubKeyed(String key) {
    super(key);
  }

  @Override
  public @NotNull String ident() {
    return key();
  }

}

@KeyedBy(value = "key")
class SuperKeyed {

  private final String key;

  public SuperKeyed(String key) { this.key = key; }

  public String key() {
    return this.key;
  }

}
