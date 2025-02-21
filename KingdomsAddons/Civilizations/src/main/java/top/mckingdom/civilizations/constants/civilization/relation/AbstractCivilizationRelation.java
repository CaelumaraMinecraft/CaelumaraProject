package top.mckingdom.civilizations.constants.civilization.relation;

import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.namespace.Namespace;

import java.util.Objects;

public abstract class AbstractCivilizationRelation implements CivilizationRelation {
    private final Namespace namespace;

    public AbstractCivilizationRelation(@NotNull Namespace namespace) {
        Objects.requireNonNull(namespace, "namespace");
        this.namespace = namespace;
    }

    @Override
    public @NotNull Namespace getNamespace() {
        return this.namespace;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CivilizationRelation relation)) return false;
        return Objects.equals(namespace, relation.getNamespace());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(this.namespace);
    }
}
