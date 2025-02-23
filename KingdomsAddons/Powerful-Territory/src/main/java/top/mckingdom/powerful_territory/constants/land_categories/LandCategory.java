package top.mckingdom.powerful_territory.constants.land_categories;

import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.constants.namespace.Namespaced;
import org.kingdoms.locale.Language;

public abstract class LandCategory implements Namespaced {

    private static int hashCount = 0;

    private final @NotNull Namespace namespace;
    private final boolean editable;
    private final int hash;

    protected LandCategory(@NotNull Namespace namespace, boolean editable) {
        this.namespace = namespace;
        this.editable = editable;
        this.hash = hash();
    }

    private synchronized static int hash() {
        return hashCount++;
    }

    /**
     * Gets this {@linkplain LandCategory} as a config key.
     *
     * @return the config key
     */
    public final @NotNull String toConfigKey() {
        return this.namespace.getConfigOptionName();
    }

    public abstract @NotNull String getName(@NotNull Language lang);

    public boolean isEditable() {
        return editable;
    }

    @Override
    public final @NotNull Namespace getNamespace() {
        return this.namespace;
    }

    public @NotNull String asString() {
        return this.namespace.asString();
    }

    @Override
    public final int hashCode() {
        return this.hash;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LandCategory)) {
            return false;
        } else {
            return this.namespace.equals(((LandCategory) o).namespace);
        }
    }

    @Override
    public @NotNull String toString() {
        return "LandCategory:{" + this.toConfigKey() + '}';
    }
}
