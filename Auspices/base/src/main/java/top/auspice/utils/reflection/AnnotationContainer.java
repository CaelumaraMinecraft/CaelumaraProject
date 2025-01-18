package top.auspice.utils.reflection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public interface AnnotationContainer {

    @Nullable
    <T extends Annotation> T getAnnotation(@NotNull java.lang.Class<T> var1);

    @NotNull
    Object getJavaObject();

    @NotNull
    java.lang.Class<?> getDeclaringClass();

    @NotNull
    default List<Annotation> findAll(@NotNull java.lang.Class<? extends Annotation>... annotations) {
        Objects.requireNonNull(annotations, "annotations");
        List<Annotation> found = (new ArrayList<>());
        int var3 = 0;

        for (int var4 = annotations.length; var3 < var4; ++var3) {
            java.lang.Class<? extends Annotation> annotation = annotations[var3];
            Annotation annotaiton = this.getAnnotation(annotation);
            if (annotaiton != null) {
                found.add(annotaiton);
            }
        }

        return found;
    }

    @Nullable
    default Annotation any(@NotNull java.lang.Class<? extends Annotation>... annotations) {
        Objects.requireNonNull(annotations);
        int var2 = 0;

        for (int var3 = annotations.length; var2 < var3; ++var2) {
            java.lang.Class<? extends Annotation> annotation = annotations[var2];
            Annotation annotaiton = this.getAnnotation(annotation);
            if (annotaiton != null) {
                return annotaiton;
            }
        }

        return null;
    }

    @NotNull
    static Executable of(@NotNull java.lang.reflect.Executable executable) {
        return new Executable(executable);
    }

    @NotNull
    static <T> Class<T> of(@NotNull java.lang.Class<T> clazz) {
        return new Class<>(clazz);
    }

    @NotNull
    static Field of(@NotNull java.lang.reflect.Field field) {
        return new Field(field);
    }


    final class Class<C> implements AnnotationContainer {
        @NotNull
        private final java.lang.Class<C> javaObject;
        @NotNull
        private final java.lang.Class<?> declaringClass;

        public Class(@NotNull java.lang.Class<C> javaObject) {
            Objects.requireNonNull(javaObject);
            this.javaObject = javaObject;
            java.lang.Class<?> var10001 = this.getJavaObject().getDeclaringClass();
            if (var10001 == null) {
                var10001 = Void.class;
            }

            this.declaringClass = var10001;
        }

        @NotNull
        public java.lang.Class<C> getJavaObject() {
            return this.javaObject;
        }

        @NotNull
        public java.lang.Class<?> getDeclaringClass() {
            return this.declaringClass;
        }

        @Nullable
        public <T extends Annotation> T getAnnotation(@NotNull java.lang.Class<T> annotationClass) {
            Objects.requireNonNull(annotationClass);
            return this.getJavaObject().getAnnotation(annotationClass);
        }
    }


    final class Executable implements AnnotationContainer {
        @NotNull
        private final java.lang.reflect.Executable javaObject;
        @NotNull
        private final java.lang.Class<?> declaringClass;

        public Executable(@NotNull java.lang.reflect.Executable javaObject) {
            Objects.requireNonNull(javaObject);
            this.javaObject = javaObject;
            java.lang.Class<?> var10001 = this.getJavaObject().getDeclaringClass();
            Objects.requireNonNull(var10001);
            this.declaringClass = var10001;
        }

        @NotNull
        public java.lang.reflect.Executable getJavaObject() {
            return this.javaObject;
        }

        @NotNull
        public java.lang.Class<?> getDeclaringClass() {
            return this.declaringClass;
        }

        @Nullable
        public <T extends Annotation> T getAnnotation(@NotNull java.lang.Class<T> annotationClass) {
            Objects.requireNonNull(annotationClass);
            return this.getJavaObject().getAnnotation(annotationClass);
        }
    }


    final class Field implements AnnotationContainer {
        @NotNull
        private final java.lang.reflect.Field javaObject;
        @NotNull
        private final java.lang.Class<?> declaringClass;

        public Field(@NotNull java.lang.reflect.Field javaObject) {
            Objects.requireNonNull(javaObject);
            this.javaObject = javaObject;
            java.lang.Class<?> var10001 = this.getJavaObject().getDeclaringClass();
            Objects.requireNonNull(var10001);
            this.declaringClass = var10001;
        }

        @NotNull
        public java.lang.reflect.Field getJavaObject() {
            return this.javaObject;
        }

        @NotNull
        public java.lang.Class<?> getDeclaringClass() {
            return this.declaringClass;
        }

        @Nullable
        public <T extends Annotation> T getAnnotation(@NotNull java.lang.Class<T> annotationClass) {
            Objects.requireNonNull(annotationClass, "annotationClass");
            return this.getJavaObject().getAnnotation(annotationClass);
        }
    }
}
