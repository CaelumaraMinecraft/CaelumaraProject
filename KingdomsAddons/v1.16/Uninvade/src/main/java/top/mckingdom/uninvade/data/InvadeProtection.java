package top.mckingdom.uninvade.data;

import org.kingdoms.constants.metadata.KingdomMetadata;
import org.kingdoms.constants.metadata.StandardKingdomMetadata;
import org.kingdoms.constants.namespace.NamespaceContainer;
import org.kingdoms.events.invasion.KingdomPreInvadeEvent;
import org.kingdoms.locale.SupportedLanguage;
import org.kingdoms.locale.messenger.DefinedMessenger;
import org.kingdoms.locale.provider.MessageBuilder;
import top.mckingdom.uninvade.config.UninvadeAddonLang;

public interface InvadeProtection extends NamespaceContainer {

    boolean isProtect(KingdomPreInvadeEvent event);

    String getName(SupportedLanguage language);

}
