package top.mckingdom.powerfulterritory.constants.invade_protection;

import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.namespace.Namespaced;
import org.kingdoms.events.invasion.KingdomPreInvadeEvent;
import org.kingdoms.locale.Language;

public interface InvadeProtection extends Namespaced {

    boolean isProtect(KingdomPreInvadeEvent event);

    @NotNull String getName(@NotNull Language language);
}
