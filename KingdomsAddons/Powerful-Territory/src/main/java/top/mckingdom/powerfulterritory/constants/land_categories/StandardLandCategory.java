package top.mckingdom.powerfulterritory.constants.land_categories;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.locale.Language;
import org.kingdoms.locale.LanguageEntry;
import org.kingdoms.locale.messenger.DefaultedMessenger;
import org.kingdoms.locale.messenger.LanguageEntryMessenger;
import org.kingdoms.locale.messenger.Messenger;
import org.kingdoms.locale.messenger.StaticMessenger;
import org.kingdoms.locale.placeholders.context.MessagePlaceholderProvider;
import top.mckingdom.powerfulterritory.PowerfulTerritoryAddon;

public class StandardLandCategory extends LandCategory {

    public static final LandCategory NONE = reg(
            "NONE", true,
            "None",
            "None land category.");
    public static final LandCategory INVASION = reg(
            "INVASION", false,
            "Invasion",
            "This land is in a invasion");
    public static final LandCategory DEFENSE = reg(
            "DEFENSE", false,
            "Defense",
            "This land is in a defense");
    public static final LandCategory ECONOMICS = reg(
            "ECONOMICS", true,
            "Economics",
            "This land is an economics land.");
    public static final LandCategory INTERIOR = reg(
            "INTERIOR", true,
            "Interior",
            "This land is an interior land.");
    public static final LandCategory DIPLOMACY = reg(
            "DIPLOMACY", true,
            "Diplomacy",
            "This land is a diplomacy land.");

    private final @NotNull Messenger name;
    private final @NotNull Messenger description;

    public StandardLandCategory(@NotNull Namespace ns, boolean editable, @NotNull Messenger name, @NotNull Messenger description) {
        super(ns, editable);
        this.name = name;
        this.description = description;
    }

    public static void init() {
        // <clinit>
    }

    private static StandardLandCategory reg(String key, boolean editable, String name, String description) {
        return create(PowerfulTerritoryAddon.buildNS(key), editable, name, description);
    }

    private static LandCategory reg(String key, boolean editable) {
        return create(PowerfulTerritoryAddon.buildNS(key), editable);
    }

    @ApiStatus.Experimental
    protected static @NotNull LanguageEntry componentEntry(@NotNull Namespace key, @NotNull String component) {
        return new LanguageEntry(componentPath(key, component));
    }

    @ApiStatus.Experimental
    protected static @NotNull String @NotNull [] componentPath(@NotNull Namespace key, @NotNull String component) {
        Validate.Arg.notNull(key, "key");
        Validate.Arg.notNull(component, "Component");
        String s1 = key.getConfigOptionName();
        return new String[]{"powerful-territory", "land-category", s1, component};
    }

    @Deprecated
    public static @NotNull StandardLandCategory create(@NotNull Namespace ns, boolean editable) {
        String key = ns.getConfigOptionName();
        return create(
                ns,
                editable,
                key,
                "A land category " + key
        );
    }

    /**
     * Creates a {@linkplain StandardLandCategory}.
     */
    public static @NotNull StandardLandCategory create(@NotNull Namespace ns,
                                                       boolean editable,
                                                       @NotNull String name,
                                                       @NotNull String description
    ) {
        return create(
                ns,
                editable,
                new DefaultedMessenger(new LanguageEntryMessenger(componentEntry(ns, "name")), () -> new StaticMessenger(name)),
                new DefaultedMessenger(new LanguageEntryMessenger(componentEntry(ns, "description")), () -> new StaticMessenger(description))
        );
    }

    protected static StandardLandCategory create(@NotNull Namespace ns, boolean editable, @NotNull Messenger name, @NotNull Messenger description) {
        StandardLandCategory landCategory = new StandardLandCategory(ns, editable, name, description);
        PowerfulTerritoryAddon.get().getLandCategoryRegistry().register(landCategory);
        return landCategory;
    }

    public @NotNull String getName(@NotNull Language language) {
        return this.name.getProvider(language).getMessage().buildPlain(new MessagePlaceholderProvider().lang(language));
    }

    public String getDescription(Language language) {
        return this.description.getProvider(language).getMessage().buildPlain(new MessagePlaceholderProvider().lang(language));
    }

    public @NotNull Messenger getName() {
        return name;
    }

    public @NotNull Messenger getDescription() {
        return description;
    }
}
