package top.mckingdom.auspice.commands.admin.relation_attribute;

import org.bukkit.permissions.PermissionDefault;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.commands.*;
import org.kingdoms.constants.group.Kingdom;
import org.kingdoms.constants.group.model.relationships.KingdomRelation;
import org.kingdoms.constants.group.model.relationships.RelationAttribute;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.main.Kingdoms;

import java.util.*;

public class CommandAdminRelationAttribute extends KingdomsCommand {


    static List<String> relationsString = new ArrayList<>();

    static {
        Arrays.asList(KingdomRelation.values()).forEach(relation -> relationsString.add(relation.name()));
        relationsString.remove("SELF");
    }


    //输入参数:    CommandAdmin.getInstance()
    public CommandAdminRelationAttribute(KingdomsParentCommand parent) {
        super("relationAttribute", parent, PermissionDefault.OP);
    }




    @Override
    public @NotNull CommandResult execute(@NonNull CommandContext context) {
        String[] args = context.getArgs();


        String relationStr = args[1];

        if (!relationsString.contains(relationStr)) {
            System.out.println("Unknown relation: " + relationStr);
            return CommandResult.FAILED;
        }

        KingdomRelation relation = KingdomRelation.valueOf(relationStr);


        if (relation == KingdomRelation.SELF) {
            System.out.println("Can't edit attributes of SELF relation, because the SELF relation always have the all attributes.");
            return CommandResult.FAILED;
        }

        String attrStr = args[2];

        RelationAttribute attribute = Kingdoms.get().getRelationAttributeRegistry().getRegistered(Namespace.fromConfigString(attrStr));

        if (attribute == null) {
            System.out.println("The input value is not a relation attribute config name");
            return CommandResult.FAILED;
        }

        boolean setTrue = Boolean.parseBoolean(args[3]);            //Is set to true or false?

        String targetStr = args[0];

        if (targetStr.equals("#ALL")) {                //All kingdoms
            Kingdoms.get().getDataCenter().getKingdomManager().getKingdoms().forEach(kingdom -> {
                if (setTrue) {
                    kingdom.getGroup().getAttributes().get(relation).add(attribute);
                } else {
                    kingdom.getGroup().getAttributes().get(relation).remove(attribute);
                }
            });
            System.out.println("Successfully to set the relation attribute for all kingdoms: " + "at relation: " + relation + "at attribute: " + attribute + "to: " + setTrue);
            return CommandResult.SUCCESS;
        }

        Kingdom kingdom = Kingdom.getKingdom(targetStr);

        if (kingdom == null) {
            System.out.println("The input value is not a kingdom name");            //The input value is not a kingdom name
            return CommandResult.FAILED;
        }

        if (setTrue) {
            kingdom.getGroup().getAttributes().get(relation).add(attribute);
        } else {
            kingdom.getGroup().getAttributes().get(relation).remove(attribute);
        }

        System.out.println("Successfully to set the relation attribute for kingdom: " + kingdom + "at relation: " + relation + "at attribute: " + attribute + "to: " + setTrue);
        return CommandResult.SUCCESS;


    }

    @Override
    public @NonNull List<String> tabComplete(@NonNull CommandTabContext context) {

        if (context.isAtArg(0)) {
            List<String> out0 = new ArrayList<>();
            out0.add("#ALL");
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
            List<String> out3 = new ArrayList<>();
            out3.add(0, "true");
            out3.add(1, "false");
            return out3;
        }
        return emptyTab();
    }
}
