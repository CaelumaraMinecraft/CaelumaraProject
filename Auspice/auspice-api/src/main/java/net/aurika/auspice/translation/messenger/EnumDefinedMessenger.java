package net.aurika.auspice.translation.messenger;

import kotlin.collections.ArraysKt;
import kotlin.text.StringsKt;
import net.aurika.auspice.configs.globalconfig.AuspiceGlobalConfig;
import net.aurika.auspice.configs.messages.LanguageManager;
import net.aurika.auspice.configs.messages.context.MessageContextImpl;
import net.aurika.auspice.translation.message.provider.MessageProvider;
import net.aurika.auspice.translation.diversity.Diversity;
import net.aurika.auspice.translation.message.manager.MessageManager;
import net.aurika.auspice.translation.TranslationEntry;
import net.aurika.auspice.user.Auspice;
import net.aurika.auspice.server.command.CommandSender;
import net.aurika.auspice.server.entity.Player;
import net.aurika.config.annotations.AdvancedMessage;
import net.aurika.config.annotations.Comment;
import net.aurika.config.annotations.Path;
import net.aurika.config.annotations.RawPath;
import net.aurika.config.path.ConfigEntry;
import net.aurika.util.collection.ArrayUtils;
import net.aurika.util.string.Strings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public interface EnumDefinedMessenger extends DefinedMessenger {

    @Override
    default @NotNull MessageProvider getProvider(@NotNull Diversity locale) {
        Objects.requireNonNull(locale);
        MessageProvider provider = locale.getMessage(this.translationEntry(), true);

        //noinspection ConstantValue
        assert provider != null : "Language returned null for " + locale + "->" + this.translationEntry();

        return provider;
    }

    default @Nullable Comment getComment() {
        try {
            return this.getClass().getField(this.name()).getAnnotation(Comment.class);
        } catch (NoSuchFieldException ex) {
            return null;
        }
    }

    default @Nullable AdvancedMessage getAdvancedData() {
        try {
            return this.getClass().getField(this.name()).getAnnotation(AdvancedMessage.class);
        } catch (NoSuchFieldException ex) {
            return null;
        }
    }

    @NotNull String name();

    @Override
    @NotNull MessageManager messageManager();

    @Override
    @NotNull TranslationEntry translationEntry();

    @Override
    @Nullable String defaultValue();

    @Override
    default void sendMessage(@NotNull CommandSender messageReceiver, @NotNull MessageContextImpl textPlaceholderProvider) {
        Objects.requireNonNull(messageReceiver);
        Objects.requireNonNull(textPlaceholderProvider);
        if (AuspiceGlobalConfig.PREFIX.getBoolean()) {
            textPlaceholderProvider.usePrefix(true);
        }

        Object var4 = messageReceiver instanceof Player ? LanguageManager.localeOf(messageReceiver) : textPlaceholderProvider.diversity();
        Objects.requireNonNull(var4);
        Object var3 = var4;
        textPlaceholderProvider.lang((Locale) var4);
        if (CommandAdminTrack.isTracking(messageReceiver) && this != AuspiceLang.COMMAND_ADMIN_TRACK_TRACKED && this != AuspiceLang.COMMAND_ADMIN_TRACK_GUI) {
            String var5;
            Object var8;
            label23:
            {
                SupportedLanguage var6 = (SupportedLanguage) var4;
                var5 = StringsKt.replace$default(Auspice.get().getDataFolder().toPath().relativize(var6.getMainLanguageFile()).toString(), ' ', '*', false, 4, (Object) null);
                Node var10000 = var6.getAdapter().getConfig().findNode(this.translationEntry().getPath());
                if (var10000 != null) {
                    Mark var7 = var10000.getStartMark();
                    if (var7 != null) {
                        var8 = Integer.valueOf(var7.getLine()).toString();
                        if (var8 != null) {
                            break label23;
                        }
                    }
                }

                var8 = AuspiceLang.UNKNOWN;
            }

            var4 = var8;
            AuspiceLang.COMMAND_ADMIN_TRACK_TRACKED.getProvider((Locale) var3).send(messageReceiver, (new MessageContextImpl()).parse("path", Strings.join(this.translationEntry().getPath(), " {$sep}-> {$s}")).raw("file", var5).raw("raw", LanguageManager.getRawMessage(this, (SupportedLanguage) var3)).parse("line", var4));
        }

        this.getProvider((Locale) var4).send(messageReceiver, textPlaceholderProvider);
    }

    static @Nullable Path getAnnotatedPath(@NotNull EnumDefinedMessenger messenger) {
        Objects.requireNonNull(messenger);
        try {
            return messenger.getClass().getField(messenger.name()).getAnnotation(Path.class);
        } catch (NoSuchFieldException var2) {
            throw new RuntimeException(var2);
        }
    }

    static boolean isRawPath(@NotNull EnumDefinedMessenger enumDefinedMessenger) {
        Objects.requireNonNull(enumDefinedMessenger, "");

        try {
            return enumDefinedMessenger.getClass().getField(enumDefinedMessenger.name()).getAnnotation(RawPath.class) != null;
        } catch (NoSuchFieldException var2) {
            throw new RuntimeException(var2);
        }
    }

    static @NotNull ConfigEntry getEntry(@Nullable String var1, @NotNull EnumDefinedMessenger messenger, int[] group) {
        Objects.requireNonNull(messenger);
        Objects.requireNonNull(group);
        Path pathAnn = getAnnotatedPath(messenger);
        if (pathAnn != null) {
            return new ConfigEntry(pathAnn.value());
        } else {
            int[] var7 = group;
            boolean isCommandEntry = messenger.name().startsWith("COMMAND_");
            if (isCommandEntry && group.length == 0) {
                if (!messenger.name().startsWith("COMMAND_ADMIN_")) {
                    var7 = new int[]{1, 2};
                } else {
                    var7 = new int[]{1, 2, 3};
                }
            }

            try {
                List<String> var11 = Strings.split(Strings.getGroupedOption(messenger.name(), Arrays.copyOf(var7, var7.length)), '.', false);
                Objects.requireNonNull(var11);
                String[] pathStrArray = var11.toArray(new String[0]);
                if (var1 != null && !isCommandEntry && !isRawPath(messenger)) {
                    String[] var8 = new String[1];
                    var8[0] = var1;
                    Object[] var12 = ArrayUtils.merge(var8, pathStrArray);
                    Objects.requireNonNull(var12);
                    pathStrArray = (String[]) var12;
                }

                return new ConfigEntry(pathStrArray);
            } catch (IndexOutOfBoundsException var6) {
                StringBuilder var10002 = new StringBuilder("Invalid number of groups provided for ");
                String var10003 = var1;
                if (var1 == null) {
                    var10003 = "";
                }

                throw new RuntimeException(var10002.append(var10003).append(' ').append(messenger).append(" -> ").append(ArraysKt.joinToString(group, ", ", "", "", -1, "", null)).toString(), var6);
            }
        }
    }
}
