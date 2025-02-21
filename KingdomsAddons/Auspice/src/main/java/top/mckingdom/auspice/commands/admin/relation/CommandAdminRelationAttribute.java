package top.mckingdom.auspice.commands.admin.relation;

import net.aurika.validate.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.commands.*;
import org.kingdoms.constants.group.Group;
import org.kingdoms.constants.group.Kingdom;
import org.kingdoms.constants.group.Nation;
import org.kingdoms.constants.group.model.relationships.KingdomRelation;
import org.kingdoms.constants.group.model.relationships.RelationAttribute;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.main.Kingdoms;
import top.mckingdom.auspice.configs.AuspiceLang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandAdminRelationAttribute extends KingdomsCommand {

    static List<String> relationsString = new ArrayList<>();

    static {
        Arrays.asList(KingdomRelation.values()).forEach(relation -> relationsString.add(relation.name()));
        relationsString.remove("SELF");
    }

    public CommandAdminRelationAttribute(@NotNull KingdomsParentCommand parent) {
        this(parent, PermissionDefault.OP);
    }

    public CommandAdminRelationAttribute(@NotNull KingdomsParentCommand parent, @NotNull PermissionDefault permissionDefault) {
        super("attribute", parent, permissionDefault);
    }

    @Override
    public @NotNull CommandResult execute(@NotNull CommandContext context) {
        Validate.Arg.notNull(context, "context");
        String[] args = context.getArgs();
        CommandSender messageReceiver = context.getMessageReceiver();

        String relationStr = args[1];

        if (!relationsString.contains(relationStr)) {  // Unknown relation
            AuspiceLang.COMMAND_ADMIN_RELATION_ATTRIBUTE_FAILED_SELF_RELATION.sendError(messageReceiver, "relation_str", relationStr);
            return CommandResult.FAILED;
        }
        KingdomRelation relation;
        try {
            relation = KingdomRelation.valueOf(relationStr);
        } catch (IllegalArgumentException ex) {
            return CommandResult.FAILED;
        }

        if (relation == KingdomRelation.SELF) {  // Edit self relation attributes
            AuspiceLang.COMMAND_ADMIN_RELATION_ATTRIBUTE_FAILED_SELF_RELATION.sendError(messageReceiver);
            return CommandResult.FAILED;
        }

        String attrStr = args[2];

        RelationAttribute attribute = Kingdoms.get().getRelationAttributeRegistry().getRegistered(Namespace.fromConfigString(attrStr));

        if (attribute == null) {
            System.out.println("The input value is not a relation attribute config name");
            return CommandResult.FAILED;
        }

        boolean setTrue = Boolean.parseBoolean(args[3]);

        String targetStr = args[0];

        if (targetStr.equals("#ALL_KINGDOM")) {  // All kingdoms
            Kingdoms.get().getDataCenter().getKingdomManager().getKingdoms().forEach((kingdom) -> {
                if (setTrue) {
                    kingdom.getAttributes().get(relation).add(attribute);
                } else {
                    kingdom.getGroup().getAttributes().get(relation).remove(attribute);
                }
            });
            AuspiceLang.COMMAND_ADMIN_RELATION_ATTRIBUTE_SUCCESS_ALL_KINGDOM.sendMessage(messageReceiver, "relation", relationStr, "value", setTrue);
            return CommandResult.SUCCESS;
        } else if (targetStr.equals("#ALL_NATION")) { // All nations
            Kingdoms.get().getDataCenter().getNationManager().getNations().forEach((nation) -> {
                if (setTrue) {
                    nation.getAttributes().get(relation).add(attribute);
                } else {
                    nation.getGroup().getAttributes().get(relation).remove(attribute);
                }
            });
            AuspiceLang.COMMAND_ADMIN_RELATION_ATTRIBUTE_SUCCESS_ALL_NATION.sendMessage(messageReceiver, "relation", relationStr, "value", setTrue);
            return CommandResult.SUCCESS;
        } else {
            Kingdom kingdom = context.getKingdom(0);
            Nation nation = context.getNation(0);

            Group group = kingdom == null ? nation : kingdom;

            if (group == null) {
                AuspiceLang.COMMAND_ADMIN_RELATION_ATTRIBUTE_FAILED_NOTFOUND_GROUP.sendError(messageReceiver, "name", targetStr);
                return CommandResult.FAILED;
            }

            if (setTrue) {
                group.getAttributes().get(relation).add(attribute);
            } else {
                group.getAttributes().get(relation).remove(attribute);
            }

            if (group instanceof Kingdom) {
                AuspiceLang.COMMAND_ADMIN_RELATION_ATTRIBUTE_SUCCESS_SINGLE_KINGDOM.sendMessage(messageReceiver, "group-name", targetStr, "relation", relationStr, "attribute", attrStr);
            } else {
                AuspiceLang.COMMAND_ADMIN_RELATION_ATTRIBUTE_SUCCESS_SINGLE_NATION.sendMessage(messageReceiver, "group-name", targetStr, "relation", relationStr, "attribute", attrStr);
            }

            return CommandResult.SUCCESS;
        }
    }

    @Override
    public @NonNull List<String> tabComplete(@NonNull CommandTabContext context) {
        Validate.Arg.notNull(context, "context");

        if (context.isAtArg(0)) {
            List<String> out0 = new ArrayList<>();
            out0.add("#ALL_KINGDOM");
            out0.add("#ALL_NATION");
            out0.addAll(context.getKingdoms(0));
            return out0;
        }

        if (context.isAtArg(1)) {
            return relationsString;
        }

        if (context.isAtArg(2)) {
            List<String> out2 = new ArrayList<>();
            Kingdoms.get().getRelationAttributeRegistry().getRegistry().keySet().forEach(namespace -> out2.add(namespace.getConfigOptionName()));
            return out2;
        }

        if (context.isAtArg(3)) {
            List<String> out3 = new ArrayList<>(2);
            out3.add(Boolean.TRUE.toString());
            out3.add(Boolean.FALSE.toString());
            return out3;
        }
        return Collections.emptyList();
    }
}
