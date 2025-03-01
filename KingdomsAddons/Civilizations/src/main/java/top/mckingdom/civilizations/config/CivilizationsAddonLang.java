package top.mckingdom.civilizations.config;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.kingdoms.locale.LanguageEntry;
import org.kingdoms.locale.messenger.DefinedMessenger;

public enum CivilizationsAddonLang implements DefinedMessenger {

    CIVILIZATION_CREATE_FAILED_NO_ENOUGH_RP("{$e}You don't have enough resource points to create a civilization.", 1, 2, 3),
    CIVILIZATION_CREATE_FAILED_NO_ENOUGH_MONEY("{$e}You don't have enough money to create a civilization.", 1, 2, 3),
    CIVILIZATION_DISBAND_WAR("A civilization{$es}%name% has been perished due to war.", 1, 2),
    CIVILIZATION_DISBAND_VOTE("A civilization{$es}%name% was dissolved due to the vote of its root members.", 1, 2),
    CIVILIZATION_DISBAND_TAX("{$e}A civilization has been disbanded due to not being able to pay the taxes{$sep}: {$es}$%tax%", 1, 2),

    CIVILIZATION_PERMISSION_CIVILIZATION("You don't have permission to join a civilization.", 2);

    private final LanguageEntry languageEntry;
    private final String defaultValue;

    CivilizationsAddonLang(String defaultValue, int... grouped) {
        this.defaultValue = defaultValue;
        this.languageEntry = DefinedMessenger.getEntry("civilizations-addon", this, grouped);
    }

    public final void sendConsoleMessage(Object... objects) {
        this.sendMessage(Bukkit.getConsoleSender(), objects);
    }

    public final void sendEveryoneMessage(Object... objects) {
        this.sendConsoleMessage(objects);

        for (Player player : Bukkit.getOnlinePlayers()) {
            this.sendMessage(player, objects);
        }
    }

    @Override
    public LanguageEntry getLanguageEntry() {
        return this.languageEntry;
    }

    @Override
    public String getDefaultValue() {
        return this.defaultValue;
    }
}
