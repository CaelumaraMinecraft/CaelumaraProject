package top.mckingdom.civilizations.constants.civilization.member;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.data.database.dataprovider.SectionableDataSetter;
import org.kingdoms.data.handlers.DataHandlerMetadata;
import org.kingdoms.locale.placeholders.context.MessagePlaceholderProvider;
import top.mckingdom.civilizations.constants.civilization.permission.CivilizationPermission;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
public abstract non-sealed class CivilizationMember<T> extends MarkingCivilizationMember<T> {

    @Nullable
    protected CivilizationMember<?> superior;
    @NotNull
    protected final Set<CivilizationMember<?>> subordinates;
    @NotNull
    protected final Set<CivilizationPermission> permissions;

    public CivilizationMember(T key, CivilizationMemberType<T, CivilizationMember<T>> type) {
        this(key, type, null, new HashSet<>(), new HashSet<>());
    }

    public CivilizationMember(T key, CivilizationMemberType<T, CivilizationMember<T>> type, Set<CivilizationPermission> permissions) {
        this(key, type, null, new HashSet<>(), permissions);
    }

    public CivilizationMember(T key,
                              CivilizationMemberType<T, CivilizationMember<T>> type,
                              @Nullable CivilizationMember<?> superior,
                              @NotNull Set<CivilizationMember<?>> subordinates,
                              @NotNull Set<CivilizationPermission> permissions) {
        super(key, type);
        this.superior = superior;
        this.subordinates = subordinates;
        this.permissions = permissions;
    }

    public void addMessageContextEdits(@NotNull MessagePlaceholderProvider provider) {
        super.addMessageContextEdits(provider);
        provider.setPrimaryTarget(this.key);
        provider.raw("type", this.type).raw("type_key", this.key);                  //TODO
    }

    @OverridingMethodsMustInvokeSuper
    public void serialize(SectionableDataSetter section, boolean serializeIntactData) {
        super.serialize(section, serializeIntactData);
        if (serializeIntactData) {
            section.get("subordinates").setCollection(this.subordinates, (subSection, sub) -> {
                sub.serialize(subSection.createSection(), true);
            });
            DataHandlerMetadata.serializeMetadata(section, this);
        }
    }

    public boolean isTop() {
        return this.superior == null;
    }

    public void changeSuperior(@Nullable CivilizationMember<?> newSuperior) {
        if (this.superior != null) {
            this.superior.subordinates.remove(this);
        }

        if (newSuperior != null) {
            newSuperior.subordinates.add(this);
        }

        this.superior = newSuperior;
    }

    public CivilizationMember<?> findSuperior(MarkingCivilizationMember<?> target) {
        if (this.superior != null) {
            if (this.superior.equals(target)) {
                return this.superior;
            } else {
                return this.superior.findSuperior(target);
            }
        }
        return null;
    }

    public CivilizationMember<?> findSuperior(MarkingCivilizationMember<?> target, int traverseDepth) {
        if (this.superior != null && traverseDepth > 0) {
            if (this.superior.equals(target)) {
                return this.superior;
            } else {
                return this.superior.findSuperior(target, traverseDepth - 1);
            }
        }
        return null;
    }

    /**
     * Add a subordinate, it will update this subordinate's superior.
     */
    public void addSubordinate(CivilizationMember<?> subordinate) {
        subordinate.setSuperior(this);
        this.subordinates.add(subordinate);
    }

    public CivilizationMember<?> findSubordinate(MarkingCivilizationMember<?> target) {
        for (CivilizationMember<?> m : this.subordinates) {
            if (m.equals(target)) {
                return m;
            } else {
                return m.findSubordinate(target);
            }
        }
        return null;
    }

    public CivilizationMember<?> findSubordinate(MarkingCivilizationMember<?> target, int traverseDepth) {
        if (traverseDepth > 0) {
            for (CivilizationMember<?> m : this.subordinates) {
                if (m.equals(target)) {
                    return m;
                } else {
                    return m.findSubordinate(target, traverseDepth - 1);
                }
            }
        }
        return null;
    }

    @Nullable
    public CivilizationMember<?> find(MarkingCivilizationMember<?>[] path) {
        return find(path, 0);
    }

    @Nullable
    public CivilizationMember<?> find(MarkingCivilizationMember<?>[] path, int index) {
        if (index < path.length) { //index: 1, length: 2
            if (path[index].equals(this)) {
                return this;
            } else {
                return find(path, index + 1);
            }
        }
        return null;
    }

    @Nullable
    public CivilizationMember<?> getSuperior() {
        return superior;
    }

    public void setSuperior(@Nullable CivilizationMember<?> superior) {
        this.superior = superior;
    }

    @NotNull
    public Set<CivilizationMember<?>> getSubordinates() {
        return this.subordinates;
    }

    @NotNull
    public Set<CivilizationPermission> getPermissions() {
        return this.permissions;
    }
}
