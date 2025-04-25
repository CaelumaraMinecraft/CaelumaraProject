package net.aurika.common.util.reflect.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static net.aurika.common.util.reflect.ClassGenerator.generateSubClass;

public class Test {

  public static void main(String[] args) {
    // 示例使用
    try {
      Class<?> generatedClass = generateSubClass("test.A$Ab", Parent.class, MyInterface.class, Huh.class);
      System.out.println("Generated class: " + generatedClass + ", is abstract? " + Modifier.isAbstract(
          generatedClass.getModifiers()));
      System.out.println();
      System.out.println("Constructors:");
      for (Constructor<?> constructor : generatedClass.getConstructors()) {
        System.out.println(constructor);
      }
      System.out.println();
      System.out.println("Methods:");
      for (Method method : generatedClass.getMethods()) {
        System.out.println(method);
      }
    } catch (Throwable e) {
      e.printStackTrace(System.err);
    }
  }

}
