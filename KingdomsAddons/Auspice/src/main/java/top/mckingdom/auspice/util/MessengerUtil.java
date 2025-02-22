package top.mckingdom.auspice.util;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.config.annotations.AdvancedMessage;
import org.kingdoms.config.annotations.Comment;
import org.kingdoms.locale.Language;
import org.kingdoms.locale.LanguageEntry;
import org.kingdoms.locale.LanguageManager;
import org.kingdoms.locale.messenger.DefinedMessenger;
import org.kingdoms.locale.provider.MessageProvider;
import top.mckingdom.auspice.AuspiceAddon;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import static top.mckingdom.auspice.util.MessengerUtil.Companion.constants;

public class MessengerUtil {


    /**
     * Create a new DefinedMessenger object
     *
     * @param path The Path of Language, it will ignore case, such as {@code new String[]{"permission", "JAIL"}} will make a path "permission/jail"
     */
    public static DefinedMessenger createMessenger(String[] path, String defaultValue) {

        if (!AuspiceAddon.get().isKingdomsLoaded()) {
            AuspiceLogger.error("Can not create defined messenger when Kingdoms not loaded");
            return null;
        }

        if (AuspiceAddon.get().isKingdomsEnabled()) {
            AuspiceLogger.warn("You shouldn't create messenger when Kingdoms is already enabled, this will result in no language entry in the language file");
        }

        LinkedList<String> newPath = new LinkedList<>();
        for (String it : path) {
            newPath.add(it.toLowerCase().replace('_', '-'));
        }

        DynamicLanguage dynamicLanguage = new DynamicLanguage(newPath.toArray(new String[path.length]), defaultValue);
        constants.add(dynamicLanguage);
        LanguageManager.registerMessenger(DynamicLanguage.class, constants.toArray(new DynamicLanguage[constants.size()]));

        return dynamicLanguage;
    }


    public static class Companion {
        static final Set<DynamicLanguage> constants = new HashSet<>();
    }

    public static class DynamicLanguage implements DefinedMessenger {

        public final @NotNull LanguageEntry languageEntry;
        public final @NotNull String defaultValue;

        DynamicLanguage(@NonNull String[] path, @NotNull String defaultValue) {
            this.languageEntry = new LanguageEntry(path);
            this.defaultValue = defaultValue;
            constants.add(this);
        }

        public Set<DynamicLanguage> getConstants() {
            return constants;
        }

        @NotNull
        @Override
        public LanguageEntry getLanguageEntry() {
            return this.languageEntry;
        }

        @Override
        public String name() {
            StringBuilder out = new StringBuilder();
            for (String str : this.languageEntry.getPath()) {
                out.append(str.toUpperCase().replace('-', '_')).append("_");
            }
            out.deleteCharAt(out.length() - 1);   //移除尾部的  _
            return out.toString();
        }

        @NotNull
        @Override
        public String getDefaultValue() {
            return this.defaultValue;
        }

        @Override
        public MessageProvider getProvider(Language language) {
            return DefinedMessenger.super.getProvider(language);
        }

        @Override
        public Comment getComment() {
            return null;
        }

        @Override
        public AdvancedMessage getAdvancedData() {
            return null;
        }
    }
}
