package net.aurika.common.util.reflect;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

public class ClassGenerator {

  /**
   * 生成一个继承自一个父类和实现多个接口的类. 当有抽象方法时, 这个生成的类为 abstract, 否则不是.
   *
   * @param superClass 父类
   * @param interfaces 多个接口
   * @return 生成的类
   */
  public static @NotNull Class<?> generateSubClass(@NotNull String className, @NotNull Class<?> superClass, @NotNull Class<?> @NotNull ... interfaces) {
    // 1. 参数校验
    if (superClass.isInterface()) {
      throw new IllegalArgumentException("Parent must be a class, not an interface.");
    }
    for (Class<?> intf : interfaces) {
      if (!intf.isInterface()) {
        throw new IllegalArgumentException("Super '" + intf + "' is not an interface: " + intf.getName());
      }
    }

    // 2. 收集所有接口及其父接口
    Set<Class<?>> allInterfaces = new HashSet<>();
    for (Class<?> intf : interfaces) {
      collectAllInterfaces(intf, allInterfaces);
    }

    // 3. 检查是否有抽象方法
    boolean hasAbstractMethod = false;
    for (Method method : superClass.getMethods()) {
      if (Modifier.isAbstract(method.getModifiers())) {
        hasAbstractMethod = true;
        break;
      }
    }
    for (Class<?> intf : allInterfaces) {
      for (Method method : intf.getMethods()) {
        if (Modifier.isAbstract(method.getModifiers())) {
          hasAbstractMethod = true;
          break;
        }
      }
    }

    try (
        // 4. 动态生成类
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
            .subclass(superClass)
            .implement(interfaces)
            .name(className)
            .modifiers(hasAbstractMethod ? Modifier.ABSTRACT | Modifier.PUBLIC : Modifier.PUBLIC)
            .make();
        ) {

      // 5. 加载生成的类
      return dynamicType.load(superClass.getClassLoader()).getLoaded();
    } catch (Exception e) {
      throw new RuntimeException("Failed to generate class", e);
    }
  }

  /**
   * 递归收集接口及其父接口
   */
  @Contract(mutates = "param2")
  private static void collectAllInterfaces(@NotNull Class<?> intf, @NotNull Set<Class<?>> interfacesSet) {
    if (interfacesSet.add(intf)) {
      for (Class<?> parent : intf.getInterfaces()) {
        collectAllInterfaces(parent, interfacesSet);
      }
    }
  }

  /**
   * 匹配所有由指定接口或其父接口声明的方法
   */
  private static ElementMatcher.Junction<MethodDescription> isDeclaredByAny(Set<Class<?>> interfaces) {
    ElementMatcher.Junction<MethodDescription> matcher = ElementMatchers.none();
    for (Class<?> intf : interfaces) {
      matcher = matcher.or(ElementMatchers.isDeclaredBy(intf));
    }
    return matcher;
  }

}






