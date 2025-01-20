package top.mckingdom.uninvade.data;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.events.invasion.KingdomPreInvadeEvent;
import org.kingdoms.locale.SupportedLanguage;
import org.kingdoms.locale.messenger.Messenger;
import org.kingdoms.locale.provider.MessageBuilder;
import top.mckingdom.uninvade.utils.MessengerUtil;

import java.util.Locale;

public class StandardInvadeProtection implements InvadeProtection{



    public static final StandardInvadeProtection VISIBLE_PROTECTION = a(true, "VISIBLE_PROTECTION", "可见保护");
    public static final StandardInvadeProtection INVISIBLE_PROTECTION = a(true, "INVISIBLE_PROTECTION", "隐形保护");
    public static final StandardInvadeProtection NO_PROTECTION = a(false, "NO_PROTECTION", "无保护")


    ;

    private final boolean isProtect;
    private final Messenger nameMessenger;
    private final Namespace namespace;

    private static StandardInvadeProtection a(boolean b, String nsKey, String defaultNAme) {
        StandardInvadeProtection a = new StandardInvadeProtection(
                b,
                MessengerUtil.createMessenger(new String[]{"powerful-territory", "invade-protection-name", nsKey.toLowerCase(Locale.ENGLISH).replace('_', '-')}, defaultNAme),
                new Namespace("PowerfulTerritory", nsKey)
        );
        InvadeProtectionRegistry.get().register(a);
        return a;
    }

    public static void init() {

    }

    public StandardInvadeProtection(boolean isProtect, Messenger nameMessenger, Namespace namespace) {
        this.isProtect = isProtect;
        this.nameMessenger = nameMessenger;
        this.namespace = namespace;
    }

    public boolean isProtect(KingdomPreInvadeEvent event) {
        return this.isProtect;
    }
    @Override
    public String getName(SupportedLanguage language) {
        return this.nameMessenger.getProvider(language).getMessage().build(new MessageBuilder().lang(language));
    }

    @Override
    public @NonNull Namespace getNamespace() {
        return this.namespace;
    }
}
