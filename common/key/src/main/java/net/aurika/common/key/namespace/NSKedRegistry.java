package net.aurika.common.key.namespace;

import net.aurika.auspice.api.user.AuspiceUser;
import net.aurika.validate.Validate;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class NSKedRegistry<C extends Keyed> implements Keyed {

  static final Map<AuspiceUser, Map<NSedKey, NSKedRegistry<?>>> allRegistries = new HashMap<>();

  private final @NotNull NSedKey identity;
  private final @NotNull AuspiceUser provider;
  private final @NotNull String module;
  protected @NotNull Map<NSedKey, C> registered;

  protected NSKedRegistry(@NotNull AuspiceUser provider, @NotNull @NSKey.Key String module) {
    this(provider, module, new HashMap<>());
  }

  protected NSKedRegistry(@NotNull AuspiceUser provider, @NotNull @NSKey.Key String module, @NotNull Map<NSedKey, C> registry) {
    Validate.Arg.notNull(provider, "provider");
    Validate.Arg.notNull(module, "module");
    Validate.Arg.notNull(registry, "registry");

    if (!NSKey.KEY_PATTERN.matcher(module).matches()) {
      throw new IllegalArgumentException("Invalid module: '" + module + "': doesnt match: " + NSKey.ALLOWED_KEY);
    }

    NSedKey identity = NSedKey.of(provider.getNamespace(), module);
    this.identity = identity;
    this.provider = provider;
    this.module = module;
    this.registered = registry;

    Map<NSedKey, NSKedRegistry<?>> providedRegistries = allRegistries.computeIfAbsent(provider, k -> new HashMap<>());
    providedRegistries.put(identity, this);
  }

  public void register(@NotNull C value) {
    Validate.Arg.notNull(value, "value", "Cannot register null object");
    NSedKey NSedKey = value.getNamespacedKey();
    Objects.requireNonNull(NSedKey, "Cannot register object provides null namespaced key");
    C prev = this.registered.putIfAbsent(NSedKey, value);
    if (prev != null) {
      throw new IllegalArgumentException(NSedKey + " was already registered");
    }
  }

  public C getRegistered(@NonNull NSedKey NSedKey) {
    return this.registered.get(NSedKey);
  }

  public boolean isRegistered(@NonNull NSedKey NSedKey) {
    return this.registered.containsKey(NSedKey);
  }

  public @NotNull @Unmodifiable Map<NSedKey, C> getRegistry() {
    return Collections.unmodifiableMap(this.registered);
  }

  /**
   * @see NSKedRegistry#getRegisteredNamespaceFromConfigString(String, boolean, boolean, boolean)
   */
  @Nullable
  public Keyed getRegisteredFromConfigString(@NotNull String str, boolean checkAbbreviation, boolean throwExceptionWhenSepNotFound, boolean checkRepeated) {
    NSedKey ns = getRegisteredNamespaceFromConfigString(
        str, checkAbbreviation, throwExceptionWhenSepNotFound, checkRepeated);
    if (ns != null) {
      return this.registered.get(ns);
    } else {
      return null;
    }
  }

  @Nullable
  public NSedKey getRegisteredNamespaceFromConfigString(@NotNull String str) {
    return getRegisteredNamespaceFromConfigString(str, true, true, false);
  }

  @Nullable
  public NSedKey getRegisteredNamespaceFromConfigString(@NotNull String str,
                                                        boolean allowAbbreviated,
                                                        boolean checkRepeated) {
    return getRegisteredNamespaceFromConfigString(str, allowAbbreviated, true, checkRepeated);
  }

  /**
   * 从一个命名空间注册器中通过配置文件内的字符串, 寻找对应的命名空间
   *
   * @param str                           用于配置文件的命名空间字符串, 比如 {@code ”Auspice:custom“}
   * @param allowAbbreviated              是否允许简写, 比如在一个 {@link NSKedRegistry} 实例中只有一个 key 为 {@code "custom"} 的 {@link NSedKey} 时可以简写为 custom
   * @param throwExceptionWhenSepNotFound 如果为 {@code true} , 在输入的名称中不带有 ':' 会抛出异常
   * @param checkRepeated                 是否检查 key 重复的 {@link NSedKey}<p>
   *                                      若为 {@code true} 且 {@param str} 为简写, 找到了重复的 {@link NSedKey} 时, 会抛出异常<p>
   *                                      若为 {@code false} 则会返回最后找到的 {@link NSedKey}
   * @return 找到的 {@link NSedKey}
   * @throws IllegalArgumentException      str中不包含冒号且 {@param allowAbbreviated} 为 {@code true} 时
   * @throws NamespaceKeyRepeatedException 对应简写字符串能找到多个命名空间时
   */
  @Nullable
  public NSedKey getRegisteredNamespaceFromConfigString(@NotNull String str,
                                                        boolean allowAbbreviated,
                                                        boolean throwExceptionWhenSepNotFound,
                                                        boolean checkRepeated) {
    int sep = str.indexOf(':');
    NSedKey foundedNs = null;
    if (sep == -1) {                  //若找不到':'
      if (allowAbbreviated) {       //若允许简写
        for (NSedKey ns : this.registered.keySet()) {
          if (ns.getKey().equals(NSedKey.enumString(str))) {
            if (!checkRepeated) {     //若不检查重复, 直接返回
              return ns;
            } else if (foundedNs != null) {
              throw new NamespaceKeyRepeatedException(
                  "Get repeated namespace from abbreviated string: '" + ns + "'and '" + foundedNs + '\'');
            }
            foundedNs = ns;
          }
        }
      } else {                      //若不允许简写
        if (throwExceptionWhenSepNotFound) {
          throw new IllegalArgumentException(
              "Namespace string need to have separator ':' when not checking abbreviation");
        } else {
          return null;
        }
      }
    } else {                          //当找得到':'的时候
      String namespaceStr = str.substring(0, sep);
      String keyString = NSedKey.enumString(str.substring(sep + 1));
      for (NSedKey ns : this.registered.keySet()) {
        if (ns.getNamespace().equals(namespaceStr) && ns.getKey().equals(keyString)) {
          return ns;
        }
      }
    }
    return foundedNs;
  }

  @Nullable
  public C getRegisteredFromString(String str) {
    NSedKey ns = getRegisteredNamespaceFromString(str);
    return ns != null ? this.registered.get(ns) : null;
  }

  /**
   * 从一个命名空间注册器中通过命名空间字符串, 寻找对应的命名空间
   *
   * @param str 命名空间字符串, 比如 {@code "Auspice:CUSTOM"}, 它一定要带有':'
   * @return 找到的 {@link NSedKey}
   */
  @Nullable
  public NSedKey getRegisteredNamespaceFromString(String str) {
    int sep = str.indexOf(':');
    if (sep == -1) {
      throw new IllegalArgumentException("Namespace string must be separated by ':'");
    }
    String namespaceStr = str.substring(0, sep);
    String keyString = NSedKey.enumString(str.substring(sep + 1));
    for (NSedKey ns : this.registered.keySet()) {
      if (ns.getNamespace().equals(namespaceStr) && ns.getKey().equals(keyString)) {
        return ns;
      }
    }
    return null;
  }

  public @NotNull AuspiceUser getProvider() {
    return this.provider;
  }

  public @NotNull String getModule() {
    return this.module;
  }

  public @NotNull NSedKey getIdentity() {
    return this.identity;
  }

  public @NotNull NSedKey getNamespacedKey() {
    return this.identity;
  }

  public static NSKedRegistry<?> findRegistry(@NotNull AuspiceUser provider, @NotNull NSedKey identity) {
    Map<NSedKey, NSKedRegistry<?>> modules = getModules(provider);
    return modules == null ? null : modules.get(identity);
  }

  public static @Nullable Map<NSedKey, NSKedRegistry<?>> getModules(@NotNull AuspiceUser provider) {
    return allRegistries.get(provider);
  }

}
