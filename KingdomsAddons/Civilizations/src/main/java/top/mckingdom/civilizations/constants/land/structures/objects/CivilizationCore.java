package top.mckingdom.civilizations.constants.land.structures.objects;

import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.land.structures.StructureStyle;
import top.mckingdom.civilizations.constants.civilization.Civilization;
import top.mckingdom.civilizations.constants.civilization.member.CivilizationMember;

public class CivilizationCore extends CivilizationLinker {
    public CivilizationCore(String world, int x, int y, int z, StructureStyle style, @NotNull Civilization civilization, @NotNull CivilizationMember<?> owner) {
        super(world, x, y, z, style, civilization, owner);
    }
}
