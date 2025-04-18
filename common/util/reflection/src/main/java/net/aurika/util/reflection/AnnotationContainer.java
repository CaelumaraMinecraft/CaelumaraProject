package net.aurika.util.reflection;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public interface AnnotationContainer {

  <T extends Annotation> @Nullable T getAnnotation(@NotNull java.lang.Class<T> var1);

  @NotNull Object getJavaObject();

  @NotNull java.lang.Class<?> getDeclaringClass();

  default @NotNull List<Annotation> findAll(@NotNull java.lang.Class<? extends Annotation>... annotations) {
    Validate.Arg.nonNullArray(annotations, "annotations");
    List<Annotation> founds = new ArrayList<>();
    int i = 0;

    for (int length = annotations.length; i < length; ++i) {
      java.lang.Class<? extends Annotation> annotation = annotations[i];
      Annotation found = this.getAnnotation(annotation);
      if (found != null) {
        founds.add(found);
      }
    }

    return founds;
  }

  default @Nullable Annotation any(@NotNull java.lang.Class<? extends Annotation>... annotations) {
    Validate.Arg.nonNullArray(annotations, "annotations");
    int i = 0;

    for (int length = annotations.length; i < length; ++i) {
      java.lang.Class<? extends Annotation> annotation = annotations[i];
      Annotation found = this.getAnnotation(annotation);
      if (found != null) {
        return found;
      }
    }

    return null;
  }

  static @NotNull Executable of(@NotNull java.lang.reflect.Executable executable) {
    return new Executable(executable);
  }

  static <T> @NotNull Class<T> of(@NotNull java.lang.Class<T> clazz) {
    return new Class<>(clazz);
  }

  static @NotNull Field of(@NotNull java.lang.reflect.Field field) {
    return new Field(field);
  }

  final class Class<C> implements AnnotationContainer {

    private final @NotNull java.lang.Class<C> javaObject;
    private final @NotNull java.lang.Class<?> declaringClass;

    public Class(@NotNull java.lang.Class<C> javaObject) {
      Objects.requireNonNull(javaObject);
      this.javaObject = javaObject;
      java.lang.Class<?> jClass = getJavaObject().getDeclaringClass();
      if (jClass == null) {
        jClass = Void.class;
      }

      this.declaringClass = jClass;
    }

    public @NotNull java.lang.Class<C> getJavaObject() {
      return this.javaObject;
    }

    public @NotNull java.lang.Class<?> getDeclaringClass() {
      return this.declaringClass;
    }

    public <T extends Annotation> @Nullable T getAnnotation(@NotNull java.lang.Class<T> annotationClass) {
      Objects.requireNonNull(annotationClass);
      return getJavaObject().getAnnotation(annotationClass);
    }

  }

  final class Executable implements AnnotationContainer {

    private final @NotNull java.lang.reflect.Executable javaObject;
    private final @NotNull java.lang.Class<?> declaringClass;

    public Executable(@NotNull java.lang.reflect.Executable javaObject) {
      Objects.requireNonNull(javaObject);
      this.javaObject = javaObject;
      this.declaringClass = getJavaObject().getDeclaringClass();
    }

    public @NotNull java.lang.reflect.Executable getJavaObject() {
      return this.javaObject;
    }

    public @NotNull java.lang.Class<?> getDeclaringClass() {
      return this.declaringClass;
    }

    public <T extends Annotation> @Nullable T getAnnotation(@NotNull java.lang.Class<T> annotationClass) {
      Objects.requireNonNull(annotationClass);
      return this.getJavaObject().getAnnotation(annotationClass);
    }

  }

  final class Field implements AnnotationContainer {

    private final @NotNull java.lang.reflect.Field javaObject;
    private final @NotNull java.lang.Class<?> declaringClass;

    public Field(@NotNull java.lang.reflect.Field javaObject) {
      Validate.Arg.notNull(javaObject, "javaObject");
      this.javaObject = javaObject;
      this.declaringClass = getJavaObject().getDeclaringClass();
    }

    public @NotNull java.lang.reflect.Field getJavaObject() {
      return this.javaObject;
    }

    public @NotNull java.lang.Class<?> getDeclaringClass() {
      return this.declaringClass;
    }

    public <T extends Annotation> @Nullable T getAnnotation(@NotNull java.lang.Class<T> annotationClass) {
      Validate.Arg.notNull(annotationClass, "annotationClass");
      return this.getJavaObject().getAnnotation(annotationClass);
    }

  }

}
