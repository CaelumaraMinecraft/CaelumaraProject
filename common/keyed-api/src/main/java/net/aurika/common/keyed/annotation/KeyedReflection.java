package net.aurika.common.keyed.annotation;

import net.aurika.common.annotation.container.ThrowOnAbsent;
import net.aurika.common.keyed.Keyed;
import net.aurika.common.validate.Validate;
import net.aurika.util.reflection.AnnotationReflect;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class KeyedReflection {

  @ThrowOnAbsent
  public static @NotNull Object getKey(@NotNull Object keyed) {
    Validate.Arg.notNull(keyed, "keyed");
    if (keyed instanceof Keyed) {
      return ((Keyed<?>) keyed).key();
    }
    Class<?> clazz = keyed.getClass();
    KeyedBy keyAnn = AnnotationReflect.findAnnotationHierarchy(clazz, KeyedBy.class);
    if (keyAnn != null) {
      String keyGetterName = keyAnn.value();
      try {
        Method keyGetter = clazz.getMethod(keyGetterName, new Class[0]);
        keyGetter.setAccessible(true);
        return keyGetter.invoke(keyed);
      } catch (NoSuchMethodException e) {
        throw new RuntimeException("Cannot find the key getter for the class: " + clazz, e);
      } catch (InvocationTargetException e) {
        throw new RuntimeException("Error when get the key for the keyed object: " + keyed, e);
      } catch (IllegalAccessException e) {
        throw new RuntimeException("Cannot access the key getter for the keyed object: " + keyed, e);
      }
    } else {
      throw new RuntimeException("Cannot find the annotation " + KeyedBy.class.getName() + " on class: " + clazz);
    }
  }

}
