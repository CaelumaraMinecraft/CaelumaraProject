package net.aurika.common.util.reflect.test;

import net.aurika.util.reflection.AnnotationReflect;

import java.lang.annotation.*;

public class AnnotationRefTest {

  public static void main(String[] args) {
    try {
      TestAnn testAnn = AnnotationReflect.findAnnotationHierarchy(Sub.class, TestAnn.class);
      System.out.println(testAnn);
    } catch (Exception e) {
      e.printStackTrace(System.err);
    }
  }

}

@TestAnn("a")
interface Abs1 { }

@TestAnn("b")
interface Abs2 { }

//@TestAnn("c")
class Super implements Abs1 { }

//@TestAnn("asd")
class Sub extends Super implements Abs2 { }

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface TestAnn {

  String value();

}
