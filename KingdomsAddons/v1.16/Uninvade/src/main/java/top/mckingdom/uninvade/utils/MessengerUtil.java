package top.mckingdom.uninvade.utils;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.config.AdvancedMessage;
import org.kingdoms.config.Comment;
import org.kingdoms.locale.LanguageEntry;
import org.kingdoms.locale.LanguageManager;
import org.kingdoms.locale.SupportedLanguage;
import org.kingdoms.locale.messenger.DefinedMessenger;
import org.kingdoms.locale.provider.MessageProvider;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class MessengerUtil {


    /**
     * Create a new DefinedMessenger object
     * @param path The Path of Language, it will ignore case, such as "new String[]{"permissions", "JAIL"}" will make a path "permissions/jail"
     */
    public static DefinedMessenger createMessenger(String[] path, String defaultValue) {


        LinkedList<String> newPath = new LinkedList<>();
        for (String it : path) {
            newPath.add(it.toLowerCase().replace('_', '-'));
        }

        DynamicLanguage dynamicLanguage = new DynamicLanguage(newPath.toArray(new String[path.length]), defaultValue);
        Companion.constants.add(dynamicLanguage);
        LanguageManager.registerMessenger(DynamicLanguage.class, Companion.constants.toArray(new DynamicLanguage[Companion.constants.size()]));

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
            MessengerUtil.Companion.constants.add(this);
        }

        public Set<DynamicLanguage> getConstants() {
            return MessengerUtil.Companion.constants;
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
        public MessageProvider getProvider(SupportedLanguage language) {
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
