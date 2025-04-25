package net.aurika.auspice.event.bukkit;

import net.aurika.common.annotation.container.NoUseCache;
import net.aurika.common.annotation.container.UseCache;
import net.aurika.common.event.EmitterMethodName;
import net.aurika.common.event.Listenable;
import net.aurika.common.validate.Validate;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.LoadedTypeInitializer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class BukkitEventGenerator {

  /**
   * Finds the handler list from a bukkit event class.
   *
   * @see org.bukkit.event.Event
   */
  protected static @Nullable HandlerList findHandlerList(@NotNull Class<? extends org.bukkit.event.Event> bukkitEventClass) {
    try {
      Method handlerListGetter = bukkitEventClass.getMethod("getHandlerList");
      Object result = handlerListGetter.invoke(null); // static
      return (HandlerList) result;
    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
      return null;
    }
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private static boolean isAllInherited(@NotNull Class generated, Class bukkitEventClass, Class eventInterface, Class[] otherInterfaces) {
    if (!bukkitEventClass.isAssignableFrom(generated)) {
      return false;
    }
    if (!eventInterface.isAssignableFrom(generated)) {
      return false;
    }
    for (Class otherInterface : otherInterfaces) {
      if (!otherInterface.isAssignableFrom(generated)) {
        return false;
      }
    }
    return true;
  }

  /**
   * The cache. Key is the platform event type.
   */
  protected final @NotNull Map<Class<? extends Event>, EventGenerationCacheEntry> cache;

  public BukkitEventGenerator() { this(new HashMap<>()); }

  public BukkitEventGenerator(@NotNull Map<Class<? extends Event>, EventGenerationCacheEntry> cache) {
    Validate.Arg.notNull(cache, "cache");
    this.cache = cache;
  }

  @UseCache
  public <E extends org.bukkit.event.Event & BukkitEvent> @NotNull Class<E> generateEventClass(
      @NotNull Class<? extends org.bukkit.event.Event> bukkitEventClass,
      @NotNull Class<? extends BukkitEvent> eventInterface,
      @NotNull Class<?> @NotNull [] interfaces
  ) {
    String generatedEventClassName = "net.aurika.auspice.platform.bukkit.event.generated." + bukkitEventClass.getName();
    return generateEventClass(
        generatedEventClassName, bukkitEventClass.getClassLoader(), bukkitEventClass, eventInterface, interfaces);
  }

  @UseCache
  protected <E extends org.bukkit.event.Event & BukkitEvent> @NotNull Class<E> generateEventClass(
      @NotNull String generatedClassName,
      @NotNull ClassLoader classLoader,
      @NotNull Class<? extends org.bukkit.event.Event> bukkitEventClass,
      @NotNull Class<? extends BukkitEvent> eventInterface,
      @NotNull Class<?> @NotNull [] otherInterfaces
  ) {
    Validate.Arg.notNull(generatedClassName, "generatedClassName");
    Validate.Arg.notNull(classLoader, "classLoader");
    Validate.Arg.notNull(bukkitEventClass, "bukkitEventClass");
    Validate.Arg.notNull(eventInterface, "eventInterface");
    Validate.Arg.nonNullArray(otherInterfaces, "otherInterfaces");
    // TODO check the class name

    if (cache.containsKey(bukkitEventClass)) {
      EventGenerationCacheEntry cacheEntry = cache.get(bukkitEventClass);
      Class<E> prevGenerated = cacheEntry.generatedEventClass();
      if (prevGenerated != null && isAllInherited(prevGenerated, bukkitEventClass, eventInterface, otherInterfaces)) {
        return prevGenerated;
      }
    }
    Class<E> generated = createEventClass(
        generatedClassName, classLoader, bukkitEventClass, eventInterface, otherInterfaces);
    EventGenerationCacheEntry cacheEntry = new EventGenerationCacheEntry(
        bukkitEventClass, eventInterface, otherInterfaces);
    cacheEntry.generatedEventClass = generated;
    cache.put(bukkitEventClass, cacheEntry);
    return generated;
  }

  @NoUseCache
  protected <E extends org.bukkit.event.Event & BukkitEvent> @NotNull Class<E> createEventClass(
      @NotNull String generatedClassName,
      @NotNull ClassLoader classLoader,
      @NotNull Class<? extends org.bukkit.event.Event> bukkitEventClass,
      @NotNull Class<? extends BukkitEvent> eventInterface,
      @NotNull Class<?> @NotNull [] otherInterfaces
  ) {
    Validate.Arg.notNull(generatedClassName, "generatedClassName");
    Validate.Arg.notNull(classLoader, "classLoader");
    Validate.Arg.notNull(bukkitEventClass, "bukkitEventClass");
    Validate.Arg.notNull(eventInterface, "eventInterface");
    Validate.Arg.nonNullArray(otherInterfaces, "otherInterfaces");
    // TODO check the class name

    @Nullable HandlerList handlerList = findHandlerList(bukkitEventClass);

    boolean generateEmitter = handlerList != null;

    // 3. 检查是否有抽象方法
    boolean hasAbstractMethod = false;
    for (Method method : bukkitEventClass.getMethods()) {
      if (Modifier.isAbstract(method.getModifiers())) {
        hasAbstractMethod = true;
        break;
      }
    }
    for (Class<?> intf : otherInterfaces) {
      for (Method method : intf.getMethods()) {
        if (Modifier.isAbstract(method.getModifiers())) {
          hasAbstractMethod = true;
          break;
        }
      }
    }

    final Late<Class<E>> generatedEventClassLate = new Late<>();

    ByteBuddy byteBuddy = new ByteBuddy();
    DynamicType.Builder<?> builder = byteBuddy
        .subclass(bukkitEventClass)
        .implement(otherInterfaces)
        .name(generatedClassName)
        .modifiers(hasAbstractMethod ? Modifier.PUBLIC | Modifier.ABSTRACT : Modifier.PUBLIC);
    // 4. 生成 emitter
    if (generateEmitter) {

      String emitterFieldName = "generatedEmitter";
      String emitterMethodName = EmitterMethodName.DEFAULT_VALUE;

      BukkitEventHandlerEmitter<E> emitter = new AbstractBukkitEventHandlerEmitter<E>(handlerList) {
        @Override
        public @NotNull Class<E> eventType() { return generatedEventClassLate.get(); }
      };

      builder = builder
          .annotateType(new Listenable() {
            @Override
            public @NotNull Class<? extends Listenable> annotationType() {
              return Listenable.class;
            }
          })
          // 生成字段 private static final BukkitEventEmitter emitter_filed_name = ...;
          .initializer(new LoadedTypeInitializer.ForStaticField(emitterFieldName, emitter))
//          .defineField(emitterFieldName, BukkitEventEmitter.class, Modifier.PRIVATE | Modifier.STATIC | Modifier.FINAL)
          .defineField(emitterFieldName, BukkitEventHandlerEmitter.class, Modifier.PRIVATE | Modifier.STATIC)
          // 生成方法 "public static Emitter emitter();"
          .defineMethod(emitterMethodName, BukkitEventHandlerEmitter.class, Modifier.PUBLIC | Modifier.STATIC)
          .withParameters(new Type[]{})
          .intercept(FieldAccessor.ofField(emitterFieldName));
    }

    DynamicType.Unloaded<?> unloadedType = builder.make();
    // noinspection unchecked
    Class<E> generatedEventClass = (Class<E>) unloadedType.load(classLoader).getLoaded();
    // 6. 延迟初始化
    generatedEventClassLate.set(generatedEventClass);

    return generatedEventClass;
  }

  public @Nullable EventGenerationCacheEntry findCache(@NotNull Class<? extends Event> bukkitEventClass) {
    return cache.get(bukkitEventClass);
  }

  public static class EventGenerationCacheEntry {

    private final @NotNull Class<? extends org.bukkit.event.Event> bukkitEventType;
    private final @NotNull Class<? extends BukkitEvent> eventInterface;
    private final @NotNull Class<?> @NotNull [] otherInterfaces;

    protected Class generatedEventClass;

    public EventGenerationCacheEntry(
        @NotNull Class<? extends org.bukkit.event.Event> bukkitEventType,
        @NotNull Class<? extends BukkitEvent> eventInterface,
        @NotNull Class<?> @NotNull [] otherInterfaces
    ) {
      Validate.Arg.notNull(bukkitEventType, "bukkitEventType");
      Validate.Arg.notNull(eventInterface, "eventInterface");
      Validate.Arg.notNull(otherInterfaces, "otherInterfaces");
      this.bukkitEventType = bukkitEventType;
      this.eventInterface = eventInterface;
      this.otherInterfaces = otherInterfaces;
    }

    public @NotNull Class<? extends org.bukkit.event.Event> bukkitEventType() { return this.bukkitEventType; }

    public @NotNull Class<? extends BukkitEvent> eventInterface() { return this.eventInterface; }

    public @NotNull Class<?> @NotNull [] otherInterfaces() { return this.otherInterfaces; }

    @SuppressWarnings("unchecked")
    public <E extends org.bukkit.event.Event & BukkitEvent> @Nullable Class<E> generatedEventClass() {
      return this.generatedEventClass;
    }

  }

  static final class Late<T> {

    private T value;
    private boolean set;

    public T get() {
      if (set) {
        return value;
      } else {
        throw new AssertionError();
      }
    }

    public void set(T value) {
      if (set) {
        throw new AssertionError();
      } else {
        this.value = value;
        set = true;
      }
    }

  }

}
