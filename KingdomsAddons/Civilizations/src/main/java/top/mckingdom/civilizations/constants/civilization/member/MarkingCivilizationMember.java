package top.mckingdom.civilizations.constants.civilization.member;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.base.KeyedKingdomsObject;
import org.kingdoms.data.database.dataprovider.SectionableDataSetter;
import org.kingdoms.data.handlers.DataHandlerMetadata;

import java.util.Objects;

@SuppressWarnings("unused")
public abstract sealed class MarkingCivilizationMember<T> extends KeyedKingdomsObject<T> permits CivilizationMember {

    @NotNull
    protected final T key;
    @NotNull
    protected final CivilizationMemberType<T, CivilizationMember<T>> type;

    public MarkingCivilizationMember(@NotNull T key, @NotNull CivilizationMemberType<T, CivilizationMember<T>> type) {
        this.key = key;
        this.type = type;
    }

    public final boolean isMarking() {
        return !(this instanceof CivilizationMember<T>);
    }

    @Override
    public @NonNull T getKey() {
        return this.key;
    }

    @NotNull
    public CivilizationMemberType<T, CivilizationMember<T>> getType() {
        return this.type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CivilizationMember<?> that)) return false;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    /**
     * It will serialize a section like:
     * {
     * "type": String;
     * "ident": {}
     * }
     */
    public void serialize(SectionableDataSetter section, boolean ignore) {
        section.get("type").setString(this.getType().getNamespace().asNormalizedString());
        this.serializeIndentData(section.get("ident"));

        DataHandlerMetadata.serializeMetadata(section, this);   //TODO metadata
    }

    /**
     * @param section A section for storing identification information of civilization member.
     */
    protected abstract void serializeIndentData(SectionableDataSetter section);   //比如Kingdom的key UUID
}
