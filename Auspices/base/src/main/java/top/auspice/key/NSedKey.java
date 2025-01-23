package top.auspice.key;

import net.kyori.adventure.Adventure;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.Pure;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import top.auspice.data.object.DataStringRepresentation;
import top.auspice.main.Auspice;
import top.auspice.utils.Checker;

import java.util.Objects;

@SuppressWarnings("PatternValidation")
public final class NSedKey implements DataStringRepresentation {

    private final @NotNull String namespace;
    private final @NotNull String key;
    private final int hashCode;

    public static @NotNull NSedKey of(@NotNull @NSKey.Namespace String namespace, @NotNull @NSKey.Key String key) {
        return new NSedKey(namespace, key);
    }

    public NSedKey(@NSKey.Namespace @NotNull String namespace, @NSKey.Key @NotNull String key) {
        this(namespace, key, Objects.hash(namespace, key));
    }

    private NSedKey(@NSKey.Namespace @NotNull String namespace,
                    @NSKey.Key @NotNull String key,
                    int hashCode) {
        Checker.Argument.checkNotNull(namespace, "namespace", "Namespace cannot be null");
        Checker.Argument.checkNotNull(key, "key", "Key cannot be null");

        if (!NSKey.KEY_PATTERN.matcher(key).matches()) {
            throw new IllegalStateException("Key string '" + key + "' doesn't match: " + NSKey.ALLOWED_KEY);
        }

        this.namespace = namespace;
        this.key = key;
        this.hashCode = hashCode;
    }

    @Pure
    public static NSedKey fromString(@NSKey @NotNull String str) {
        Checker.Argument.checkNotNull(str, "str");
        int sep = str.indexOf(':');
        String key = str.substring(sep + 1);
        if (sep == -1) {
            return new NSedKey("Default", key);
        } else {
            String namespace = str.substring(0, sep);
            return new NSedKey(namespace, key);
        }
    }

    @Contract("null -> null")
    public static NSedKey fromConfigString(String configString) {
        if (configString == null) {
            return null;
        }
        int sep = configString.indexOf(':');
        String key = configString.substring(sep + 1);
        if (sep == -1) {
            return null;  //TODO
        } else {
            String namespace = configString.substring(0, sep);
            return new NSedKey(namespace, enumString(key));
        }
    }

    /**
     * 生成对应的配置文件字符串
     *
     * @param abbreviate 是否简写
     */
    public @NonNull String asConfigString(NSKedRegistry<?> module, boolean abbreviate) {
        if (abbreviate) {
            for (NSedKey ns : module.getRegistered().keySet()) {
                if (ns != this && ns.getKey().equals(this.getKey())) {       //若找到任何key相同的命名空间
                    return this.namespace + ':' + configString(this.key);
                }
            }
            return configString(this.getKey());
        } else {
            return this.namespace + ':' + configString(this.key);
        }
    }

    @Pure
    public @NonNull String getConfigOptionName() {
        String keyConfig = configString(this.key);
        return this.namespace + ':' + keyConfig;
    }

    public @NotNull String asString() {
        return this.namespace + ':' + this.key;
    }

    public static @NotNull NSedKey auspice(@NotNull @NSKey.Key String key) {
        Checker.Argument.checkNotNull(key, "key");
        return new NSedKey(Auspice.NAMESPACE, key);
    }

    public static @NotNull NSedKey adventure(@NotNull @NSKey.Key String key) {
        Checker.Argument.checkNotNull(key, "key");
        return new NSedKey(Adventure.NAMESPACE, key);
    }

    public static @NotNull NSedKey minecraft(@NotNull @NSKey.Key String key) {
        Checker.Argument.checkNotNull(key, "key");
        return new NSedKey("minecraft", key);
    }

    public static @NotNull NSedKey kingdoms(@NotNull @NSKey.Key String key) {
        Checker.Argument.checkNotNull(key, "key");
        return new NSedKey("kingdoms", key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NSedKey that)) return false;
        return Objects.equals(this.namespace, that.namespace) && Objects.equals(this.key, that.key);
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    @Override
    public @NotNull String toString() {
        return "NamespacedKey<" + this.namespace + ':' + this.key + '>';
    }

    public @NotNull @NSKey.Namespace String getNamespace() {
        return this.namespace;
    }

    public @NotNull @NSKey.Key String getKey() {
        return this.key;
    }

    @Override
    public @NotNull String asDataString() {
        return this.asString();
    }

    public static boolean isRightKey(@NotNull String key) {
        return NSKey.KEY_PATTERN.matcher(key).matches();
    }

    public static boolean isRightNamespace(@NotNull String namespace) {
        return NSKey.NAMESPACE_PATTERN.matcher(namespace).matches();
    }

    static String configString(String str) {
        char[] chars = str.toCharArray();
        int len = str.length();

        for (int i = 0; i < len; ++i) {
            char ch = chars[i];
            if (ch == '_') {
                chars[i] = '-';
            } else {
                chars[i] = (char) (ch | 32);
            }
        }

        return new String(chars);
    }

    static String enumString(String str) {

        char[] chars = str.toCharArray();
        int len = str.length();

        for (int i = 0; i < len; ++i) {
            char ch = chars[i];
            if (ch == '-') {
                chars[i] = '_';
            } else {
                chars[i] = (char) (ch & 95);
            }
        }

        return new String(chars);
    }
}
