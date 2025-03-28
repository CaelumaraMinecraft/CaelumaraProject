package top.mckingdom.auspice.commands.admin.registry;

import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.commands.KingdomsParentCommand;
import org.kingdoms.peacetreaties.PeaceTreatiesAddon;
import org.kingdoms.peacetreaties.terms.TermProvider;
import org.kingdoms.peacetreaties.terms.TermRegistry;

public class CommandAdminRegistryTerm extends CommandAdminRegistryTemplate<TermProvider, TermRegistry> {
  public CommandAdminRegistryTerm(@Nullable KingdomsParentCommand parent) {
    this(parent, PermissionDefault.OP);
  }

  public CommandAdminRegistryTerm(@Nullable KingdomsParentCommand parent, @NotNull PermissionDefault permissionDefault) {
    super("term", parent, permissionDefault, PeaceTreatiesAddon.get().getTermRegistry(), TermProvider.class);
  }

}
