package net.aurika.util.reflection;

import net.aurika.util.collection.ArrayUtils;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * A class for reflecting on your behavior and morals of committing API evasion and using the dreaded NMS.
 */
public final class Reflect {

  /**
   * @param className to autocomplete the class name + package you can use Ctrl+Alt+Space for IntelliJ on Windows.
   */
  public static boolean classExists(@NotNull String className) {
    try {
      // Prevent static initialization
      Class.forName(className, false, Reflect.class.getClassLoader());
      return true;
    } catch (ClassNotFoundException | NoClassDefFoundError e) {
      return false;
    } catch (Throwable e) {
      // Not sure if this'd happen, but some server software like silencing errors for some reason.
      e.printStackTrace();
      return true;
    }
  }

  public static @NotNull Field getDeclaredField(@NotNull Class<?> clazz, @NotNull String @NotNull ... names) throws NoSuchFieldException {
    Validate.Arg.notNull(clazz, "clazz");
    NoSuchFieldException error = null;

    for (String name : names) {
      try {
        return clazz.getDeclaredField(name);
      } catch (NoSuchFieldException ex) {
        if (error == null)
          error = new NoSuchFieldException(
              "Couldn't find any of the fields " + Arrays.toString(names) + " in class: " + clazz);
        error.addSuppressed(ex);
      }
    }

    throw error;
  }

  public static Class<?> @NotNull [] getClassHierarchy(Class<?> clazz, boolean allowAnonymous) {
    List<Class<?>> classes = new ArrayList<>();

    Class<?> lastUpperClass = clazz;
    while ((lastUpperClass = (allowAnonymous ? lastUpperClass.getEnclosingClass() : lastUpperClass.getDeclaringClass())) != null) {
      if (classes.isEmpty()) classes.add(clazz);
      classes.add(lastUpperClass);
    }

    if (classes.isEmpty()) return new Class[]{clazz};

    return ArrayUtils.reverse(classes.toArray(new Class[0]));
  }

  public static @NotNull List<Field> getFields(@NotNull Class<?> clazz) {
    Validate.Arg.notNull(clazz, "clazz");
    List<Field> fields = new ArrayList<>();
    for (Class<?> hierarchy : getClassHierarchy(clazz, false)) {
      fields.addAll(Arrays.asList(hierarchy.getDeclaredFields()));
    }
    return fields;
  }

  public static @NotNull String toString(@NotNull Object obj) {
    Validate.Arg.notNull(obj, "obj");
    Class<?> clazz = obj.getClass();
    List<Field> fields = getFields(clazz);
    StringBuilder string = new StringBuilder(clazz.getSimpleName()).append('{');
    StringJoiner joiner = new StringJoiner(", ");

    for (Field field : fields) {
      if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) continue;
      field.setAccessible(true);
      try {
        joiner.add(field.getName() + '=' + field.get(obj));
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }

    return string.append(joiner).append('}').toString();
  }

}
