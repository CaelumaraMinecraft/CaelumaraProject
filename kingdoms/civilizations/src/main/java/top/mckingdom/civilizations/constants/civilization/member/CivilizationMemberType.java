package top.mckingdom.civilizations.constants.civilization.member;

import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.constants.namespace.Namespaced;
import org.kingdoms.data.database.dataprovider.SectionableDataGetter;
import org.kingdoms.data.handlers.DataHandlerMetadata;

public abstract class CivilizationMemberType<T, M extends CivilizationMember<T>> implements Namespaced {
  private final Namespace namespace;
  private final Class<T> type;

  protected CivilizationMemberType(Namespace namespace, Class<T> type) {
    this.namespace = namespace;
    this.type = type;
  }

  public final MarkingCivilizationMember<T> deserializeMarkingMember(SectionableDataGetter section) {
    return this.deserializeFromIdentData(section.get("ident"));
  }

  public final CivilizationMember<T> deserializeIntactMember(SectionableDataGetter section) {
    CivilizationMember<T> member = (CivilizationMember<T>) this.deserializeMarkingMember(section);
    SectionableDataGetter subordinatesSection;
    try {
      subordinatesSection = section.get("subordinates");
    } catch (Exception e) {
      throw new IllegalStateException("The stored data should include section 'subordinates'.", e);
    }
    subordinatesSection.asCollection(member.getSubordinates(), (membersSet, getter) -> {
      CivilizationMemberTypeRegistry.deserializeIntactMember(getter).changeSuperior(member);
    });
    DataHandlerMetadata.deserializeMetadata(section, member);
    return member;
  }

  protected abstract M deserializeFromIdentData(SectionableDataGetter section);

  public abstract M of0(T object);

  @Override
  public final Namespace getNamespace() {
    return namespace;
  }

  public Class<T> getType() {
    return type;
  }

  public static <T extends Object, M extends CivilizationMember<T>> M of(T o) {
    for (CivilizationMemberType<?, ?> type1 : CivilizationMemberTypeRegistry.get().getRegistry().values()) {
      if (type1.getType() == o.getClass()) {
        return ((CivilizationMemberType<T, M>) type1).of0(o);
      }
    }
    return null;
  }

}
