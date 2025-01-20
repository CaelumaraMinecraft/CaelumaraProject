package top.mckingdom.civilizations.constants.civilization.permission;

import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.constants.namespace.Namespaced;

public class CivilizationPermission implements Namespaced {
    private int hash;
    private final Namespace namespace;
    private final boolean canBeInherited;
    private final Scope scope;

    public CivilizationPermission(Namespace namespace, boolean canBeInherited, Scope scope) {
        this.namespace = namespace;
        this.canBeInherited = canBeInherited;
        this.scope = scope;
    }

    @Override
    public Namespace getNamespace() {
        return this.namespace;
    }

    protected void setHash(int hash) {
        this.hash = hash;
    }

    public final int hashCode() {
        return this.hash;
    }

    public boolean equals(Object o) {
        if (!(o instanceof CivilizationPermission)) {
            return false;
        } else {
            return this.hash == o.hashCode();
        }
    }


    @Override
    public String toString() {
        return "CivilizationPermission{" +
                "namespace=" + namespace +
                ", canBeInherited=" + canBeInherited +
                ", scope=" + scope +
                '}';
    }

    public boolean isCanBeInherited() {
        return canBeInherited;
    }

    public Scope getScope() {
        return scope;
    }


    /**
     * 决定了一个文明成员在将这个权限设置为true的时候，要经过哪些成员的同意
     * <p>
     * CIVILIZATION:
     * 这个文明成员所处的文明都会受到这个权限的作用。因此，这个权限应该由全文明的根成员同意，或者从其上级获取
     * <p>
     * ROOT_MEMBER:
     * 该成员的最高上级的所有下级，都会受到这个权限的作用。因此这个权限应该由这个成员的最高上级同意，或从其上级获取
     * <p>
     * SUBORDINATE:
     * 该成员的所有下属及该成员自己，都会受到这个权限的作用，因此这个权限可以由这个成员的上级进行设置。
     *
     *
     */
    public enum Scope {

        CIVILIZATION,
        ROOT_MEMBER,
        SUBORDINATES,

    }

}
