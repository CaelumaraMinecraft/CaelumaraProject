package top.mckingdom.civilizations.constants.civilization.relation;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.constants.namespace.Namespaced;

import java.util.Objects;

public abstract class CivilizationRelation implements Namespaced {
    private final Namespace namespace;

    public CivilizationRelation(Namespace namespace) {
        this.namespace = namespace;
    }

    @Override
    public @NonNull Namespace getNamespace() {
        return this.namespace;
    }

    public abstract boolean isCustomizeAttributes();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CivilizationRelation relation = (CivilizationRelation) o;
        return Objects.equals(namespace, relation.namespace);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(namespace);
    }
}
