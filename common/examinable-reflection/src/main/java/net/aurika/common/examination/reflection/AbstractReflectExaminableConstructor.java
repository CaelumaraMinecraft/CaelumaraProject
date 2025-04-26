package net.aurika.common.examination.reflection;

import net.aurika.common.examination.ExaminableConstructorRegistry;
import net.aurika.common.validate.Validate;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import net.kyori.examination.Examiner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Stream;

public class AbstractReflectExaminableConstructor<E extends Examinable> implements ReflectionExaminableConstructor<E> {

  private final @NotNull Class<? extends E> examinableType;
  private final @NotNull ExaminableConstructor parametersAnnotation;
  private final @NotNull Executable examinableConstructor;
  private final @NotNull LinkedList<String> parameterNames;
  private final int arity;

  public AbstractReflectExaminableConstructor(
      @NotNull Class<? extends E> examinableType,
      @NotNull ExaminableConstructor parametersAnnotation,
      @NotNull Executable examinableConstructor
  ) {
    Validate.Arg.notNull(examinableType, "examinableType");
    Validate.Arg.notNull(parametersAnnotation, "parametersAnnotation");
    Validate.Arg.notNull(examinableConstructor, "examinableConstructor");
    this.examinableType = examinableType;
    this.parametersAnnotation = parametersAnnotation;
    this.examinableConstructor = examinableConstructor;
    String[] params = parametersAnnotation.properties();
    if (examinableConstructor.getParameterCount() != params.length) {
      throw new IllegalArgumentException(
          "Wrong number of parameters, annotation got " + params.length + ", but annotated executable got: " + examinableConstructor.getParameterCount());
    }
    this.parameterNames = new LinkedList<>(Arrays.asList(params));
    this.arity = params.length;
  }

  @Override
  public @NotNull E construct(@NotNull Stream<? extends ExaminableProperty> properties, @Nullable ExaminableConstructorRegistry constructorRegistry) {
    ExaminableProperty[] props = properties.toArray(ExaminableProperty[]::new);
    if (props.length != arity) {
      throw new IllegalArgumentException(
          "Wrong number of examinable properties, require: " + arity + ", but got: " + props.length);
    }
    ExaminableProperty[] sortedProps = new ExaminableProperty[arity];

    Collector collector = new Collector();

    for (int i = 0; i < arity; i++) {
      ExaminableProperty prop = props[i];
      String name = prop.name();
      int sortedIndex = parameterNames.indexOf(name);
      if (sortedIndex == -1) {
        throw new IllegalArgumentException(/* TODO */);
      }
      sortedProps[i] = prop;
    }

    for (ExaminableProperty prop : sortedProps) {
      collector.name(prop.name());
      prop.examine(collector);
    }

    Object[] args = collector.properties.values().toArray(new Object[0]);
    if (examinableConstructor instanceof Method) {
      Method examinableConstructorMethod = (Method) examinableConstructor;
      try {
        examinableConstructorMethod.setAccessible(true);
        return (E) examinableConstructorMethod.invoke(null, args);
      } catch (IllegalAccessException | InvocationTargetException e) {
        throw new RuntimeException(e);
      }
    }
    if (examinableConstructor instanceof Constructor<?>) {
      // noinspection unchecked
      Constructor<E> examinableConstructorConstructor = (Constructor<E>) examinableConstructor;
      try {
        examinableConstructorConstructor.setAccessible(true);
        return examinableConstructorConstructor.newInstance(args);
      } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
    throw new IllegalStateException();
  }

  @Override
  public @NotNull Class<? extends E> examinableType() {
    return this.examinableType;
  }

  class Collector implements Examiner<Map<? extends String, ?>> {

    private final @NotNull Map<String, Object> properties;

    private @Nullable String name;

    protected Collector() {
      this(new LinkedHashMap<>());
    }

    protected Collector(@NotNull Map<String, Object> properties) {
      Validate.Arg.notNull(properties, "properties");
      this.properties = properties;
    }

    protected void name(String name) {
      this.name = name;
    }

    protected void set(Object value) {
      assert this.name != null;
      properties.put(this.name, value);
      this.name = null;
    }

    @Override
    public @NotNull Map<? extends String, ?> examine(@NotNull String name, @NotNull Stream<? extends ExaminableProperty> properties) {
      properties.forEach(examinableProperty -> {
        this.name = examinableProperty.name();
        this.examine(examinableProperty);
      });
      return this.properties;
    }

    @Override
    public @NotNull Map<? extends String, ?> examine(@Nullable Object value) {

      // == test only ==
      System.out.println("Collect single: " + this.name + ": " + value);

      set(value);
      return this.properties;
    }

    @Override
    public @NotNull Map<? extends String, ?> examine(boolean value) {
      set(value);

      return this.properties;
    }

    @Override
    public @NotNull Map<? extends String, ?> examine(boolean @Nullable [] values) {
      set(values);

      return this.properties;
    }

    @Override
    public @NotNull Map<? extends String, ?> examine(byte value) {
      set(value);

      return this.properties;
    }

    @Override
    public @NotNull Map<? extends String, ?> examine(byte @Nullable [] values) {
      set(values);

      return this.properties;
    }

    @Override
    public @NotNull Map<? extends String, ?> examine(char value) {
      set(value);
      return this.properties;
    }

    @Override
    public @NotNull Map<? extends String, ?> examine(char @Nullable [] values) {
      set(values);
      return this.properties;
    }

    @Override
    public @NotNull Map<? extends String, ?> examine(double value) {
      set(value);
      return this.properties;
    }

    @Override
    public @NotNull Map<? extends String, ?> examine(double @Nullable [] values) {
      set(values);
      return this.properties;
    }

    @Override
    public @NotNull Map<? extends String, ?> examine(float value) {
      set(value);
      return this.properties;
    }

    @Override
    public @NotNull Map<? extends String, ?> examine(float @Nullable [] values) {
      set(values);
      return this.properties;
    }

    @Override
    public @NotNull Map<? extends String, ?> examine(int value) {

      // == test only ==
      System.out.println("Collect single: " + this.name + ": " + value);

      set(value);
      return this.properties;
    }

    @Override
    public @NotNull Map<? extends String, ?> examine(int @Nullable [] values) {
      set(values);
      return this.properties;
    }

    @Override
    public @NotNull Map<? extends String, ?> examine(long value) {
      set(value);
      return this.properties;
    }

    @Override
    public @NotNull Map<? extends String, ?> examine(long @Nullable [] values) {
      set(values);
      return this.properties;
    }

    @Override
    public @NotNull Map<? extends String, ?> examine(short value) {
      set(value);
      return this.properties;
    }

    @Override
    public @NotNull Map<? extends String, ?> examine(short @Nullable [] values) {
      set(values);
      return this.properties;
    }

    @Override
    public @NotNull Map<? extends String, ?> examine(@Nullable String value) {
      set(value);
      return this.properties;
    }

  }

}
