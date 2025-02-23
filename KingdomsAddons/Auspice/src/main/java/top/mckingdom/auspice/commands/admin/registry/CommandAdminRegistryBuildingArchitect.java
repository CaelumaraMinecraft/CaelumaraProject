package top.mckingdom.auspice.commands.admin.registry;

import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.commands.KingdomsParentCommand;
import org.kingdoms.constants.land.building.BuildingArchitect;
import org.kingdoms.constants.land.building.BuildingArchitectRegistry;
import org.kingdoms.main.Kingdoms;
import org.kingdoms.peacetreaties.PeaceTreatiesAddon;
import org.kingdoms.peacetreaties.terms.TermProvider;
import top.mckingdom.auspice.commands.admin.registry.operator.RegistryOperatorCommandUnregister;

public class CommandAdminRegistryBuildingArchitect extends CommandAdminRegistryTemplate<BuildingArchitect, BuildingArchitectRegistry> {
    public CommandAdminRegistryBuildingArchitect(@Nullable KingdomsParentCommand parent) {
        this(parent, PermissionDefault.OP);
    }

    public CommandAdminRegistryBuildingArchitect(@Nullable KingdomsParentCommand parent, @NotNull PermissionDefault permissionDefault) {
        super("building" + "architect", parent, permissionDefault, Kingdoms.get().getBuildingArchitectRegistry(), BuildingArchitect.class);
        new RegistryOperatorCommandUnregister<>("unregister", this, permissionDefault, PeaceTreatiesAddon.get().getTermRegistry(), TermProvider.class);
    }
}
