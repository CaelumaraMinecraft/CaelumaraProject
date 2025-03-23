package net.aurika.config.sections.label;

import org.snakeyaml.engine.v2.common.UriEncoder;

import java.util.Objects;

public final class Label {

    public static final String PREFIX = "";
    /**
     * 待决定的
     */
    public static final Label AUTO = new Label("auto");

    public static final Label SET = new Label(PREFIX + "set");
    public static final Label BINARY = new Label(PREFIX + "binary");
    public static final Label INT = new Label(PREFIX + "int");
    public static final Label FLOAT = new Label(PREFIX + "float");
    public static final Label BOOL = new Label(PREFIX + "bool");
    public static final Label NULL = new Label(PREFIX + "null");
    public static final Label STR = new Label(PREFIX + "str");
    public static final Label SEQ = new Label(PREFIX + "seq");
    public static final Label MAP = new Label(PREFIX + "map");

    public static final Label ENV_TAG = new Label("!ENV_VARIABLE");

    private final String value;

    public Label(String tag) {
        Objects.requireNonNull(tag, "Tag must be provided.");
        if (tag.isEmpty()) {
            throw new IllegalArgumentException("Tag must not be empty.");
        } else if (tag.trim().length() != tag.length()) {
            throw new IllegalArgumentException("Tag must not contain leading or trailing spaces.");
        }
        this.value = UriEncoder.encode(tag);
    }

    /**
     * Create a global tag to dump the fully qualified class name
     *
     * @param clazz - the class to use the name
     */
    public Label(Class<?> clazz) {
        Objects.requireNonNull(clazz, "Class for tag must be provided.");
        this.value = Label.PREFIX + UriEncoder.encode(clazz.getName());
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return this.value;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Label) {
            return value.equals(((Label) obj).getValue());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return value.hashCode();
    }
}
