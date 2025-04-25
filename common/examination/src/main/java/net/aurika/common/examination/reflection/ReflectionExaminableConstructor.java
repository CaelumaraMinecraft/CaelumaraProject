package net.aurika.common.examination.reflection;

import net.aurika.common.validate.Validate;
import net.kyori.examination.Examinable;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public interface ReflectionExaminableConstructor<E extends Examinable> extends net.aurika.common.examination.ExaminableConstructor<E> {

  static <E extends Examinable> @NotNull ReflectionExaminableConstructor<E> create(
      @NotNull Class<E> examinableType,
      @NotNull Executable examinableConstructor
  ) {
    Validate.Arg.notNull(examinableConstructor, "examinableConstructor");
    if (examinableConstructor instanceof Method) {
      Method examinableConstructorMethod = (Method) examinableConstructor;
      if (!Modifier.isStatic(examinableConstructorMethod.getModifiers())) {
        throw new IllegalArgumentException("Require static method");
      }
    }
    ExaminableConstructor paramsAnn = examinableConstructor.getAnnotation(
        ExaminableConstructor.class);
    if (paramsAnn == null) {
      throw new IllegalArgumentException("Require annotation " + ExaminableConstructor.class);
    }
    return new AbstractReflectExaminableConstructor<>(examinableType, paramsAnn, examinableConstructor);
  }

}
