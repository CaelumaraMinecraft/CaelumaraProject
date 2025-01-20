package top.mckingdom.powerful_territory.constants.land_categories;

import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.locale.Language;
import org.kingdoms.locale.messenger.DefinedMessenger;
import org.kingdoms.locale.messenger.Messenger;
import org.kingdoms.locale.placeholders.context.MessagePlaceholderProvider;
import top.mckingdom.auspice.utils.MessengerUtil;
import top.mckingdom.powerful_territory.PowerfulTerritory;

import java.util.Locale;

public class StandardLandCategory extends LandCategory {
    /**
     * 未分配
     */
    public static final LandCategory NONE = reg("NONE", true);
    /**
     * 入侵时自动转为此区域
     */
    public static final LandCategory INVASION = reg("INVASION", false);
    /**
     * 被入侵时自动转为此区域
     */
    public static final LandCategory DEFENSE = reg("DEFENSE", false);
    /**
     * 经济发展区
     */
    public static final LandCategory ECONOMICS = reg("ECONOMICS", true);
    /**
     * 内务
     */
    public static final LandCategory INTERIOR = reg("INTERIOR", true);
    /**
     * 外交
     */
    public static final LandCategory DIPLOMACY = reg("DIPLOMACY", true);

    private final Messenger nameMessenger;
    private final DefinedMessenger descriptionMessenger;
    private final DefinedMessenger loreMessenger;

    public StandardLandCategory(Namespace ns, boolean editable, DefinedMessenger nameMessenger, DefinedMessenger descriptionMessenger, DefinedMessenger loreMessenger) {
        super(ns, editable);
        this.nameMessenger = nameMessenger;
        this.descriptionMessenger = descriptionMessenger;
        this.loreMessenger = loreMessenger;
    }


    public static void init() {

    }

    private static LandCategory reg(String key, boolean editable) {
        return register(PowerfulTerritory.buildNS(key), editable);
    }

    public static StandardLandCategory register(Namespace ns, boolean editable) {
        String key = ns.getKey().toLowerCase(Locale.ENGLISH).replace('_', '-');
        return register(
                ns,
                editable,
                MessengerUtil.createMessenger(new String[]{"powerful-territory", "constants-category", key, "name"}, key),
                MessengerUtil.createMessenger(new String[]{"powerful-territory", "constants-category", key, "description"}, "A constants category: " + key),
                MessengerUtil.createMessenger(new String[]{"powerful-territory", "constants-category", key, "lore"}, "A constants category, it may has some abilities: " + key)
        );
    }

    protected static StandardLandCategory register(Namespace ns, boolean editable, DefinedMessenger nameMessenger, DefinedMessenger descriptionMessenger, DefinedMessenger loreMessenger) {
        StandardLandCategory landCategory = new StandardLandCategory(ns, editable, nameMessenger, descriptionMessenger, loreMessenger);
        PowerfulTerritory.get().getLandCategoryRegistry().register(landCategory);
        return landCategory;
    }


    public @NotNull String getName(@NotNull Language language) {
        return this.nameMessenger.getProvider(language).getMessage().buildPlain(new MessagePlaceholderProvider().lang(language));
    }

    public String getDescription(Language language) {
        return this.descriptionMessenger.getProvider(language).getMessage().buildPlain(new MessagePlaceholderProvider().lang(language));
    }

    public String getLore(Language language) {
        return this.loreMessenger.getProvider(language).getMessage().buildPlain(new MessagePlaceholderProvider().lang(language));
    }


    public Messenger getNameMessenger() {
        return nameMessenger;
    }

    public Messenger getDescriptionMessenger() {
        return descriptionMessenger;
    }

    public Messenger getLoreMessenger() {
        return loreMessenger;
    }
}
