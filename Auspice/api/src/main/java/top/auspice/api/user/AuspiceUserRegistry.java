package top.auspice.api.user;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import net.aurika.namespace.NSKey;
import top.auspice.utils.string.Strings;

import java.util.*;

public final class AuspiceUserRegistry {
    private static final Set<AuspiceUser> ALL_USERS = new HashSet<>();
    private static final Map<String, AuspiceUser> BY_NAME = new HashMap<>();
    private static final Map<String, AuspiceUser> BY_NAMESPACE = new HashMap<>();

    public static @Nullable AuspiceUser getUserFromUserName(@NonNull String name) {
        return BY_NAME.get(name);
    }

    public static @Nullable AuspiceUser getUserFromNamespace(@NonNull String namespace) {
        return BY_NAMESPACE.get(namespace);
    }

    public static void register(AuspiceUser user) {
        Objects.requireNonNull(user, "user instance cannot be null");
        if (Strings.isNullOrEmpty(user.getAuspiceUserName())) {
            throw new IllegalArgumentException("User name can not be empty");
        }
        Objects.requireNonNull(user.namespace(), "user namespace cannot be null");

        if (!NSKey.NAMESPACE_PATTERN.matcher(user.namespace()).matches()) {
            throw new IllegalArgumentException("namespace of auspice uer dosen't matches pattern: " + NSKey.ALLOWED_NAMESPACE);
        }

        AuspiceUser other_name = BY_NAME.put(user.getAuspiceUserName(), user);
        if (other_name != null) {
            (new IllegalStateException("Two Auspice user registered a name: '" + user.getAuspiceUserName() + "', " + other_name + '|' + user)).printStackTrace();
        }

        AuspiceUser other_ns = BY_NAME.put(user.getAuspiceUserName(), user);
        if (other_ns != null) {
            (new IllegalStateException("Two Auspice user registered a namespace: '" + user.getAuspiceUserName() + "', " + other_ns + '|' + user)).printStackTrace();
        }

        ALL_USERS.add(user);
    }
}
