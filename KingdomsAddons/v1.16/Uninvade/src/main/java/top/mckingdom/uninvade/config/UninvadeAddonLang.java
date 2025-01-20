package top.mckingdom.uninvade.config;

import org.kingdoms.locale.LanguageEntry;
import org.kingdoms.locale.messenger.DefinedMessenger;

public enum UninvadeAddonLang implements DefinedMessenger {

    COMMAND_ADMIN_UNINVADE_SET_USAGES("{$usage} admin uninvade set <type> [x] [z]", 1, 2, 3, 4),
    COMMAND_ADMIN_UNINVADE_SET_DESCRIPTION("设置土地保护状态", 1, 2, 3, 4),
    COMMAND_ADMIN_UNINVADE_SET_SUCCESS("&a成功设置此土地状态为&8: &f%new-status%", 1, 2, 3, 4),
    COMMAND_ADMIN_UNINVADE_SET_FAILED_NOT_CLAIMED("NOPREFIX|ゃ &c这块土地本就无人占领", 1, 2, 3, 4, 5),
    COMMAND_ADMIN_UNINVADE_SET_FAILED_UNKNOWN_STATUS("NOPREFIX|ゃ &c未知的保护状态", 1, 2, 3, 4, 5),

    COMMAND_ADMIN_UNINVADE_GET_USAGES("{$usage} admin uninvade get [x] [z]", 1, 2, 3, 4),
    COMMAND_ADMIN_UNINVADE_GET_DESCRIPTION("查看土地保护状态", 1, 2, 3, 4),
    COMMAND_ADMIN_UNINVADE_GET_SUCCESS("&a此土地状态为&8: &f%status%", 1, 2, 3, 4),
    COMMAND_ADMIN_UNINVADE_GET_FAILED_NOT_CLAIMED("NOPREFIX|ゃ &c这块土地本就无人占领", 1, 2, 3, 4, 5),

    INVADE_PROTECTION("NOPREFIX|ゃ &c此土地已被保护"),
    INVADE_PROTECTION_STATUS_NAME_VISIBLE("可见保护", 4),
    INVADE_PROTECTION_STATUS_NAME_INVISIBLE("隐形保护", 4),
    INVADE_PROTECTION_STATUS_NAME_NO_PROTECTION("无保护", 4),


            ;
    private final LanguageEntry languageEntry;
    private final String defaultValue;

    UninvadeAddonLang(String defaultValue, int... grouped) {
        this.defaultValue = defaultValue;
        this.languageEntry = DefinedMessenger.getEntry("uninvade-addon", this, grouped);
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
