package top.mckingdom.auspice.configs;

import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.group.model.relationships.KingdomRelation;
import org.kingdoms.locale.LanguageEntry;
import org.kingdoms.locale.messenger.DefinedMessenger;

import static top.mckingdom.auspice.commands.admin.registry.operator.RegistryOperatorCommandForeach.VAR_OP;
import static top.mckingdom.auspice.commands.admin.registry.operator.RegistryOperatorCommandList.VAR_LIST_ENTRY;
import static top.mckingdom.auspice.commands.admin.registry.operator.RegistryOperatorCommandUnregister.VAR_KEY;
import static top.mckingdom.auspice.configs.MsgConst.*;

public enum AuspiceLang implements DefinedMessenger {

    COMMAND_ADMIN_RELATION_ATTRIBUTE_FAILED_NOTFOUND_RELATION(E_COLOR + "Can not find relation attribute %relation_str%", 1, 2, 3, 4, 5),
    /**
     * When try to edit attributes for {@linkplain KingdomRelation#SELF} relation.
     */
    COMMAND_ADMIN_RELATION_ATTRIBUTE_FAILED_SELF_RELATION(E_COLOR + "Can not edit relation attribute for self relation", 1, 2, 3, 4, 5),
    COMMAND_ADMIN_RELATION_ATTRIBUTE_FAILED_NOTFOUND_GROUP(E_COLOR + "Can not found group for: %name%", 1, 2, 3, 4, 5),

    COMMAND_ADMIN_RELATION_ATTRIBUTE_SUCCESS_SINGLE_KINGDOM(P_COLOR + "Successfully set the relation attribute for kingdom %group-name% for relation: %relation% , attribute %attribute% to %value%", 1, 2, 3, 4, 5),
    COMMAND_ADMIN_RELATION_ATTRIBUTE_SUCCESS_SINGLE_NATION(P_COLOR + "Successfully set the relation attribute for nation %group-name% for relation: %relation% , attribute %attribute% to %value%", 1, 2, 3, 4, 5),
    @Deprecated
    COMMAND_ADMIN_RELATION_ATTRIBUTE_SUCCESS_ALL_GROUP(P_COLOR + "Successfully set the relation attribute for all groups for relation: %relation% , attribute %attribute% to %value%", 1, 2, 3, 4, 5),
    COMMAND_ADMIN_RELATION_ATTRIBUTE_SUCCESS_ALL_KINGDOM(P_COLOR + "Successfully set the relation attribute for all kingdoms for relation: %relation% , attribute %attribute% to %value%", 1, 2, 3, 4, 5),
    COMMAND_ADMIN_RELATION_ATTRIBUTE_SUCCESS_ALL_NATION(P_COLOR + "Successfully set the relation attribute for all nations for relation: %relation% , attribute %attribute% to %value%", 1, 2, 3, 4, 5),

    COMMAND_ADMIN_REGISTRY_LOCK_FAILED_NOT_LOCKABLE(E_COLOR + "The registry is not lockable.", 1, 2, 3, 4, 5),
    COMMAND_ADMIN_REGISTRY_LOCK_FAILED_EXCEPTION(E_COLOR + "Error when lock the registry.", 1, 2, 3, 4, 5),
    COMMAND_ADMIN_REGISTRY_LOCK_SUCCESS(P_COLOR + "Successful lock the registry.", 1, 2, 3, 4),

    COMMAND_ADMIN_REGISTRY_UNREGISTER_FAILED_NOT_UNREGISTERABLE(E_COLOR + "Registry cannot unregister.", 1, 2, 3, 4, 5),
    COMMAND_ADMIN_REGISTRY_UNREGISTER_FAILED_NO_KEY(E_COLOR + "Cannot find key: '" + VAR_KEY + "' in this registry.", 1, 2, 3, 4, 5),
    COMMAND_ADMIN_REGISTRY_UNREGISTER_FAILED_WRONG_KEY(E_COLOR + "Wrong key: '" + VAR_KEY + "'.", 1, 2, 3, 4, 5),
    COMMAND_ADMIN_REGISTRY_UNREGISTER_SUCCESS(P_COLOR + "Success unregister entry: '" + VAR_KEY + "' in this registry.", 1, 2, 3, 4),

    COMMAND_ADMIN_REGISTRY_FOREACH_FAILED_NOOP(E_COLOR + "Lose registry operator.", 1, 2, 3, 4, 5),
    COMMAND_ADMIN_REGISTRY_FOREACH_FAILED_UNKNOWN_OP(E_COLOR + "Unknown registry operator: %" + VAR_OP + "%.", 1, 2, 3, 4, 5),

    COMMAND_ADMIN_REGISTRY_LIST_SUCCESS_HEAD("[", 1, 2, 3, 4, 5),
    COMMAND_ADMIN_REGISTRY_LIST_SUCCESS_ENTRY("  %" + VAR_LIST_ENTRY + "%,", 1, 2, 3, 4, 5),
    COMMAND_ADMIN_REGISTRY_LIST_SUCCESS_END("]", 1, 2, 3, 4, 5),

    COMMAND_TRANSFER_MEMBER_DESCRIPTION(S_COLOR + "Transfer members of your kingdom to another kingdom.", 1, 3),
    COMMAND_TRANSFER_MEMBER_USAGE(USAGE + "transferMember <YourKingdomMember> <Kingdom>", 1, 3),
    COMMAND_TRANSFER_MEMBER_FAILED_OTHER_KINGDOM(E_COLOR + "You cannot transfer members of other kingdom.", 1, 3, 4),
    COMMAND_TRANSFER_MEMBER_FAILED_OTHER_NATION(E_COLOR + "You cannot transfer members of other nation.", 1, 3, 4),
    COMMAND_TRANSFER_MEMBER_FAILED_RANK_PRIORITY(E_COLOR + "You cannot transfer members with higher rank priority than yourself.", 1, 3, 4),
    COMMAND_TRANSFER_MEMBER_REQUEST_SUCCESS(P_COLOR + "Successfully sent a request to transfer members to the other kingdom.", 1, 3),
    COMMAND_TRANSFER_MEMBER_DIRECT_SUCCESS(P_COLOR + "Successfully transferred member %player% to the other kingdom %kingdom%.", 1, 3),

    LANDS_ELYTRA_PROTECTION(E_COLOR + "You can't fly with elytra in this kingdom's constants", 1),

    SENDER_ONLY_CONSOLE(E_COLOR + "This command can only be executed from the console."),
    SENDER_ONLY_PLAYER(E_COLOR + "This command can only be executed from a player.");

    private final @NotNull LanguageEntry entry;
    private final @NotNull String defaultValue;

    AuspiceLang(@NotNull String defaultValue, int... grouped) {
        this.defaultValue = defaultValue;
        this.entry = DefinedMessenger.getEntry(null, this, grouped);
    }

    @Override
    public @NotNull LanguageEntry getLanguageEntry() {
        return entry;
    }

    @Override
    public @NotNull String getDefaultValue() {
        return this.defaultValue;
    }
}
