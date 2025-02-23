package top.mckingdom.auspice.commands.admin.registry;

import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.commands.KingdomsParentCommand;
import org.kingdoms.constants.economy.Economy;
import org.kingdoms.constants.economy.EconomyRegistry;
import org.kingdoms.peacetreaties.PeaceTreatiesAddon;
import org.kingdoms.peacetreaties.terms.TermProvider;
import top.mckingdom.auspice.commands.admin.registry.operator.RegistryOperatorCommandUnregister;

public class CommandAdminRegistryEconomy extends CommandAdminRegistryTemplate<Economy, EconomyRegistry> {
    public CommandAdminRegistryEconomy(@Nullable KingdomsParentCommand parent) {
        this(parent, PermissionDefault.OP);
    }

    public CommandAdminRegistryEconomy(@Nullable KingdomsParentCommand parent, @NotNull PermissionDefault permissionDefault) {
        super("economy", parent, permissionDefault, EconomyRegistry.INSTANCE, Economy.class);
        new RegistryOperatorCommandUnregister<>("unregister", this, permissionDefault, PeaceTreatiesAddon.get().getTermRegistry(), TermProvider.class);
    }
}
