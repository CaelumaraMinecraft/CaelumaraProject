package top.mckingdom.civilizations.constants.land.structures.objects;

import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.land.abstraction.data.DeserializationContext;
import org.kingdoms.constants.land.abstraction.data.SerializationContext;
import org.kingdoms.constants.land.structures.Structure;
import org.kingdoms.constants.land.structures.StructureStyle;
import org.kingdoms.data.database.dataprovider.SectionableDataGetter;
import org.kingdoms.data.database.dataprovider.SectionableDataSetter;
import top.mckingdom.civilizations.constants.civilization.Civilization;
import top.mckingdom.civilizations.constants.civilization.member.CivilizationMember;
import top.mckingdom.civilizations.constants.civilization.member.CivilizationMemberTypeRegistry;
import top.mckingdom.civilizations.constants.civilization.member.MarkingCivilizationMember;

public abstract class CivilizationLinker extends Structure {
  @NotNull
  private Civilization civilization;
  @NotNull
  private MarkingCivilizationMember<?> owner;

  public CivilizationLinker(String world, int x, int y, int z, StructureStyle style, @NotNull Civilization civilization, @NotNull CivilizationMember<?> owner) {
    super(world, x, y, z, style);
    this.civilization = civilization;
    this.owner = owner;
  }

  @NotNull
  public Civilization getCivilization() {
    return civilization;
  }

  @Override
  public void serialize(SerializationContext<SectionableDataSetter> context) {
    super.serialize(context);
    SectionableDataSetter dataSetter = context.getDataProvider();

    dataSetter.get("civilization").setUUID(this.civilization.getKey());

    SectionableDataSetter section = dataSetter.get("owner");
    this.owner.serialize(section, false);
  }

  //"civilization": UUID
  //"owner": {
  //    "type": String
  //    "ident": {}
  // }
  //

  @Override
  public void deserialize(DeserializationContext<SectionableDataGetter> context) {
    super.deserialize(context);
    SectionableDataGetter var2 = context.getDataProvider();
    this.civilization = Civilization.getCivilization(var2.get("civilization").asUUID());
    SectionableDataGetter ownerSec = var2.get("owner");
    this.owner = CivilizationMemberTypeRegistry.deserializeMarkingMember(ownerSec);
  }

  public void setCivilization(@NotNull Civilization civilization) {
    this.civilization = civilization;
  }

  @NotNull
  public MarkingCivilizationMember<?> getOwner() {
    return owner;
  }

  public void setOwner(@NotNull CivilizationMember<?> owner) {
    this.owner = owner;
  }

}
