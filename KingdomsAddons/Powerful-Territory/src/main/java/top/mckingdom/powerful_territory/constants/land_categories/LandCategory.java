package top.mckingdom.powerful_territory.constants.land_categories;

import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.constants.namespace.Namespaced;
import org.kingdoms.locale.Language;

public abstract class LandCategory implements Namespaced {
    private int hash;
    private final @NotNull Namespace namespace;
    private final boolean editable;

    public LandCategory(@NotNull Namespace namespace, boolean editable) {
        this.namespace = namespace;
        this.editable = editable;
    }

    @Override
    public @NotNull Namespace getNamespace() {
        return this.namespace;
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

    public @NotNull String asString() {
        return this.namespace.asString();
    }


    /**
     * 用于语言文件等场景
     *
     * @return 返回这个土地类型的关键字的小写英文名
     */
    public @NotNull String getConfigName() {
        return this.namespace.getKey().toLowerCase().replace('_', '-');
    }

    @Override
    public String toString() {
        return "LandCategory:{" + this.getConfigName() + '}';
    }

    public abstract @NotNull String getName(@NotNull Language lang);

    public boolean isEditable() {
        return editable;
    }
}
