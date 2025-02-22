package top.mckingdom.auspice.commands.admin.registry;

import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.commands.KingdomsParentCommand;
import org.kingdoms.peacetreaties.PeaceTreatiesAddon;
import org.kingdoms.peacetreaties.terms.TermProvider;
import org.kingdoms.peacetreaties.terms.TermRegistry;
import top.mckingdom.auspice.commands.admin.registry.operator.RegistryOperatorCommandList;
import top.mckingdom.auspice.commands.admin.registry.operator.RegistryOperatorCommandUnregister;

import java.util.Map;

public class CommandAdminRegistryTerm extends KingdomsParentCommand {
    public CommandAdminRegistryTerm(@Nullable KingdomsParentCommand parent) {
        this(parent, PermissionDefault.OP);
    }

    public CommandAdminRegistryTerm(@Nullable KingdomsParentCommand parent, @NotNull PermissionDefault permissionDefault) {
        super("term", parent, permissionDefault);
        new RegistryOperatorCommandUnregister<>("unregister", this, permissionDefault, PeaceTreatiesAddon.get().getTermRegistry(), TermProvider.class);
        new RegistryOperatorCommandList<>("list", this, permissionDefault, PeaceTreatiesAddon.get().getTermRegistry(), TermProvider.class) {

            @Override
            protected @NotNull Map<String, ? extends RegistryOperatorCommandList<TermProvider, TermRegistry>.ListOperation> availableOperations() {
                return Map.of(
                        "hashcode", new HashCode(),
                        "key-full-string", new KeyToFullString()
                );
            }
        };
    }
}
