package net.aurika.util.reflection;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.Objects;

public final class AnnotationReflect {

  /**
   * 用以解决 Java 中被 {@link java.lang.annotation.Inherited} 注解的注解不能从接口继承的问题.
   *
   * @param clazz          寻找注解的类
   * @param annotationType 注解类型
   * @param <A>            注解类型
   * @return 找到的注解
   * @throws IllegalArgumentException 当注解继承层级中发现了多个冲突的注解时
   */
  public static <A extends Annotation> @Nullable A findAnnotationHierarchy(@NotNull Class<?> clazz, @NotNull Class<A> annotationType) {
    Validate.Arg.notNull(clazz, "clazz");
    Validate.Arg.notNull(annotationType, "annotationType");
    if (clazz == Object.class) {  // the Object class has no annotations
      return null;
    }
    if (clazz.isAnnotationPresent(annotationType)) {
      return clazz.getAnnotation(annotationType);
    }
    Class<?>[] interfaces = clazz.getInterfaces();
    int inheritsCount = interfaces.length + 1;  // super class and interfaces
    // noinspection unchecked
    A[] superLvlAnnotations = (A[]) Array.newInstance(annotationType, inheritsCount);
    // 获取临近父级的注解
    // includes inheritable annotation inherited from super class
    superLvlAnnotations[0] = findAnnotationHierarchy(clazz.getSuperclass(), annotationType);
    for (int i = 1; i < inheritsCount; i++) {
      Class<?> intf = interfaces[i - 1];
      Objects.requireNonNull(intf, "intf");
      superLvlAnnotations[i] = findAnnotationHierarchy(intf, annotationType);
    }
    // 检查注解冲突
    int c = -1;
    for (int i = 0; i < inheritsCount; i++) {
      @Nullable A foundAnn = superLvlAnnotations[i];
      if (foundAnn != null) {
        // 若之前已经找到同类注解且内容不同
        if (c != -1 && !superLvlAnnotations[c].equals(foundAnn)) {
          throw new IllegalArgumentException(
              "Found more than one @" + annotationType.getName() + " annotation and they are not equal to other (found "
                  + superLvlAnnotations[c] + " on " + (c == 0 ? clazz.getSuperclass() : interfaces[c - 1])
                  + " and " + foundAnn + " on " + interfaces[i - 1] + ")");
        } else {
          c = i;
        }
      }
    }
    if (c != -1) {
      return superLvlAnnotations[c];
    } else {
      return null;
    }
  }

}
