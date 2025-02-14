package top.mckingdom.auspice.configs;

import org.kingdoms.config.annotations.Comment;
import org.kingdoms.locale.LanguageEntry;
import org.kingdoms.locale.messenger.DefinedMessenger;

public enum AuspiceLang implements DefinedMessenger {
    COMMAND_ADMIN_DOMAIN_NAME("constants", 1, 2, 3),
    COMMAND_ADMIN_DOMAIN_ALIASES("terra auspiceLand", 1, 2, 3),
    COMMAND_ADMIN_DOMAIN_DESCRIPTION("{$s}Manage anything related to constants from auspice addon.", 1, 2, 3),


    COMMAND_ADMIN_DOMAIN_CATEGORY_NAME("category", 1, 2, 3, 4),
    COMMAND_ADMIN_DOMAIN_CATEGORY_DESCRIPTION("{$s}Manage constants categories.", 1, 2, 3, 4),

    COMMAND_ADMIN_DOMAIN_CATEGORY_GET_SUCCESS("{$p}The constants category of constants %location% is %category%.", 1, 2, 3, 4, 5),
    COMMAND_ADMIN_DOMAIN_CATEGORY_GET_FAILED_NOT_CLAIMED("{$e}The constants is not claimed, it don't have category.", 1, 2, 3, 4, 5, 6),
    COMMAND_ADMIN_DOMAIN_CATEGORY_SET_SUCCESS("{$p}The constants category of constants %location% has been changed from %old-category% to %new-category%.", 1, 2, 3, 4, 5),
    COMMAND_ADMIN_DOMAIN_CATEGORY_SET_FAILED_NOT_CLAIMED("{$e}The constants is not claimed, you can't set category of this constants.", 1, 2, 3, 4, 5, 6),


    COMMAND_ADMIN_DOMAIN_CONTRACTION_NAME("contraction", 1, 2, 3, 4),
    COMMAND_ADMIN_DOMAIN_CONTRACTION_DESCRIPTION("{$s}Manage constants categories.", 1, 2, 3, 4),

    COMMAND_ADMIN_DOMAIN_CONTRACTION_GET_SUCCESS_HEAD("{$sep}&l-------=( {$sep}%kingdom% {$p}Land contractions {$sep})=-------", 1, 2, 3, 4, 5, 6),
    COMMAND_ADMIN_DOMAIN_CONTRACTION_GET_SUCCESS_BODY(
            "  {$s}%contraction%:" +
            "\n    &7%players%",
            1, 2, 3, 4, 5, 6
    ),
    COMMAND_ADMIN_DOMAIN_CONTRACTION_GET_SUCCESS_END("{$p}&l---------------------------------------------------", 1, 2, 3, 4, 5, 6),
    COMMAND_ADMIN_DOMAIN_CONTRACTION_GET_FAILED_NOT_CLAIMED("{$e}The constants is not claimed, it don't have contractions.", 1, 2, 3, 4, 5, 6),
    COMMAND_ADMIN_DOMAIN_CONTRACTION_SET_SUCCESS("{$p}The constants contractions of constants %location% has been changed from %old-contraction% to %new-contraction%.", 1, 2, 3, 4, 5),
    COMMAND_ADMIN_DOMAIN_CONTRACTION_SET_FAILED_NOT_CLAIMED("{$e}The constants is not claimed, you can't allocate contractions of this constants.", 1, 2, 3, 4, 5, 6),




    COMMAND_DOMAIN_NAME("constants", 1, 2),
    COMMAND_DOMAIN_ALIASES("terra landControl", 1, 2),
    COMMAND_DOMAIN_DESCRIPTION("{$s}Control the anything of a constants.", 1, 2),


    COMMAND_DOMAIN_CATEGORY_NAME("category", 1, 2, 3),
    COMMAND_DOMAIN_CATEGORY_ALIASES("category", 1, 2, 3),
    COMMAND_DOMAIN_CATEGORY_DESCRIPTION("{$s}Manage constants categories.", 1, 2, 3),

    COMMAND_DOMAIN_CATEGORY_GET_ALIASES("get see", 1, 2, 3, 4),
    COMMAND_DOMAIN_CATEGORY_GET_DESCRIPTION("{$s}Get the constants category of the constants you stand on.", 1, 2, 3, 4),
    COMMAND_DOMAIN_CATEGORY_GET_SUCCESS("{$p}The constants category of constants {$s}%location% {$p}is {$s}%category%.", 1, 2, 3, 4),
    COMMAND_DOMAIN_CATEGORY_GET_FAILED_OTHER_KINGDOM("{$e}You can't see the category of other kingdom's constants.", 1, 2, 3, 4, 5),
    COMMAND_DOMAIN_CATEGORY_GET_FAILED_NOT_CLAIMED("{$e}You can't see the category in not claimed constants.", 1, 2, 3, 4, 5),

    COMMAND_DOMAIN_CATEGORY_SET_USAGE("constants category change <newCategory>", 1, 2, 3, 4),
    COMMAND_DOMAIN_CATEGORY_SET_ALIASES("change", 1, 2, 3, 4),
    COMMAND_DOMAIN_CATEGORY_SET_DESCRIPTION("{$s}Change the constants category of the constants you stand on.", 1, 2, 3, 4),
    COMMAND_DOMAIN_CATEGORY_SET_SUCCESS("{$p}Successfully set the constants category to {$s}%new-category%.", 1, 2, 3, 4),
    COMMAND_DOMAIN_CATEGORY_SET_FAILED_OTHER_KINGDOM("{$e}Cat not change other kingdom's constants category.", 1, 2, 3, 4, 5),
    COMMAND_DOMAIN_CATEGORY_SET_FAILED_NOT_CLAIMED("{$e}Cat not change constants category in not claimed constants.", 1, 2, 3, 4, 5),


    COMMAND_DOMAIN_CONTRACTION_NAME("contraction", 1, 2, 3),
    COMMAND_DOMAIN_CONTRACTION_DESCRIPTION("{$s}Manage constants contractions.", 1, 2, 3),
    COMMAND_DOMAIN_CONTRACTION_GET_ALIASES("see", 1, 2, 3, 4),
    COMMAND_DOMAIN_CONTRACTION_GET_DESCRIPTION("{$s}Get all the contractions of the constants you stand on.", 1, 2, 3, 4),
    @Comment("Available placeholders: %kingdom%")
    COMMAND_DOMAIN_CONTRACTION_GET_SUCCESS_HEAD("{$sep}-------=( {$p}Land contractions {$sep})=-------", 1, 2, 3, 4, 5),
    COMMAND_DOMAIN_CONTRACTION_GET_SUCCESS_BODY(
            "  {$s}%contraction%: " +
            "\n    &7%players%",
            1, 2, 3, 4, 5
    ),
    COMMAND_DOMAIN_CONTRACTION_GET_SUCCESS_END("{$p}+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+", 1, 2, 3, 4, 5),
    COMMAND_DOMAIN_CONTRACTION_GET_FAILED_NOT_CLAIMED("{$e}This constants is not claimed!", 1, 2, 3, 4, 5),
    COMMAND_DOMAIN_CONTRACTION_GET_FAILED_OTHER_KINGDOM("{$e}You can not see the contractions in other kingdom's constants!", 1, 2, 3, 4, 5),

    COMMAND_DOMAIN_CONTRACTION_SET_ALIASES("allocate", 1, 2, 3, 4),
    COMMAND_DOMAIN_CONTRACTION_SET_DESCRIPTION("{$s}Allocation the contraction to a player.", 1, 2, 3, 4),
    COMMAND_DOMAIN_CONTRACTION_SET_USAGE("{$usage}landContraction allocation <contraction> <player>", 1, 2, 3, 4),
    COMMAND_DOMAIN_CONTRACTION_SET_SUCCESS("{$p}Success to allocation contraction {$s}%contraction% {$p}for player {$s}%player%.", 1, 2, 3, 4),
    COMMAND_DOMAIN_CONTRACTION_SET_FAILED_ALREADY_SET("{$e}Already allocation contraction {$p}%contraction% {$e}for player {$p}%player%{$e}!", 1, 2, 3, 4),
    COMMAND_DOMAIN_CONTRACTION_SET_FAILED_OTHER_KINGDOM("{$e}You can not allocation contractions for other kingdom's constants!", 1, 2, 3, 4),
    COMMAND_DOMAIN_CONTRACTION_SET_FAILED_NOT_CLAIMED("{$e}This constants isn't claimed!", 1, 2, 3, 4),



    COMMAND_TRANSFER_MEMBER_DESCRIPTION("{$s}Transfer members of your kingdom to another kingdom.", 1, 3),
    COMMAND_TRANSFER_MEMBER_USAGE("{$usage}transferMember <YourKingdomMember> <Kingdom>", 1, 3),
    COMMAND_TRANSFER_MEMBER_FAILED_OTHER_KINGDOM("{$e}You cannot transfer members of other kingdom.", 1, 3, 4),
    COMMAND_TRANSFER_MEMBER_FAILED_OTHER_NATION("{$e}You cannot transfer members of other nation.", 1, 3, 4),
    COMMAND_TRANSFER_MEMBER_FAILED_RANK_PRIORITY("{$e}You cannot transfer members with higher rank priority than yourself.", 1, 3, 4),
    COMMAND_TRANSFER_MEMBER_REQUEST_SUCCESS("{$s}Successfully sent a request to transfer members to the other kingdom.", 1, 3),
    COMMAND_TRANSFER_MEMBER_DIRECT_SUCCESS("{$s}Successfully transferred member %player% to the other kingdom %kingdom%.", 1, 3),

    LANDS_ELYTRA_PROTECTION("{$e}You can't fly with elytra in this kingdom's constants", 1),


    SENDER_ONLY_CONSOLE("{$e}This command can only be executed from the console."),
    SENDER_ONLY_PLAYER("{$e}This command can only be executed from a player.")

    ;

    private final LanguageEntry languageEntry;
    private final String defaultValue;

    AuspiceLang(String defaultValue, int... grouped) {
        this.defaultValue = defaultValue;
        this.languageEntry = DefinedMessenger.getEntry(null, this, grouped);
    }

    @Override
    public LanguageEntry getLanguageEntry() {
        return languageEntry;
    }

    @Override
    public String getDefaultValue() {
        return this.defaultValue;
    }
}
