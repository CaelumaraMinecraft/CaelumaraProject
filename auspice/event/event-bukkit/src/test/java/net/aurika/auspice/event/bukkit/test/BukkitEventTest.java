package net.aurika.auspice.event.bukkit.test;

import net.aurika.auspice.event.bukkit.BukkitEvent;
import net.aurika.auspice.event.bukkit.BukkitEventGenerator;
import org.bukkit.event.player.PlayerChatEvent;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class BukkitEventTest {

  public static void main(String[] args) {
    Class<? extends org.bukkit.event.Event> bukkitEventClass = PlayerChatEvent.class;
    Class<? extends BukkitEvent> auspiceEventClass = TestBukkitPlayerChatEvent.class;

    BukkitEventGenerator eventGenerator = new BukkitEventGenerator();
    Class<?> generatedEventClass = eventGenerator.generateEventClass(
        bukkitEventClass, auspiceEventClass, new Class[]{});
    System.out.println(generatedEventClass);
    System.out.println("Extends: " + generatedEventClass.getSuperclass());
    System.out.println("Implements: " + Arrays.toString(generatedEventClass.getInterfaces()));
    System.out.println();
    System.out.println("Annotations:");
    for (Annotation annotation : generatedEventClass.getAnnotations()) {
      System.out.println(annotation);
    }
    System.out.println();
    System.out.println("Constructors:");
    for (Constructor<?> constructor : generatedEventClass.getConstructors()) {
      System.out.println(constructor);
    }
    System.out.println();
    System.out.println("Fields:");
    for (Field field : generatedEventClass.getDeclaredFields()) {
      System.out.println(field);
    }
    System.out.println();
    System.out.println("Methods:");
    for (Method method : generatedEventClass.getMethods()) {
      System.out.println(method);


      if (method.getName().equals("emitter")) {
        try {
          method.setAccessible(true);
          Object result = method.invoke(null);
          System.out.println("\n" + result + "\n");
        } catch (InvocationTargetException | IllegalAccessException e) {
          throw new RuntimeException(e);
        }
      }
    }
    System.out.println();
  }

}
