package net.aurika.common.key;

import net.aurika.common.data.DataStringRepresentation;
import net.aurika.validate.Validate;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public interface Key extends Namespaced, DataStringRepresentation {
    char SEPARATOR = ':';

    /**
     * Create a {@linkplain Key} from a full key string.
     * Such as: {@code "Aitclub:TEXT"} {@code "Bulug:CREATE_HOME"}
     *
     * @param keyString The full key string
     */
    @SuppressWarnings("PatternValidation")
    static @NotNull Key key(@KeyPatterns.Key final @NotNull String keyString) {
        Validate.Arg.notEmpty(keyString, "keyString");
        int sepI = keyString.indexOf(SEPARATOR);
        String namespace = keyString.substring(0, sepI);
        String value = keyString.substring(sepI + 1);
        return key(namespace, value);
    }

    static @NotNull Key key(
            @KeyPatterns.Namespace final @NotNull String namespace,
            @KeyPatterns.KeyValue final @NotNull String value
    ) {
        return new KeyImpl(namespace, value);
    }

    @KeyPatterns.Namespace
    @NotNull String namespace();

    @KeyPatterns.KeyValue
    @NotNull String value();

    @NotNull String asDataString();

    boolean equals(@NotNull Key other);

    boolean equals(Object obj);

    int hashCode();
}

class KeyImpl extends NamespacedImpl implements Key {

    @Language("RegExp")
    static final String ALLOWED_KEY_VALUE = "[A-Z0-9_]{3,50}";
    static final java.util.regex.Pattern KEY_VALUE_PATTERN = java.util.regex.Pattern.compile(ALLOWED_NAMESPACE);
    @Language("RegExp")
    static final String ALLOWED_KEY = "(?:(" + ALLOWED_NAMESPACE + ":)?|:)" + ALLOWED_KEY_VALUE;
    static final java.util.regex.Pattern KEY_PATTERN = java.util.regex.Pattern.compile(ALLOWED_KEY);

    @KeyPatterns.KeyValue
    private final @NotNull String value;

    KeyImpl(@KeyPatterns.Namespace final @NotNull String namespace, @KeyPatterns.KeyValue final @NotNull String value) {
        super(namespace);
        Validate.Arg.notEmpty(value, "value");
        Validate.Arg.require(
                KEY_VALUE_PATTERN.matcher(value).matches(),
                "Key value '" + value + "' is not matches regex " + ALLOWED_KEY_VALUE
        );
        this.value = value;
    }

    @KeyPatterns.KeyValue
    public @NotNull String value() {
        return value;
    }

    public @NotNull String asDataString() {
        return namespace() + SEPARATOR + this.value;
    }

    @Override
    public boolean equals(@NotNull Key other) {
        Validate.Arg.notNull(other, "other");
        return Objects.equals(this.namespace(), other.namespace()) && Objects.equals(this.value(), other.value());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof KeyImpl key)) return false;
        return equals(key);
    }

    @Override
    public int hashCode() {
        int result = namespace().hashCode();
        result = (31 * result) + this.value.hashCode();
        return result;
    }
}
